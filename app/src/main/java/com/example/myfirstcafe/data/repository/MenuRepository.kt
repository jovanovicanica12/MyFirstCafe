package com.example.myfirstcafe.data.repository

import com.example.myfirstcafe.data.local.dao.CategoryDao
import com.example.myfirstcafe.data.local.dao.CategoryWithItemsDao
import com.example.myfirstcafe.data.local.dao.MenuItemDao
import com.example.myfirstcafe.data.local.entity.CategoryEntity
import com.example.myfirstcafe.data.local.entity.MenuItemEntity
import com.example.myfirstcafe.data.local.relation.CategoryWithItems
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MenuRepository @Inject constructor(
    private val categoryDao: CategoryDao,
    private val menuItemDao: MenuItemDao,
    private val categoryWithItemsDao: CategoryWithItemsDao
) {
    fun observeCategories(): Flow<List<CategoryEntity>> = categoryDao.observeCategories()

    fun observeCategoriesWithItems(): Flow<List<CategoryWithItems>> =
        categoryWithItemsDao.observeCategoriesWithItems()

    fun observeSearch(query: String): Flow<List<MenuItemEntity>> = menuItemDao.observeSearch(query)

    suspend fun seedIfEmpty(categories: List<CategoryEntity>, items: List<MenuItemEntity>) {
        val hasCategories = categoryDao.countCategories() > 0
        val hasItems = menuItemDao.countItems() > 0
        if (!hasCategories && !hasItems) {
            categoryDao.insertAll(categories)
            menuItemDao.insertAll(items)
        }
    }

    suspend fun getItemById(id: Long): MenuItemEntity? = menuItemDao.getById(id)
    suspend fun upsertItem(item: MenuItemEntity) = menuItemDao.upsert(item)
    suspend fun deleteItem(id: Long) = menuItemDao.deleteById(id)
}