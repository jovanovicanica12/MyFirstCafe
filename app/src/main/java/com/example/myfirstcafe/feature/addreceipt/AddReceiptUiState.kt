package com.example.myfirstcafe.feature.addreceipt

import com.example.myfirstcafe.data.local.relation.CategoryWithItems

data class AddReceiptUiState(
    val categories: List<CategoryWithItems> = emptyList(),
    val expanded: Map<Long, Boolean> = emptyMap(),
    val quantities: Map<Long, Int> = emptyMap()
) {
    val totalSelectedCount: Int get() = quantities.values.sum()
}