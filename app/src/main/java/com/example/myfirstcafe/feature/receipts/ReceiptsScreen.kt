package com.example.myfirstcafe.feature.receipts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myfirstcafe.core.navigation.Routes
import com.example.myfirstcafe.core.ui.components.FabButton
import com.example.myfirstcafe.core.ui.components.ReceiptListItem
import com.example.myfirstcafe.core.ui.strings.AppStrings
import com.example.myfirstcafe.core.ui.theme.WhiteColor
import com.example.myfirstcafe.core.ui.util.toMoney
import com.example.myfirstcafe.feature.receipts.model.ReceiptDaySectionUi
import androidx.compose.foundation.lazy.items as lazyItems

@Composable
fun ReceiptsScreen(
    navController: NavController,
    viewModel: ReceiptsViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    var receiptToDeleteId by remember { mutableStateOf<Long?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {

        if (state.sections.isEmpty()) {
            Text(
                text = AppStrings.NO_RECEIPT_MESSAGE,
                color = WhiteColor,
                fontSize = 18.sp,
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentPadding = PaddingValues(bottom = 90.dp)
            ) {
                state.sections.forEach { section ->
                    this.daySection(
                        section = section,
                        onReceiptClick = { receiptId ->
                            navController.navigate(Routes.receiptDetails(receiptId))
                        },
                        onReceiptLongClick = { receiptId ->
                            receiptToDeleteId = receiptId
                        }
                    )
                }
            }
        }

        FabButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            onClick = { navController.navigate(Routes.ADD_RECEIPT) }
        )
    }

    receiptToDeleteId?.let { id ->
        AlertDialog(
            onDismissRequest = { receiptToDeleteId = null },
            title = { Text(AppStrings.DELETE_RECEIPT) },
            text = { Text(AppStrings.CONFIRM_DELETE_RECEIPT) },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.deleteReceipt(id)
                        receiptToDeleteId = null
                    }
                ) { Text(AppStrings.YES) }
            },
            dismissButton = {
                TextButton(onClick = { receiptToDeleteId = null }) { AppStrings.NO }
            }
        )
    }
}

private fun LazyListScope.daySection(
    section: ReceiptDaySectionUi,
    onReceiptClick: (Long) -> Unit,
    onReceiptLongClick: (Long) -> Unit
) {
    item(key = "header_${section.date}") {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = section.date,
                color = WhiteColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = section.dayRevenue.toMoney(),
                color = WhiteColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }

    this.lazyItems(
        items = section.receipts,
        key = { it.id }
    ) { r ->
        ReceiptListItem(
            timeLabel = r.time,
            totalLabel = r.total.toMoney(),
            items = r.items,
            tipAmount = r.tip,
            onClick = { onReceiptClick(r.id) },
            onLongClick = { onReceiptLongClick(r.id) }
        )

        Spacer(modifier = Modifier.height(10.dp))
    }

    item(key = "spacer_${section.date}") {
        Spacer(modifier = Modifier.height(16.dp))
    }
}
