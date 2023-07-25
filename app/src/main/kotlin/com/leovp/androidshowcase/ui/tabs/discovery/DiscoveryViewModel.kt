package com.leovp.androidshowcase.ui.tabs.discovery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leovp.androidshowcase.ui.tabs.discovery.data.DiscoveryRepository
import com.leovp.androidshowcase.ui.tabs.discovery.data.SimpleListItemModel
import com.leovp.module.common.successOr
import kotlinx.coroutines.async
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
    val personalRecommends: List<SimpleListItemModel> = emptyList(), val loading: Boolean = false
)

class HomeScreenVM(private val repository: DiscoveryRepository) : ViewModel() {
    // UI state exposed to the UI
    private val _uiState = MutableStateFlow(HomeUiState(loading = true))
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        refreshAll()
    }

    private fun refreshAll() {
        _uiState.update { it.copy(loading = true) }

        viewModelScope.launch {
            val personalRecommendsDeferred = async { repository.getPersonalRecommends() }

            val personalRecommends = personalRecommendsDeferred.await().successOr(emptyList())
            _uiState.update {
                it.copy(
                    loading = false,
                    personalRecommends = personalRecommends
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
