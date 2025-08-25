package com.leovp.discovery.presentation.discovery

import androidx.lifecycle.viewModelScope
import com.leovp.compose.composable.event.UiEventManager
import com.leovp.discovery.domain.model.PlaylistModel
import com.leovp.discovery.domain.model.PrivateContentModel
import com.leovp.discovery.domain.model.TopSongModel
import com.leovp.discovery.domain.usecase.GetDiscoveryListUseCase
import com.leovp.discovery.presentation.discovery.DiscoveryViewModel.DiscoveryAction
import com.leovp.discovery.presentation.discovery.base.DiscoveryContract.DiscoveryUiEvent
import com.leovp.discovery.presentation.discovery.base.DiscoveryContract.DiscoveryUiState
import com.leovp.feature.base.framework.BaseAction
import com.leovp.feature.base.framework.BaseViewModel
import com.leovp.feature.base.ui.Screen
import com.leovp.log.base.e
import com.leovp.log.base.i
import com.leovp.log.base.w
import com.leovp.network.http.exception.ResultException
import com.leovp.network.http.exceptionOrNull
import com.leovp.network.http.getOrDefault
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.net.URLEncoder
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
    companion object {
        private const val TAG = "DisVM"
    }

    // Avoid recompose when pop back to this screen.
    // Because when pop back to this screen,
    // the NavGraphBuilder.composable() will be called again.
    init {
        loadData()
    }

    fun onEvent(event: DiscoveryUiEvent) {
        viewModelScope.launch {
            when (event) {
                DiscoveryUiEvent.Refresh -> loadData(forceRefresh = true)

                is DiscoveryUiEvent.CarouselItemClick -> {
                    showToast("Carousel recommend clickedItem: ${event.data}")
                }

                is DiscoveryUiEvent.PersonalItemClick -> {
                    val artist =
                        URLEncoder.encode(
                            event.data.getDefaultArtistName(),
                            Charsets.UTF_8.name(),
                        )
                    val track =
                        URLEncoder.encode(
                            event.data.name,
                            Charsets.UTF_8.name(),
                        )
                    i(TAG) {
                        "Click [Personal Item] artist=$artist track=$track"
                    }
                    navigate(
                        Screen.PlayerScreen.routeName,
                        "${event.data.id}/$artist/$track",
                    )
                }

                is DiscoveryUiEvent.RecommendsItemClick -> {
                    showToast("Everyday recommend clickedItem: ${event.data}")
                }
            }
        }
    }

    private fun loadData(forceRefresh: Boolean = false) {
        val uiState = uiStateFlow.value as DiscoveryUiState.Content
        i(TAG) {
            "loadData(forceRefresh=$forceRefresh) uiState.isLoading=${uiState.isLoading}"
        }
        if (!forceRefresh && uiState.isLoading) {
            w(TAG) { "The data is loading now. Ignore loading." }
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

                val exceptions =
                    listOfNotNull(
                        privateContentResult.exceptionOrNull(),
                        recommendPlaylistResult.exceptionOrNull(),
                        topSongsResult.exceptionOrNull(),
                    )

                if (exceptions.isNotEmpty()) {
                    sendAction(DiscoveryAction.LoadFailure(exceptions.first()))
                    exceptions.drop(1).forEach {
                        e(TAG, it) {
                            "Additional exception occurred during data loading"
                        }
                    }
                } else {
                    val successAction =
                        DiscoveryAction.LoadSuccess(
                            privateContent =
                                privateContentResult.getOrDefault(emptyList()),
                            recommendPlaylist =
                                recommendPlaylistResult.getOrDefault(emptyList()),
                            topSongs = topSongsResult.getOrDefault(emptyList()),
                        )
                    sendAction(successAction)
                }
            } finally {
                hideLoading()
            }
        }
    }

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
