package com.example.myfirstcafe.feature.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myfirstcafe.feature.addedititem.AddEditItemScreen
import com.example.myfirstcafe.feature.pricelist.PriceListScreen
import com.example.myfirstcafe.core.navigation.BottomNavigationScreens
import com.example.myfirstcafe.feature.receipts.ReceiptsScreen
import com.example.myfirstcafe.core.navigation.Routes
import com.example.myfirstcafe.core.ui.theme.BlackColor
import com.example.myfirstcafe.core.ui.theme.OrangeColor
import com.example.myfirstcafe.core.ui.theme.WhiteColor
import com.example.myfirstcafe.feature.addreceipt.AddReceiptScreen
import com.example.myfirstcafe.feature.addreceipt.model.receiptDraftFromRouteArg
import com.example.myfirstcafe.feature.receiptsdetails.ReceiptDetailsScreen
import com.example.myfirstcafe.feature.receiptsdetails.SavedReceiptDetailsScreen


@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomBar = when {
        currentRoute == null -> true
        currentRoute.startsWith("add_receipt") -> false
        currentRoute.startsWith("receipt_details_draft") -> false
        currentRoute.startsWith("receipt_details/") -> false
        currentRoute.startsWith("add_edit_item") -> false
        else -> true
    }

    Scaffold(
        bottomBar = {
            if (showBottomBar) BottomBar(navController)
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Routes.RECEIPTS,
            modifier = Modifier.padding(padding)
        ) {

            composable(Routes.RECEIPTS) {
                ReceiptsScreen(navController)
            }

            composable(Routes.ADD_RECEIPT) {
                AddReceiptScreen(navController = navController)
            }

            composable(
                route = Routes.RECEIPT_DETAILS_DRAFT_ROUTE,
                arguments = listOf(navArgument("draft") { type = NavType.StringType })
            ) { entry ->
                val arg = entry.arguments?.getString("draft").orEmpty()
                val draft = receiptDraftFromRouteArg(arg)
                ReceiptDetailsScreen(navController = navController, draft = draft)
            }

            composable(
                route = Routes.RECEIPT_DETAILS_ROUTE,
                arguments = listOf(navArgument("receiptId") { type = NavType.LongType })
            ) { entry ->
                val id = entry.arguments?.getLong("receiptId") ?: -1L
                SavedReceiptDetailsScreen(
                    navController = navController,
                    receiptId = id
                )
            }


            composable(Routes.PRICELIST) {
                PriceListScreen(navController)
            }

            composable(
                route = Routes.ADD_EDIT_ITEM_ROUTE,
                arguments = listOf(
                    navArgument("itemId") {
                        type = NavType.LongType
                        defaultValue = -1L
                    }
                )
            ) { backStackEntry ->
                val itemId = backStackEntry.arguments?.getLong("itemId") ?: -1L
                AddEditItemScreen(
                    navController = navController,
                    itemId = itemId
                )
            }
        }
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        BottomNavigationScreens.Receipts,
        BottomNavigationScreens.Pricelist
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(containerColor = BlackColor) {
        screens.forEach { screen ->
            val selected = currentRoute?.startsWith(screen.route) == true

            NavigationBarItem(
                selected = selected,
                onClick = {
                    if (!selected) {
                        navController.navigate(screen.route) {
                            popUpTo(Routes.RECEIPTS) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = { Icon(imageVector = screen.icon, contentDescription = screen.label) },
                label = {
                    Text(
                        text = screen.label,
                        color = if (selected) OrangeColor else WhiteColor
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = OrangeColor,
                    unselectedIconColor = WhiteColor,
                    selectedTextColor = OrangeColor,
                    unselectedTextColor = WhiteColor,
                    indicatorColor = BlackColor
                )
            )
        }
    }
}