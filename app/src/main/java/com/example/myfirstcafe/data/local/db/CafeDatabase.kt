package com.example.myfirstcafe.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myfirstcafe.data.local.entity.CategoryEntity
import com.example.myfirstcafe.data.local.entity.MenuItemEntity
import com.example.myfirstcafe.data.local.dao.CategoryDao
import com.example.myfirstcafe.data.local.dao.CategoryWithItemsDao
import com.example.myfirstcafe.data.local.dao.MenuItemDao
import com.example.myfirstcafe.data.local.dao.ReceiptDao
import com.example.myfirstcafe.data.local.entity.ReceiptEntity
import com.example.myfirstcafe.data.local.entity.ReceiptItemEntity

@Database(
    entities = [
        CategoryEntity::class,
        MenuItemEntity::class,
        ReceiptEntity::class,
        ReceiptItemEntity::class],
    version = 2,
    exportSchema = false
)
abstract class CafeDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun menuItemDao(): MenuItemDao
    abstract fun categoryWithItemsDao(): CategoryWithItemsDao
    abstract fun receiptDao(): ReceiptDao
}

