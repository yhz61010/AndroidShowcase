package com.leovp.feature_discovery.presentation

import androidx.annotation.Keep
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leovp.feature_discovery.domain.model.CarouselItem
import com.leovp.feature_discovery.domain.model.EverydayItem
import com.leovp.feature_discovery.domain.model.MusicItem
import com.leovp.feature_discovery.domain.usecase.GetDiscoveryListUseCase
import com.leovp.log.LogContext
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

class DiscoveryViewModel(private val useCase: GetDiscoveryListUseCase) : ViewModel() {

    companion object {
        private const val TAG = "DisVM"
    }

    // Backing property to avoid state updates from other classes
    private val _uiState = MutableStateFlow(DiscoveryUiState(loading = true))

    // UI state exposed to the UI
    val uiState: StateFlow<DiscoveryUiState> = _uiState.asStateFlow()

    init {
        refreshAll()
    }

    fun refreshAll() {
        LogContext.log.i(TAG, "Discovery -> refreshAll()")
        _uiState.update { it.copy(loading = true) }

        viewModelScope.launch {
            val carouselDeferred = async { useCase.getCarouselRecommends() }
            val everydayDeferred = async { useCase.getEverydayRecommends() }
            val personalDeferred = async { useCase.getPersonalRecommends() }

            val carouselRecommends = carouselDeferred.await().successOr(emptyList())
            val everydayRecommends = everydayDeferred.await().successOr(emptyList())
            val personalRecommends = personalDeferred.await().successOr(emptyList())

            _uiState.update {
                it.copy(
                    loading = false,
                    carouselRecommends = carouselRecommends,
                    everydayRecommends = everydayRecommends,
                    personalRecommends = personalRecommends,
                )
            }
        }
    }
}

/**
 * UI state for the Discovery screen
 */
@Keep
data class DiscoveryUiState(
    val carouselRecommends: List<CarouselItem> = emptyList(),
    val everydayRecommends: List<EverydayItem> = emptyList(),
    val personalRecommends: List<MusicItem> = emptyList(),
    val loading: Boolean = false
)