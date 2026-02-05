package com.example.myfirstcafe.core.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myfirstcafe.core.ui.theme.GrayColor
import com.example.myfirstcafe.core.ui.theme.WhiteColor
import com.example.myfirstcafe.core.ui.strings.AppStrings

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit
) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp)),
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = AppStrings.SEARCH,
                tint = WhiteColor
            )
        },
        placeholder = {
            Text(
                text = AppStrings.SEARCH_PLACEHOLDER,
                color = WhiteColor.copy(alpha = 0.6f)
            )
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = GrayColor,
            unfocusedContainerColor = GrayColor,
            disabledContainerColor = GrayColor,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = WhiteColor,
            focusedTextColor = WhiteColor,
            unfocusedTextColor = WhiteColor
        )
    )
}