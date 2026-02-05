package com.example.myfirstcafe.feature.addedititem

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfirstcafe.data.local.entity.MenuItemEntity
import com.example.myfirstcafe.data.repository.MenuRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditItemViewModel @Inject constructor(
    private val repo: MenuRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddEditItemUiState())
    val uiState: StateFlow<AddEditItemUiState> = _uiState.asStateFlow()

    private var currentItemId: Long = Long.MIN_VALUE

    init {
        viewModelScope.launch {
            repo.observeCategories().collect { categories ->
                _uiState.update { it.copy(categories = categories) }
            }
        }
    }

    fun init(itemId: Long) {
        if (currentItemId == itemId) return
        currentItemId = itemId

        _uiState.update { it.copy(isEdit = itemId != -1L) }

        if (itemId != -1L) {
            viewModelScope.launch {
                val item = repo.getItemById(itemId)
                if (item != null) {
                    _uiState.update {
                        it.copy(
                            name = item.name,
                            priceText = item.price.toString(),
                            selectedCategoryId = item.categoryId
                        )
                    }
                }
            }
        } else {
            _uiState.update {
                it.copy(
                    name = "",
                    priceText = "",
                    selectedCategoryId = null
                )
            }
        }
    }

    fun onNameChange(value: String) = _uiState.update { it.copy(name = value) }
    fun onPriceChange(value: String) = _uiState.update { it.copy(priceText = value) }
    fun onCategorySelected(categoryId: Long) = _uiState.update { it.copy(selectedCategoryId = categoryId) }

    fun save(onSaved: () -> Unit, onValidationError: () -> Unit) {
        val state = _uiState.value
        val name = state.name.trim()
        val price = state.priceText.toDoubleOrNull()
        val categoryId = state.selectedCategoryId

        if (name.isBlank() || price == null || categoryId == null) {
            onValidationError()
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true) }

            val entity = if (currentItemId == -1L) {
                MenuItemEntity(name = name, price = price, categoryId = categoryId)
            } else {
                MenuItemEntity(id = currentItemId, name = name, price = price, categoryId = categoryId)
            }

            repo.upsertItem(entity)

            _uiState.update { it.copy(isSaving = false) }
            onSaved()
        }
    }
}