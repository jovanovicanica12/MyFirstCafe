package com.example.myfirstcafe.feature.receiptsdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfirstcafe.core.ui.util.round2
import com.example.myfirstcafe.data.local.entity.ReceiptEntity
import com.example.myfirstcafe.data.local.entity.ReceiptItemEntity
import com.example.myfirstcafe.data.repository.ReceiptsRepository
import com.example.myfirstcafe.feature.addreceipt.model.ReceiptDraft
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReceiptDetailsViewModel @Inject constructor(
    private val receiptsRepo: ReceiptsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReceiptDetailsUiState())
    val uiState: StateFlow<ReceiptDetailsUiState> = _uiState.asStateFlow()

    fun initDraft(draft: ReceiptDraft) {
        _uiState.update { it.copy(draft = draft) }
    }

    fun onTipChange(value: String) {
        _uiState.update { it.copy(tipText = value) }
    }

    fun subtotal(): Double = _uiState.value.draft.subtotal.round2()

    fun vatAmount(): Double {
        val s = _uiState.value
        val base = (s.draft.subtotal + s.tip)
        return (base * s.vatRate).round2()
    }

    fun total(): Double {
        val s = _uiState.value
        val base = s.draft.subtotal + s.tip
        return (base + base * s.vatRate).round2()
    }

    fun chargeReceipt(onDone: () -> Unit) {
        val s = _uiState.value
        viewModelScope.launch {
            _uiState.update { it.copy(isCharging = true) }

            val now = System.currentTimeMillis()
            val receiptId = receiptsRepo.createReceipt(
                receipt = ReceiptEntity(
                    createdAt = now,
                    tip = s.tip,
                    vatRate = s.vatRate
                ),
                items = s.draft.lines.map { line ->
                    ReceiptItemEntity(
                        receiptId = 0,
                        menuItemId = line.id,
                        nameSnapshot = line.name,
                        unitPriceSnapshot = line.price,
                        quantity = line.qty
                    )
                }
            )

            _uiState.update { it.copy(isCharging = false) }
            onDone()
        }
    }
}