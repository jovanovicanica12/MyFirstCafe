package com.example.myfirstcafe.feature.receiptsdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfirstcafe.core.ui.util.DateTimeFormat
import com.example.myfirstcafe.core.ui.util.round2
import com.example.myfirstcafe.data.repository.ReceiptsRepository
import com.example.myfirstcafe.feature.receiptsdetails.model.SavedReceiptDetailsUi
import com.example.myfirstcafe.feature.receiptsdetails.model.SavedReceiptLineUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedReceiptDetailsViewModel @Inject constructor(
    private val receiptsRepo: ReceiptsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SavedReceiptDetailsUi())
    val uiState: StateFlow<SavedReceiptDetailsUi> = _uiState.asStateFlow()

    fun load(receiptId: Long) {
        if (_uiState.value.receiptId == receiptId && !_uiState.value.isLoading) return

        _uiState.update { it.copy(isLoading = true, receiptId = receiptId) }

        viewModelScope.launch {
            receiptsRepo.observeReceiptDetails(receiptId).collect { details ->
                if (details == null) {
                    _uiState.update { it.copy(isLoading = false) }
                    return@collect
                }

                val receipt = details.receipt
                val tip = receipt.tip
                val vatRate = receipt.vatRate

                val grouped = details.items
                    .groupBy { it.nameSnapshot.trim() }
                    .map { (name, items) ->
                        val qty = items.sumOf { it.quantity }
                        val unit = items.first().unitPriceSnapshot
                        val lineTotal = (unit * qty).round2()
                        SavedReceiptLineUi(
                            name = name,
                            qty = qty,
                            unitPrice = unit,
                            lineTotal = lineTotal
                        )
                    }
                    .sortedByDescending { it.qty }

                val subtotal = grouped.sumOf { it.lineTotal }.round2()
                val base = (subtotal + tip).round2()
                val vatAmount = (base * vatRate).round2()
                val total = (base + vatAmount).round2()

                _uiState.update {
                    it.copy(
                        receiptId = receipt.id,
                        dateLabel = DateTimeFormat.date(receipt.createdAt),
                        timeLabel = DateTimeFormat.time(receipt.createdAt),
                        lines = grouped,
                        tip = tip,
                        vatRate = vatRate,
                        subtotal = subtotal,
                        vatAmount = vatAmount,
                        total = total,
                        isLoading = false
                    )
                }
            }
        }
    }
}