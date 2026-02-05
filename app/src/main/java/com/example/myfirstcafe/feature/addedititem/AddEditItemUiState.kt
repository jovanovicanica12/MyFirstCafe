package com.example.myfirstcafe.feature.addedititem

import com.example.myfirstcafe.data.local.entity.CategoryEntity

data class AddEditItemUiState(
    val isEdit: Boolean = false,
    val name: String = "",
    val priceText: String = "",
    val selectedCategoryId: Long? = null,
    val categories: List<CategoryEntity> = emptyList(),
    val isSaving: Boolean = false
)