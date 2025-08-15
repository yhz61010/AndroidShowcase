package com.leovp.feature_discovery.presentation

import androidx.annotation.Keep
import androidx.lifecycle.viewModelScope
import com.leovp.feature_discovery.domain.model.PlaylistModel
import com.leovp.feature_discovery.domain.model.PrivateContentModel
import com.leovp.feature_discovery.domain.model.TopSongModel
import com.leovp.feature_discovery.domain.usecase.GetDiscoveryListUseCase
import com.leovp.feature_discovery.presentation.DiscoveryViewModel.UiState
import com.leovp.feature_discovery.presentation.DiscoveryViewModel.UiState.Content
import com.leovp.log.base.i
import com.leovp.mvvm.viewmodel.BaseAction
import com.leovp.mvvm.viewmodel.BaseState
import com.leovp.mvvm.viewmodel.BaseViewModel
import com.leovp.network.http.exception.ResultException
import com.leovp.network.http.exceptionOrNull
import com.leovp.network.http.getOrDefault
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Author: Michael Leo
 * Date: 2023/7/24 15:12
 */

@HiltViewModel
class DiscoveryViewModel @Inject constructor(
    private val useCase: GetDiscoveryListUseCase,
) : BaseViewModel<UiState, BaseAction<UiState>>(Content()) {

    companion object {
        private const val TAG = "DisVM"
    }

    private var job: Job? = null

    init {
        onEnter()
    }

    fun showLoading() {
        sendAction(Action.ShowLoading)
    }

    fun onEnter() {
        i(TAG) { "Discovery -> refreshAll()" }
        if (job != null) {
            job?.cancel()
            job = null
        }

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

            if (ex != null) {
                sendAction(Action.LoadFailure(ex))
            } else {
                sendAction(
                    Action.LoadSuccess(
                        privateContent = privateContentResult.getOrDefault(emptyList()),
                        recommendPlaylist = recommendPlaylistResult.getOrDefault(emptyList()),
                        topSongs = topSongsResult.getOrDefault(emptyList()),
                    )
                )
            }
        }
    }

    sealed interface Action : BaseAction<UiState> {
        object ShowLoading : Action {
            override fun execute(state: UiState): UiState {
                val uiState = state as Content
                return Content(
                    privateContent = uiState.privateContent,
                    recommendPlaylist = uiState.recommendPlaylist,
                    topSongs = uiState.topSongs,
                    isLoading = true,
                    exception = uiState.exception
                )
            }
        }

        class LoadSuccess(
            val privateContent: List<PrivateContentModel> = emptyList(),
            val recommendPlaylist: List<PlaylistModel> = emptyList(),
            val topSongs: List<TopSongModel> = emptyList(),
        ) : Action {
            override fun execute(state: UiState): UiState = Content(
                privateContent = privateContent,
                recommendPlaylist = recommendPlaylist,
                topSongs = topSongs,
                isLoading = false,
                exception = null
            )
        }

        class LoadFailure(private val err: ResultException) : Action {
            override fun execute(state: UiState): UiState {
                val uiState = state as Content
                return Content(
                    privateContent = uiState.privateContent,
                    recommendPlaylist = uiState.recommendPlaylist,
                    topSongs = uiState.topSongs,
                    isLoading = false,
                    exception = err
                )
            }
        }
    }

    @Keep
    sealed interface UiState : BaseState {
        data class Content(
            val privateContent: List<PrivateContentModel> = emptyList(),
            val recommendPlaylist: List<PlaylistModel> = emptyList(),
            val topSongs: List<TopSongModel> = emptyList(),
            val isLoading: Boolean = false,
            val exception: ResultException? = null
        ) : UiState
    }
}