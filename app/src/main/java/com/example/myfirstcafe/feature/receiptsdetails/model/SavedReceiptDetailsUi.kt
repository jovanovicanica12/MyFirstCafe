package com.example.myfirstcafe.feature.receiptsdetails.model

data class SavedReceiptLineUi(
    val name: String,
    val qty: Int,
    val unitPrice: Double,
    val lineTotal: Double
)

data class SavedReceiptDetailsUi(
    val receiptId: Long = -1L,
    val dateLabel: String = "",
    val timeLabel: String = "",
    val lines: List<SavedReceiptLineUi> = emptyList(),
    val tip: Double = 0.0,
    val vatRate: Double = 0.20,
    val subtotal: Double = 0.0,
    val vatAmount: Double = 0.0,
    val total: Double = 0.0,
    val isLoading: Boolean = true
)