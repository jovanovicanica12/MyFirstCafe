package com.example.myfirstcafe.feature.receiptsdetails

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myfirstcafe.core.ui.components.OrangeButton
import com.example.myfirstcafe.core.ui.components.ReceiptLineRow
import com.example.myfirstcafe.core.ui.strings.AppStrings
import com.example.myfirstcafe.core.ui.theme.GrayColor
import com.example.myfirstcafe.core.ui.theme.WhiteColor
import com.example.myfirstcafe.core.ui.util.toMoney
import com.example.myfirstcafe.feature.addreceipt.model.ReceiptDraft

@Composable
fun ReceiptDetailsScreen(
    navController: NavController,
    draft: ReceiptDraft,
    viewModel: ReceiptDetailsViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(draft) {
        viewModel.initDraft(draft)
    }

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
                text = AppStrings.RECEIPTS_DETAILS,
                color = WhiteColor,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(48.dp))
        }

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(items = state.draft.lines, key = { it.id }) { line ->
                ReceiptLineRow(line = line)
            }

            item { Spacer(modifier = Modifier.height(12.dp)) }

            item {
                Text(
                    text = AppStrings.TIP,
                    color = WhiteColor,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                )
            }

            item {
                OutlinedTextField(
                    value = state.tipText,
                    onValueChange = viewModel::onTipChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    label = { Text(AppStrings.TIP_LABEL, color = WhiteColor) },
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = GrayColor,
                        unfocusedContainerColor = GrayColor,
                        disabledContainerColor = GrayColor,
                        focusedIndicatorColor = GrayColor,
                        unfocusedIndicatorColor = GrayColor,
                        focusedTextColor = WhiteColor,
                        unfocusedTextColor = WhiteColor,
                        cursorColor = WhiteColor
                    )
                )
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }

            item {
                val subtotal = viewModel.subtotal()
                val vat = viewModel.vatAmount()
                val total = viewModel.total()

                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Text(text = "${AppStrings.SUBTOTAL}: ${subtotal.toMoney()}", color = WhiteColor)
                    Text(text = "${AppStrings.TIP}: ${state.tip.toMoney()}", color = WhiteColor)
                    Text(text = "${AppStrings.VAT}: ${vat.toMoney()}", color = WhiteColor)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "${AppStrings.TOTAL}: ${total.toMoney()}",
                        color = WhiteColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
            }
        }

        OrangeButton(
            text = if (state.isCharging) AppStrings.CHARGING else AppStrings.CHARGE_RECEIPT,
            onClick = {
                if (state.isCharging) return@OrangeButton
                viewModel.chargeReceipt {
                    navController.popBackStack()
                    navController.popBackStack()
                }
            }
        )
    }
}