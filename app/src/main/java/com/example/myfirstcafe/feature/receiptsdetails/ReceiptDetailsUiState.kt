package com.example.myfirstcafe.feature.receiptsdetails

import com.example.myfirstcafe.feature.addreceipt.model.ReceiptDraft

data class ReceiptDetailsUiState(
    val draft: ReceiptDraft = ReceiptDraft(emptyList()),
    val tipText: String = "",
    val vatRate: Double = 0.20,
    val isCharging: Boolean = false
) {
    val tip: Double get() = tipText.toDoubleOrNull() ?: 0.0
}