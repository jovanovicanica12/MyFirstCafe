package com.example.myfirstcafe.core.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfirstcafe.core.ui.theme.WhiteColor
import com.example.myfirstcafe.core.ui.util.toMoney
import com.example.myfirstcafe.feature.addreceipt.model.DraftLine

@Composable
fun ReceiptLineRow(line: DraftLine, modifier: Modifier = Modifier) {
    val lineTotal = line.price * line.qty

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = "${line.qty}x ${line.name}", color = WhiteColor, fontSize = 16.sp)
            Text(
                text = "${line.price.toMoney()} / item",
                color = WhiteColor.copy(alpha = 0.75f),
                fontSize = 13.sp
            )
        }

        Text(
            text = lineTotal.toMoney(),
            color = WhiteColor,
            fontWeight = FontWeight.SemiBold
        )
    }
}