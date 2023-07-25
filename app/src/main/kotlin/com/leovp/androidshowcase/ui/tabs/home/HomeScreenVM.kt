package com.leovp.androidshowcase.ui.tabs.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leovp.androidshowcase.ui.tabs.home.data.SimpleListItemModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Author: Michael Leo
 * Date: 2023/7/24 15:12
 */

/**
 * UI state for the Home screen
 */
data class HomeUiState(
    val personalRecommends: List<SimpleListItemModel> = emptyList(),
    val loading: Boolean = false
)

class HomeScreenVM(private val repository: HomeRepository) : ViewModel() {
    // UI state exposed to the UI
    private val _uiState = MutableStateFlow(HomeUiState(loading = true))
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        refreshAll()
    }

    private fun refreshAll() {
        _uiState.update { it.copy(loading = true) }

        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    loading = false,
                    personalRecommends = repository.personalRecommendedMusic
                )
            }
        }
    }

    /**
     * Factory for HomeScreenVM that takes HomeRepository as a dependency
     */
    // companion object {
    //     fun provideFactory(
    //         repository: HomeRepository,
    //     ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
    //         @Suppress("UNCHECKED_CAST")
    //         override fun <T : ViewModel> create(modelClass: Class<T>): T {
    //             return HomeScreenVM(repository) as T
    //         }
    //     }
    // }
}
