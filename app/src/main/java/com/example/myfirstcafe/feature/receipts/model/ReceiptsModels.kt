package com.example.myfirstcafe.feature.receipts.model

data class ReceiptRowUi(
    val id: Long,
    val time: String,
    val total: Double,
    val tip: Double,
    val items: List<ReceiptItemUi> = emptyList(),
    val itemCount: Int = items.sumOf { it.quantity }
)

data class ReceiptDaySectionUi(
    val date: String,
    val dayRevenue: Double,
    val receipts: List<ReceiptRowUi>
)