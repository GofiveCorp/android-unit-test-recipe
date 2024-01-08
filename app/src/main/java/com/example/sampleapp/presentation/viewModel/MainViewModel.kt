package com.example.sampleapp.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sampleapp.data.model.Category
import com.example.sampleapp.domain.GetCategoriesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

class MainViewModel(private val getCategoriesUseCase: GetCategoriesUseCase): ViewModel() {
    private val _categorieState = MutableStateFlow(RecipeState())
    val categoriesState: StateFlow<RecipeState> = _categorieState

    init {
        fetchCategories()
    }


    private fun fetchCategories() {
        getCategoriesUseCase.execute()
            .flowOn(Dispatchers.IO)
            .onStart {
                _categorieState.emit(RecipeState(loading = true))
            }
            .onEach { result ->
                result.fold(
                    onSuccess = {
                        _categorieState.emit(RecipeState(loading = false, list = it))
                    },
                    onFailure = {
                        _categorieState.emit(RecipeState(loading = false, error = "Error fetching Categories ${it.message}"))
                    }
                )
            }
            .catch { e ->
                _categorieState.emit(RecipeState(loading = false, error = "Error fetching Categories ${e.message}"))
            }
            .launchIn(viewModelScope)
    }


    data class RecipeState(
        val loading: Boolean = true,
        val list: List<Category> = emptyList(),
        val error: String? = null
    )
}