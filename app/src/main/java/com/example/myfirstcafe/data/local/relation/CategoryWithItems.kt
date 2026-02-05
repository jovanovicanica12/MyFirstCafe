package com.example.myfirstcafe.data.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.example.myfirstcafe.data.local.entity.CategoryEntity
import com.example.myfirstcafe.data.local.entity.MenuItemEntity

data class CategoryWithItems(
    @Embedded val category: CategoryEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "categoryId"
    )
    val items: List<MenuItemEntity>
)