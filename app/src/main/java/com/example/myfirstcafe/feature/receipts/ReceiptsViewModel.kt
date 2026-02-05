package com.example.myfirstcafe.feature.receipts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfirstcafe.core.ui.util.DateTimeFormat
import com.example.myfirstcafe.core.ui.util.round2
import com.example.myfirstcafe.data.repository.ReceiptsRepository
import com.example.myfirstcafe.feature.receipts.model.ReceiptDaySectionUi
import com.example.myfirstcafe.feature.receipts.model.ReceiptItemUi
import com.example.myfirstcafe.feature.receipts.model.ReceiptRowUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class ReceiptsViewModel @Inject constructor(
    private val receiptsRepo: ReceiptsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReceiptsUiState())
    val uiState: StateFlow<ReceiptsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            receiptsRepo.observeReceiptsWithItems().collect { list ->
                val sections = list
                    .groupBy { dayKey(it.receipt.createdAt) }
                    .toList()
                    .sortedByDescending { it.first }
                    .map { (_, receiptsInDay) ->
                        val dateLabel = DateTimeFormat.date(receiptsInDay.first().receipt.createdAt)

                        val rows = receiptsInDay.map { r ->
                            val subtotal = r.items.sumOf { it.unitPriceSnapshot * it.quantity }
                            val tip = r.receipt.tip
                            val vatRate = r.receipt.vatRate
                            val base = subtotal + tip
                            val total = (base + base * vatRate).round2()

                            val itemsUi: List<ReceiptItemUi> =
                                r.items
                                    .groupBy { it.nameSnapshot.trim() }
                                    .map { (name, items) ->
                                        ReceiptItemUi(
                                            name = name,
                                            quantity = items.sumOf { it.quantity }
                                        )
                                    }
                                    .sortedByDescending { it.quantity }

                            ReceiptRowUi(
                                id = r.receipt.id,
                                time = DateTimeFormat.time(r.receipt.createdAt),
                                total = total,
                                tip = tip,
                                items = itemsUi,
                                itemCount = itemsUi.sumOf { it.quantity }
                            )
                        }

                        val dayRevenue = rows.sumOf { it.total }.round2()

                        ReceiptDaySectionUi(
                            date = dateLabel,
                            dayRevenue = dayRevenue,
                            receipts = rows
                        )
                    }

                _uiState.update { it.copy(sections = sections) }
            }
        }
    }

    private fun dayKey(epochMillis: Long): Long {
        val cal = Calendar.getInstance()
        cal.timeInMillis = epochMillis
        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)
        return cal.timeInMillis
    }

    fun deleteReceipt(receiptId: Long) {
        viewModelScope.launch {
            receiptsRepo.deleteReceipt(receiptId)
        }
    }
}