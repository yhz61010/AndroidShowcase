package com.leovp.feature_discovery.presentation

import androidx.annotation.Keep
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leovp.feature_discovery.domain.model.CarouselItem
import com.leovp.feature_discovery.domain.model.EverydayItem
import com.leovp.feature_discovery.domain.model.MusicItem
import com.leovp.feature_discovery.domain.usecase.GetDiscoveryListUseCase
import com.leovp.module.common.exceptionOrNull
import com.leovp.module.common.getOrDefault
import com.leovp.module.common.log.i
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Author: Michael Leo
 * Date: 2023/7/24 15:12
 */

@HiltViewModel
class DiscoveryViewModel @Inject constructor(
    private val useCase: GetDiscoveryListUseCase,
) : ViewModel() {

    companion object {
        private const val TAG = "DisVM"
    }

    // Backing property to avoid state updates from other classes
    private val _uiState = MutableStateFlow(DiscoveryUiState(loading = true))

    // UI state exposed to the UI
    val uiState: StateFlow<DiscoveryUiState> = _uiState.asStateFlow()

    private var job: Job? = null

    init {
        refreshAll()
    }

    fun refreshAll() {
        i(TAG) { "Discovery -> refreshAll()" }
        if (job != null) {
            job?.cancel()
            job = null
        }

        _uiState.update { it.copy(loading = true) }

        job = viewModelScope.launch {
            val carouselDeferred = async { useCase.getCarouselMusic() }
            val everydayDeferred = async { useCase.getEverydayMusic() }
            val personalDeferred = async { useCase.getPersonalMusic() }

            val carouselRecommendsResult = carouselDeferred.await()
            val everydayRecommendsResult = everydayDeferred.await()
            val personalRecommendsResult = personalDeferred.await()

            val ex = carouselRecommendsResult.exceptionOrNull()
                ?: everydayRecommendsResult.exceptionOrNull()
                ?: personalRecommendsResult.exceptionOrNull()

            _uiState.update {
                it.copy(
                    loading = false,
                    carouselRecommends = carouselRecommendsResult.getOrDefault(emptyList()),
                    everydayRecommends = everydayRecommendsResult.getOrDefault(emptyList()),
                    personalRecommends = personalRecommendsResult.getOrDefault(emptyList()),
                    exception = ex
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
    val loading: Boolean = false,
    val exception: Throwable? = null
)