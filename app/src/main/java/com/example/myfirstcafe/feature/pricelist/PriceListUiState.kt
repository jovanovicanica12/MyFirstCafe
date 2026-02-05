package com.example.myfirstcafe.feature.pricelist

import com.example.myfirstcafe.data.local.entity.MenuItemEntity
import com.example.myfirstcafe.data.local.relation.CategoryWithItems

data class PriceListUiState(
    val query: String = "",
    val categories: List<CategoryWithItems> = emptyList(),
    val searchResults: List<MenuItemEntity> = emptyList(),
    val expanded: Map<Long, Boolean> = emptyMap()
)