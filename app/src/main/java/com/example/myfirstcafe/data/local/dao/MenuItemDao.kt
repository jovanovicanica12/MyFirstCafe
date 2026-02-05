package com.example.myfirstcafe.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myfirstcafe.data.local.entity.MenuItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MenuItemDao {

    @Query("SELECT * FROM menu_items ORDER BY name")
    fun observeAllItems(): Flow<List<MenuItemEntity>>

    @Query("SELECT * FROM menu_items WHERE name LIKE '%' || :query || '%' ORDER BY name")
    fun observeSearch(query: String): Flow<List<MenuItemEntity>>

    @Query("SELECT * FROM menu_items WHERE id = :id LIMIT 1")
    suspend fun getById(id: Long): MenuItemEntity?

    @Query("SELECT COUNT(*) FROM menu_items")
    suspend fun countItems(): Int

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun upsert(item: MenuItemEntity)

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertAll(items: List<MenuItemEntity>)

    @Query("DELETE FROM menu_items WHERE id = :id")
    suspend fun deleteById(id: Long)
}