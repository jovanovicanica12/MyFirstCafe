package com.example.myfirstcafe.feature.receiptsdetails

import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myfirstcafe.core.ui.theme.GrayColor
import com.example.myfirstcafe.core.ui.theme.WhiteColor
import com.example.myfirstcafe.core.ui.util.toMoney

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedReceiptDetailsScreen(
    navController: NavController,
    receiptId: Long,
    viewModel: SavedReceiptDetailsViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(receiptId) {
        viewModel.load(receiptId)
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
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = WhiteColor
                )
            }

            Text(
                text = "Receipt details",
                color = WhiteColor,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(48.dp))
        }

        if (state.isLoading) {
            Text(text = "Loading...", color = WhiteColor)
            return
        }

        Text(
            text = "${state.dateLabel} • ${state.timeLabel}",
            color = WhiteColor.copy(alpha = 0.85f),
            fontSize = 13.sp
        )

        Spacer(Modifier.height(12.dp))
        Divider(color = GrayColor.copy(alpha = 0.35f))
        Spacer(Modifier.height(12.dp))

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(state.lines.size) { idx ->
                val line = state.lines[idx]

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "${line.qty} x ${line.name}",
                            color = WhiteColor,
                            fontSize = 16.sp
                        )
                        Text(
                            text = "${line.unitPrice.toMoney()} / item",
                            color = WhiteColor.copy(alpha = 0.7f),
                            fontSize = 12.sp
                        )
                    }

                    Text(
                        text = line.lineTotal.toMoney(),
                        color = WhiteColor,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Divider(color = GrayColor.copy(alpha = 0.25f))
            }

            item {
                Spacer(Modifier.height(12.dp))

                Row(Modifier.fillMaxWidth()) {
                    Text("Subtotal", color = WhiteColor.copy(alpha = 0.7f), modifier = Modifier.weight(1f))
                    Text(state.subtotal.toMoney(), color = WhiteColor, fontWeight = FontWeight.SemiBold)
                }

                Spacer(Modifier.height(8.dp))

                Row(Modifier.fillMaxWidth()) {
                    Text("Tip", color = WhiteColor.copy(alpha = 0.7f), modifier = Modifier.weight(1f))
                    Text(state.tip.toMoney(), color = WhiteColor, fontWeight = FontWeight.SemiBold)
                }

                Spacer(Modifier.height(8.dp))

                Row(Modifier.fillMaxWidth()) {
                    Text("VAT (20%)", color = WhiteColor.copy(alpha = 0.7f), modifier = Modifier.weight(1f))
                    Text(state.vatAmount.toMoney(), color = WhiteColor, fontWeight = FontWeight.SemiBold)
                }

                Spacer(Modifier.height(12.dp))
                Divider(color = GrayColor.copy(alpha = 0.35f))
                Spacer(Modifier.height(12.dp))

                Row(Modifier.fillMaxWidth()) {
                    Text("Total", color = WhiteColor, fontSize = 16.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                    Text(state.total.toMoney(), color = WhiteColor, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }

                Spacer(Modifier.height(20.dp))
            }
        }
    }
}
