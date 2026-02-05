package com.example.myfirstcafe.feature.addreceipt

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myfirstcafe.core.navigation.Routes
import com.example.myfirstcafe.core.ui.components.AddReceiptItemRow
import com.example.myfirstcafe.core.ui.components.CategoryItemRowEntity
import com.example.myfirstcafe.core.ui.components.OrangeButton
import com.example.myfirstcafe.core.ui.theme.GrayColor
import com.example.myfirstcafe.core.ui.theme.WhiteColor
import com.example.myfirstcafe.feature.addreceipt.model.toRouteArg
import androidx.compose.foundation.lazy.items
import com.example.myfirstcafe.core.ui.strings.AppStrings

@Composable
fun AddReceiptScreen(
    navController: NavController,
    viewModel: AddReceiptViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = AppStrings.BACK, tint = WhiteColor)
            }

            Text(
                text = AppStrings.ADD_RECEIPT,
                color = WhiteColor,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(48.dp))
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(bottom = 12.dp)
        ) {
            items(items = state.categories, key = { it.category.id }) { cwi ->
                val catId = cwi.category.id
                val isExpanded = state.expanded[catId] ?: false

                CategoryItemRowEntity(
                    title = cwi.category.name,
                    expanded = isExpanded,
                    onClick = { viewModel.toggleCategory(catId) }
                )

                if (isExpanded) {
                    cwi.items.forEach { item ->
                        val qty = state.quantities[item.id] ?: 0
                        AddReceiptItemRow(
                            item = item,
                            quantity = qty,
                            onPlus = { viewModel.inc(item.id) },
                            onMinus = { viewModel.dec(item.id) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                Divider(color = GrayColor.copy(alpha = 0.35f))
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        OrangeButton(
            text = AppStrings.NEXT,
            onClick = {
                if (state.totalSelectedCount == 0) {
                    Toast.makeText(context, AppStrings.NO_ITEM_SELECTED_MESSAGE, Toast.LENGTH_SHORT).show()
                    return@OrangeButton
                }
                val draft = viewModel.buildDraft()
                navController.navigate(Routes.receiptDetailsDraft(draft.toRouteArg()))
            }
        )
    }
}