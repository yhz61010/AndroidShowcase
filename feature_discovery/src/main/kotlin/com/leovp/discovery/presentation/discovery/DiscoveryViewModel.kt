package com.leovp.discovery.presentation.discovery

import androidx.lifecycle.viewModelScope
import com.leovp.discovery.domain.model.PlaylistModel
import com.leovp.discovery.domain.model.PrivateContentModel
import com.leovp.discovery.domain.model.TopSongModel
import com.leovp.discovery.domain.usecase.GetDiscoveryListUseCase
import com.leovp.discovery.presentation.discovery.DiscoveryViewModel.DiscoveryAction
import com.leovp.discovery.presentation.discovery.base.DiscoveryContract.DiscoveryUiEvent
import com.leovp.discovery.presentation.discovery.base.DiscoveryContract.DiscoveryUiState
import com.leovp.feature.base.ui.Screen
import com.leovp.log.base.i
import com.leovp.log.base.w
import com.leovp.mvvm.BaseAction
import com.leovp.mvvm.BaseViewModel
import com.leovp.mvvm.event.base.UiEventManager
import com.leovp.mvvm.http.extractBizData
import com.leovp.network.http.ResultBiz
import com.leovp.network.http.exception.ResultException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Author: Michael Leo
 * Date: 2023/7/24 15:12
 */

@HiltViewModel
class DiscoveryViewModel
@Inject
constructor(
    private val useCase: GetDiscoveryListUseCase,
    uiEventManager: UiEventManager,
) : BaseViewModel<DiscoveryUiState, DiscoveryAction>(
    initialState = DiscoveryUiState.Content(),
    uiEventManager = uiEventManager,
) {
    override fun getTagName() = "DisVM"

    // Avoid recompose when pop back to this screen.
    // Because when pop back to this screen,
    // the NavGraphBuilder.composable() will be called again.
    init {
        onEvent(DiscoveryUiEvent.Refresh)
    }

    fun onEvent(event: DiscoveryUiEvent) {
        viewModelScope.launch {
            when (event) {
                DiscoveryUiEvent.Refresh -> loadData(forceRefresh = true)

                is DiscoveryUiEvent.CarouselItemClick -> {
                    showToast("Carousel recommend clickedItem: ${event.data}")
                }

                is DiscoveryUiEvent.PersonalItemClick -> {
                    val songId = event.data.id
                    val songName = event.data.name
                    val artist = event.data.getDefaultArtistName()
                    val track = event.data.name
                    i(tag) {
                        "Click [Personal Item] song[$songId]=$songName artist=$artist track=$track"
                    }
                    val songParam = Screen.Player(
                        id = songId,
                        artist = artist,
                        track = track
                    )
                    navigate(songParam)
                }

                is DiscoveryUiEvent.RecommendsItemClick -> {
                    showToast("Everyday recommend clickedItem: ${event.data}")
                }
            }
        }
    }

    @Suppress("SameParameterValue")
    private fun loadData(forceRefresh: Boolean = false) {
        val uiState = uiStateFlow.value as DiscoveryUiState.Content
        i(tag) {
            "loadData(forceRefresh=$forceRefresh) uiState.isLoading=${uiState.isLoading}"
        }
        if (!forceRefresh && uiState.isLoading) {
            w(tag) { "The data is loading now. Ignore loading." }
            return
        }

        sendAction(DiscoveryAction.ShowLoading)
        viewModelScope.launch {
            try {
                val (privateContentResult, recommendPlaylistResult, topSongsResult) =
                    coroutineScope {
                        val private = async { useCase.getPrivateContent() }
                        val playlist = async { useCase.getRecommendPlaylist() }
                        val songs = async { useCase.getTopSongs() }
                        Triple(private.await(), playlist.await(), songs.await())
                    }

                val privateContent = extractData(privateContentResult) ?: return@launch
                val recommendPlaylist =
                    extractData(recommendPlaylistResult) ?: return@launch
                val topSongs = extractData(topSongsResult) ?: return@launch

                sendAction(
                    DiscoveryAction.LoadSuccess(
                        privateContent = privateContent,
                        recommendPlaylist = recommendPlaylist,
                        topSongs = topSongs,
                    )
                )
            } finally {
                hideLoading()
            }
        }
    }

    private suspend fun <T> extractData(bizResult: ResultBiz<T>): T? =
        extractBizData(uiEventManager, bizResult) { err, _ ->
            sendAction(DiscoveryAction.LoadFailure(err))
        }

    // ==============================

    sealed interface DiscoveryAction : BaseAction.Simple<DiscoveryUiState> {
        data object ShowLoading : DiscoveryAction {
            override fun reduce(state: DiscoveryUiState): DiscoveryUiState {
                val uiState = state as DiscoveryUiState.Content
                return uiState.copy(isLoading = true)
            }
        }

        data class LoadSuccess(
            val privateContent: List<PrivateContentModel> = emptyList(),
            val recommendPlaylist: List<PlaylistModel> = emptyList(),
            val topSongs: List<TopSongModel> = emptyList(),
        ) : DiscoveryAction {
            override fun reduce(state: DiscoveryUiState): DiscoveryUiState {
                val uiState = state as DiscoveryUiState.Content
                return uiState.copy(
                    privateContent = privateContent,
                    recommendPlaylist = recommendPlaylist,
                    topSongs = topSongs,
                    isLoading = false,
                    exception = null,
                )
            }
        }

        data class LoadFailure(
            private val err: ResultException,
        ) : DiscoveryAction {
            override fun reduce(state: DiscoveryUiState): DiscoveryUiState {
                val discoveryUiState = state as DiscoveryUiState.Content
                return discoveryUiState.copy(isLoading = false, exception = err)
            }
        }
    }
}
