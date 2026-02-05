package com.example.myfirstcafe.feature.receipts

import com.example.myfirstcafe.feature.receipts.model.ReceiptDaySectionUi

data class ReceiptsUiState(
    val sections: List<ReceiptDaySectionUi> = emptyList()
)