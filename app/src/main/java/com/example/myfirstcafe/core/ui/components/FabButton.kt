package com.example.myfirstcafe.core.ui.components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myfirstcafe.core.ui.theme.OrangeColor
import com.example.myfirstcafe.core.ui.theme.WhiteColor
import com.example.myfirstcafe.core.ui.strings.AppStrings

@Composable
fun FabButton (
    modifier: Modifier = Modifier,
    onClick: () -> Unit) {

    FloatingActionButton(
        onClick = onClick,
        modifier = modifier,
        shape = CircleShape,
        containerColor = OrangeColor,
        elevation = FloatingActionButtonDefaults.elevation(
            defaultElevation = 8.dp,
            pressedElevation = 12.dp
        )
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = AppStrings.ADD,
            tint = WhiteColor
        )
    }

}