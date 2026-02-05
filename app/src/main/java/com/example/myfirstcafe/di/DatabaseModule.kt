package com.example.myfirstcafe.di

import android.content.Context
import androidx.room.Room
import com.example.myfirstcafe.data.local.dao.CategoryDao
import com.example.myfirstcafe.data.local.dao.CategoryWithItemsDao
import com.example.myfirstcafe.data.local.dao.MenuItemDao
import com.example.myfirstcafe.data.local.dao.ReceiptDao
import com.example.myfirstcafe.data.local.db.CafeDatabase
import com.example.myfirstcafe.data.repository.MenuRepository
import com.example.myfirstcafe.data.repository.ReceiptsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDb(@ApplicationContext context: Context): CafeDatabase =
        Room.databaseBuilder(context, CafeDatabase::class.java, "cafe_database")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideCategoryDao(db: CafeDatabase): CategoryDao = db.categoryDao()

    @Provides
    fun provideMenuItemDao(db: CafeDatabase): MenuItemDao = db.menuItemDao()

    @Provides
    fun provideCategoryWithItemsDao(db: CafeDatabase): CategoryWithItemsDao =
        db.categoryWithItemsDao()

    @Provides
    @Singleton
    fun provideMenuRepository(
        categoryDao: CategoryDao,
        menuItemDao: MenuItemDao,
        categoryWithItemsDao: CategoryWithItemsDao
    ): MenuRepository = MenuRepository(categoryDao, menuItemDao, categoryWithItemsDao)

    @Provides
    fun provideReceiptDao(db: CafeDatabase): ReceiptDao = db.receiptDao()

    @Provides
    @Singleton
    fun provideReceiptsRepository(
        receiptDao: ReceiptDao
    ): ReceiptsRepository = ReceiptsRepository(receiptDao)
}