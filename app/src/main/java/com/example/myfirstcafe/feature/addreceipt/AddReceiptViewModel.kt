package com.example.myfirstcafe.feature.addreceipt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfirstcafe.data.repository.MenuRepository
import com.example.myfirstcafe.feature.addreceipt.model.ReceiptDraft
import com.example.myfirstcafe.feature.addreceipt.model.toDraftLine
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddReceiptViewModel @Inject constructor(
    private val menuRepo: MenuRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddReceiptUiState())
    val uiState: StateFlow<AddReceiptUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            menuRepo.observeCategoriesWithItems().collect { categories ->
                _uiState.update { old ->
                    val normalizedExpanded = buildMap {
                        categories.forEach { cwi ->
                            val id = cwi.category.id
                            put(id, old.expanded[id] ?: false)
                        }
                    }
                    old.copy(categories = categories, expanded = normalizedExpanded)
                }
            }
        }
    }

    fun toggleCategory(categoryId: Long) {
        _uiState.update { old ->
            val current = old.expanded[categoryId] ?: false
            old.copy(expanded = old.expanded.toMutableMap().apply { this[categoryId] = !current })
        }
    }

    fun inc(itemId: Long) {
        _uiState.update { old ->
            val current = old.quantities[itemId] ?: 0
            old.copy(quantities = old.quantities.toMutableMap().apply { this[itemId] = current + 1 })
        }
    }

    fun dec(itemId: Long) {
        _uiState.update { old ->
            val current = old.quantities[itemId] ?: 0
            if (current <= 0) old
            else old.copy(quantities = old.quantities.toMutableMap().apply { this[itemId] = current - 1 })
        }
    }

    fun buildDraft(): ReceiptDraft {
        val state = _uiState.value
        val qtyMap = state.quantities

        val lines = buildList {
            state.categories.forEach { cwi ->
                cwi.items.forEach { item ->
                    val qty = qtyMap[item.id] ?: 0
                    if (qty > 0) add(item.toDraftLine(qty))
                }
            }
        }
        return ReceiptDraft(lines)
    }
}