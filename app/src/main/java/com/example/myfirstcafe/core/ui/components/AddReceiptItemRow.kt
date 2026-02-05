package com.example.myfirstcafe.core.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfirstcafe.core.ui.strings.AppStrings
import com.example.myfirstcafe.core.ui.theme.WhiteColor
import com.example.myfirstcafe.core.ui.util.toMoney
import com.example.myfirstcafe.data.local.entity.MenuItemEntity

@Composable
fun AddReceiptItemRow(
    item: MenuItemEntity,
    quantity: Int,
    onPlus: () -> Unit,
    onMinus: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = item.name, color = WhiteColor, fontSize = 16.sp)
            Text(
                text = item.price.toMoney(),
                color = WhiteColor.copy(alpha = 0.75f),
                fontSize = 13.sp
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onMinus, enabled = quantity > 0) {
                Icon(Icons.Default.Remove, contentDescription = AppStrings.MINUS, tint = WhiteColor)
            }

            Text(
                text = quantity.toString(),
                color = WhiteColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.widthIn(min = 24.dp)
            )

            IconButton(onClick = onPlus) {
                Icon(Icons.Default.Add, contentDescription = AppStrings.PLUS, tint = WhiteColor)
            }
        }
    }
}