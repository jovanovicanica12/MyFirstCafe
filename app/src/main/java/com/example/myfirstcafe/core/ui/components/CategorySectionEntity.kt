package com.example.myfirstcafe.core.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.myfirstcafe.data.local.entity.CategoryEntity
import com.example.myfirstcafe.data.local.entity.MenuItemEntity

@Composable
fun CategorySectionEntity(
    category: CategoryEntity,
    items: List<MenuItemEntity>,
    expanded: Boolean,
    onHeaderClick: () -> Unit,
    onItemClick: (MenuItemEntity) -> Unit,
    onItemLongClick: (MenuItemEntity) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
    ) {
        CategoryItemRowEntity(
            title = category.name,
            expanded = expanded,
            onClick = onHeaderClick
        )

        if (expanded) {
            items.forEach { item ->
                MenuItemRowEntity(
                    item = item,
                    onClick = { onItemClick(item) },
                    onLongClick = { onItemLongClick(item) }
                )
            }
        }
    }
}