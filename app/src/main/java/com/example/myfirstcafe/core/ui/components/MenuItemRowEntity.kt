package com.example.myfirstcafe.core.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfirstcafe.data.local.entity.MenuItemEntity
import com.example.myfirstcafe.core.ui.theme.WhiteColor
import com.example.myfirstcafe.core.ui.strings.AppStrings

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MenuItemRowEntity(
    item: MenuItemEntity,
    onClick: () -> Unit,
    onLongClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            )
            .padding(start = 16.dp, end = 16.dp, top = 6.dp, bottom = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = item.name,
            color = WhiteColor,
            fontSize = 16.sp,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = String.format(AppStrings.PRICE_EUR, item.price),
            color = WhiteColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}