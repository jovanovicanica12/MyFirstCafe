package com.example.myfirstcafe.feature.pricelist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myfirstcafe.core.ui.components.FabButton
import com.example.myfirstcafe.core.ui.components.SearchBar
import com.example.myfirstcafe.core.ui.components.CategorySectionEntity
import com.example.myfirstcafe.core.ui.components.MenuItemRowEntity
import com.example.myfirstcafe.data.local.entity.MenuItemEntity
import com.example.myfirstcafe.core.navigation.Routes
import com.example.myfirstcafe.core.ui.theme.WhiteColor
import com.example.myfirstcafe.core.ui.strings.AppStrings

@Composable
fun PriceListScreen(
    navController: NavController,
    viewModel: PriceListViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    var itemToDelete by remember { mutableStateOf<MenuItemEntity?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            SearchBar(
                query = state.query,
                onQueryChange = viewModel::onQueryChange
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {

                if (state.query.length >= 3) {

                    item {
                        Text(
                            text = AppStrings.SEARCH_RESULTS,
                            color = WhiteColor,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }

                    if (state.searchResults.isEmpty()) {
                        item {
                            Text(
                                text = AppStrings.NO_ITEMS_FOUND,
                                color = WhiteColor.copy(alpha = 0.7f),
                                fontSize = 14.sp
                            )
                        }
                    } else {
                        items(state.searchResults) { item ->
                            MenuItemRowEntity(
                                item = item,
                                onClick = {
                                    navController.navigate(
                                        Routes.addEditItem(item.id)
                                    )
                                },
                                onLongClick = {
                                    itemToDelete = item
                                }
                            )
                        }
                    }
                }

                else {
                    items(state.categories) { categoryWithItems ->
                        val categoryId = categoryWithItems.category.id
                        val expanded = state.expanded[categoryId] ?: false

                        CategorySectionEntity(
                            category = categoryWithItems.category,
                            items = categoryWithItems.items,
                            expanded = expanded,
                            onHeaderClick = {
                                viewModel.onToggleCategory(categoryId)
                            },
                            onItemClick = { item ->
                                navController.navigate(
                                    Routes.addEditItem(item.id)
                                )
                            },
                            onItemLongClick = { item ->
                                itemToDelete = item
                            }
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }

        FabButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            onClick = {
                navController.navigate(Routes.addEditItem())
            }
        )
    }

    itemToDelete?.let { item ->
        AlertDialog(
            onDismissRequest = { itemToDelete = null },
            title = { Text(AppStrings.DELETE_ITEM_TITLE) },
            text = { Text(AppStrings.DELETE_ITEM_MESSAGE) },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.deleteItem(item.id)
                        itemToDelete = null
                    }
                ) {
                    Text(AppStrings.YES)
                }
            },
            dismissButton = {
                TextButton(onClick = { itemToDelete = null }) {
                    Text(AppStrings.NO)
                }
            }
        )
    }
}