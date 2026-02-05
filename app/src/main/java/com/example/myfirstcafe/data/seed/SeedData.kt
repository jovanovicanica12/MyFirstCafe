package com.example.myfirstcafe.data.seed

import com.example.myfirstcafe.data.local.entity.CategoryEntity
import com.example.myfirstcafe.data.local.entity.MenuItemEntity

object SeedData {
    const val CAT_DRINKS = 1L
    const val CAT_STARTERS = 2L
    const val CAT_MAIN = 3L
    const val CAT_DESSERTS = 4L

    val categories = listOf(
        CategoryEntity(CAT_DRINKS, "Drinks"),
        CategoryEntity(CAT_STARTERS, "Starters"),
        CategoryEntity(CAT_MAIN, "Main dishes"),
        CategoryEntity(CAT_DESSERTS, "Desserts")
    )

    val items = listOf(
        MenuItemEntity(name = "Espresso", price = 1.5, categoryId = CAT_DRINKS),
        MenuItemEntity(name = "Cappuccino", price = 2.0, categoryId = CAT_DRINKS),
        MenuItemEntity(name = "Lemonade", price = 1.8, categoryId = CAT_DRINKS),
        MenuItemEntity(name = "Bruschetta", price = 3.5, categoryId = CAT_STARTERS),
        MenuItemEntity(name = "Cheese plate", price = 4.0, categoryId = CAT_STARTERS),
        MenuItemEntity(name = "Pasta Bolognese", price = 7.5, categoryId = CAT_MAIN),
        MenuItemEntity(name = "Chicken steak", price = 8.0, categoryId = CAT_MAIN),
        MenuItemEntity(name = "Tiramisu", price = 3.0, categoryId = CAT_DESSERTS),
        MenuItemEntity(name = "Cheesecake", price = 3.5, categoryId = CAT_DESSERTS)
    )
}