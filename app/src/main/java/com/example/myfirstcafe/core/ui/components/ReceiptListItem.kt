package com.example.myfirstcafe.core.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfirstcafe.core.ui.strings.AppStrings
import com.example.myfirstcafe.core.ui.theme.GrayColor
import com.example.myfirstcafe.core.ui.theme.WhiteColor
import com.example.myfirstcafe.core.ui.util.toMoney
import com.example.myfirstcafe.feature.receipts.model.ReceiptItemUi

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ReceiptListItem(
    timeLabel: String,
    totalLabel: String,
    items: List<ReceiptItemUi>,
    tipAmount: Double,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            ),
        color = GrayColor
    ) {
        Column(modifier = Modifier.padding(14.dp)) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = timeLabel,
                    color = WhiteColor,
                    fontSize = 14.sp,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = totalLabel,
                    color = WhiteColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.height(10.dp))

            items.forEach { item ->
                Text(
                    text = "${item.quantity} x ${item.name}",
                    color = WhiteColor.copy(alpha = 0.8f),
                    fontSize = 12.sp
                )
            }

            Spacer(Modifier.height(10.dp))
            Divider(color = WhiteColor.copy(alpha = 0.12f))
            Spacer(Modifier.height(10.dp))

            Row(Modifier.fillMaxWidth()) {
                Text(
                    text = AppStrings.TIP,
                    color = WhiteColor.copy(alpha = 0.7f),
                    fontSize = 12.sp,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = tipAmount.toMoney(),
                    color = WhiteColor,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}
