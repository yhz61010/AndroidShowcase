package com.leovp.androidshowcase.ui.tabs.discovery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leovp.androidshowcase.ui.tabs.discovery.data.CarouselItemModel
import com.leovp.androidshowcase.ui.tabs.discovery.data.DiscoveryRepository
import com.leovp.androidshowcase.ui.tabs.discovery.data.EverydayItemModel
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
 * UI state for the Discovery screen
 */
data class DiscoveryUiState(
    val personalRecommends: List<SimpleListItemModel> = emptyList(),
    val carouselRecommends: List<CarouselItemModel> = emptyList(),
    val everydayRecommends: List<EverydayItemModel> = emptyList(),
    val loading: Boolean = false
)

class DiscoveryVM(private val repository: DiscoveryRepository) : ViewModel() {

    // Backing property to avoid state updates from other classes
    private val _uiState = MutableStateFlow(DiscoveryUiState(loading = true))
    // UI state exposed to the UI
    val uiState: StateFlow<DiscoveryUiState> = _uiState.asStateFlow()

    init {
        refreshAll()
    }

    private fun refreshAll() {
        _uiState.update { it.copy(loading = true) }

        viewModelScope.launch {
            val personalRecommendsDeferred = async { repository.getPersonalRecommends() }
            val carouselRecommendsDeferred = async { repository.getCarouselRecommends() }
            val everydayRecommendsDeferred = async { repository.getEverydayRecommends() }

            val personalRecommends = personalRecommendsDeferred.await().successOr(emptyList())
            val carouselRecommends = carouselRecommendsDeferred.await().successOr(emptyList())
            val everydayRecommends = everydayRecommendsDeferred.await().successOr(emptyList())
            _uiState.update {
                it.copy(
                    loading = false,
                    personalRecommends = personalRecommends,
                    carouselRecommends = carouselRecommends,
                    everydayRecommends = everydayRecommends
                )
            }
        }
    }

    /**
     * Factory for DiscoveryScreenVM that takes DiscoveryRepository as a dependency
     */
    // companion object {
    //     fun provideFactory(
    //         repository: DiscoveryRepository,
    //     ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
    //         @Suppress("UNCHECKED_CAST")
    //         override fun <T : ViewModel> create(modelClass: Class<T>): T {
    //             return DiscoveryScreenVM(repository) as T
    //         }
    //     }
    // }
}
