package com.example.myfirstcafe.feature.addedititem

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myfirstcafe.core.ui.components.CategoryDropdown
import com.example.myfirstcafe.core.ui.components.OrangeButton
import com.example.myfirstcafe.core.ui.theme.GrayColor
import com.example.myfirstcafe.core.ui.theme.WhiteColor
import com.example.myfirstcafe.core.ui.strings.AppStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditItemScreen(
    navController: NavController,
    itemId: Long,
    viewModel: AddEditItemViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(itemId) {
        viewModel.init(itemId)
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
                    contentDescription = AppStrings.BACK,
                    tint = WhiteColor
                )
            }

            Text(
                text = if (state.isEdit) AppStrings.EDIT_ITEM else AppStrings.ADD_NEW_ITEM,
                color = WhiteColor,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(48.dp))
        }

        OutlinedTextField(
            value = state.name,
            onValueChange = viewModel::onNameChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(AppStrings.ITEM_NAME, color = WhiteColor) },
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

        Spacer(modifier = Modifier.height(12.dp))

        CategoryDropdown(
            categories = state.categories,
            selectedCategoryId = state.selectedCategoryId,
            modifier = Modifier.fillMaxWidth(),
            onSelect = viewModel::onCategorySelected
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = state.priceText,
            onValueChange = viewModel::onPriceChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(AppStrings.PRICE, color = WhiteColor) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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

        Spacer(modifier = Modifier.weight(1f))

        OrangeButton(
            text = AppStrings.SAVE_ITEM,
            onClick = {
                viewModel.save(
                    onSaved = { navController.popBackStack() },
                    onValidationError = {
                        Toast.makeText(
                            context,
                            AppStrings.VALIDATION_ALL_FIELDS,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                )
            }
        )
    }
}