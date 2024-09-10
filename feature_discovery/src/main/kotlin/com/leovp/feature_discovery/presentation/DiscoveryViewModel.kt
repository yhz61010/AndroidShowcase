package com.leovp.feature_discovery.presentation

import androidx.annotation.Keep
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leovp.feature_discovery.domain.model.PlaylistModel
import com.leovp.feature_discovery.domain.model.PrivateContentModel
import com.leovp.feature_discovery.domain.model.TopSongModel
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
            val privateContentDeferred = async { useCase.getPrivateContent() }
            val recommendPlaylistDeferred = async { useCase.getRecommendPlaylist() }
            val topSongsDeferred = async { useCase.getTopSongs() }

            val privateContentResult = privateContentDeferred.await()
            val recommendPlaylistResult = recommendPlaylistDeferred.await()
            val topSongsResult = topSongsDeferred.await()

            val ex = privateContentResult.exceptionOrNull()
                ?: recommendPlaylistResult.exceptionOrNull()
                ?: topSongsResult.exceptionOrNull()

            _uiState.update {
                it.copy(
                    loading = false,
                    privateContent = privateContentResult.getOrDefault(emptyList()),
                    recommendPlaylist = recommendPlaylistResult.getOrDefault(emptyList()),
                    topSongs = topSongsResult.getOrDefault(emptyList()),
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
    val privateContent: List<PrivateContentModel> = emptyList(),
    val recommendPlaylist: List<PlaylistModel> = emptyList(),
    val topSongs: List<TopSongModel> = emptyList(),
    val loading: Boolean = false,
    val exception: Throwable? = null
)