package com.example.myfirstcafe.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.myfirstcafe.data.local.relation.CategoryWithItems
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryWithItemsDao {

    @Transaction
    @Query("SELECT * FROM categories ORDER BY id")
    fun observeCategoriesWithItems(): Flow<List<CategoryWithItems>>
}