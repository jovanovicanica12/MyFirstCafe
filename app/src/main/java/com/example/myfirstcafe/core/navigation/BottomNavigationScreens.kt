package com.example.myfirstcafe.core.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.myfirstcafe.core.ui.strings.AppStrings

sealed class BottomNavigationScreens(
    val route: String,
    val label: String,
    val icon: ImageVector
) {
    object Receipts : BottomNavigationScreens(
        route = Routes.RECEIPTS,
        label = AppStrings.RECEIPTS,
        icon = Icons.Filled.List
    )

    object Pricelist : BottomNavigationScreens(
        route = Routes.PRICELIST,
        label = AppStrings.PRICE_LIST,
        icon = Icons.Filled.ShoppingCart
    )

    companion object {
        val items = listOf(Receipts, Pricelist)
    }
}