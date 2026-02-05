package com.example.myfirstcafe.feature.pricelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfirstcafe.data.repository.MenuRepository
import com.example.myfirstcafe.data.seed.SeedData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PriceListViewModel @Inject constructor(
    private val repo: MenuRepository
) : ViewModel() {

    private val queryFlow = MutableStateFlow("")
    private val expandedFlow = MutableStateFlow<Map<Long, Boolean>>(emptyMap())
    private val categoriesFlow = repo.observeCategoriesWithItems()

    private val searchFlow = queryFlow
        .debounce(250)
        .distinctUntilChanged()
        .flatMapLatest { q ->
            val trimmed = q.trim()
            if (trimmed.length >= 3) repo.observeSearch(trimmed)
            else flowOf(emptyList())
        }

    val uiState: StateFlow<PriceListUiState> = combine(
        queryFlow,
        categoriesFlow,
        searchFlow,
        expandedFlow
    ) { query, categoriesWithItems, searchResults, expandedMap ->

        val normalizedExpanded = buildMap {
            categoriesWithItems.forEach { cwi ->
                val id = cwi.category.id
                put(id, expandedMap[id] ?: false)
            }
        }

        PriceListUiState(
            query = query,
            categories = categoriesWithItems,
            searchResults = searchResults,
            expanded = normalizedExpanded
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = PriceListUiState()
    )

    init {
        viewModelScope.launch {
            repo.seedIfEmpty(
                categories = SeedData.categories,
                items = SeedData.items
            )
        }
    }

    fun onQueryChange(newQuery: String) {
        queryFlow.value = newQuery
    }

    fun onToggleCategory(categoryId: Long) {
        val current = expandedFlow.value[categoryId] ?: false
        expandedFlow.value = expandedFlow.value.toMutableMap().apply {
            this[categoryId] = !current
        }
    }

    fun deleteItem(id: Long) {
        viewModelScope.launch {
            repo.deleteItem(id)
        }
    }
}