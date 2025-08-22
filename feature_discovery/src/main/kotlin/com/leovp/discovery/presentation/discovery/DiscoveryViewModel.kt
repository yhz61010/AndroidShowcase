package com.leovp.discovery.presentation.discovery

import androidx.annotation.Keep
import androidx.lifecycle.viewModelScope
import com.leovp.discovery.domain.model.PlaylistModel
import com.leovp.discovery.domain.model.PrivateContentModel
import com.leovp.discovery.domain.model.TopSongModel
import com.leovp.discovery.domain.usecase.GetDiscoveryListUseCase
import com.leovp.discovery.presentation.discovery.DiscoveryViewModel.DiscoveryAction
import com.leovp.discovery.presentation.discovery.DiscoveryViewModel.DiscoveryUiState
import com.leovp.feature.base.event.UiEventManager
import com.leovp.feature.base.framework.BaseAction
import com.leovp.feature.base.framework.BaseState
import com.leovp.feature.base.framework.BaseViewModel
import com.leovp.feature.base.ui.AppNavigationActions
import com.leovp.feature.base.ui.Screen
import com.leovp.log.base.i
import com.leovp.log.base.w
import com.leovp.network.http.exception.ResultException
import com.leovp.network.http.exceptionOrNull
import com.leovp.network.http.getOrDefault
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
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

    private var job: Job? = null

    // Avoid recompose when pop back to this screen.
    // Because when pop back to this screen,
    // the NavGraphBuilder.composable() will be called again.
    init {
        loadData()
    }

    fun onEvent(
        event: DiscoveryUiEvent,
        navController: AppNavigationActions? = null,
    ) {
        viewModelScope.launch {
            when (event) {
                DiscoveryUiEvent.Refresh -> loadData(forceRefresh = true)

                is DiscoveryUiEvent.CarouselItemClick -> {
                    showToast("Carousel recommend clickedItem: ${event.data}")
                }

                is DiscoveryUiEvent.PersonalItemClick -> {
                    checkNotNull(navController)
                    val artist =
                        URLEncoder.encode(
                            event.data.getDefaultArtistName(),
                            "UTF-8",
                        )
                    val track = URLEncoder.encode(event.data.name, "UTF-8")
                    i(TAG) { "Click [Personal Item] artist=$artist  track=$track" }
                    navController.navigate(
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
        if (job != null) {
            job?.cancel()
            job = null
        }

        job =
            viewModelScope.launch {
                val privateContentDeferred =
                    async { useCase.getPrivateContent() }
                val recommendPlaylistDeferred =
                    async { useCase.getRecommendPlaylist() }
                val topSongsDeferred = async { useCase.getTopSongs() }

                val privateContentResult = privateContentDeferred.await()
                val recommendPlaylistResult = recommendPlaylistDeferred.await()
                val topSongsResult = topSongsDeferred.await()

                val ex =
                    privateContentResult.exceptionOrNull()
                        ?: recommendPlaylistResult.exceptionOrNull()
                        ?: topSongsResult.exceptionOrNull()

                if (ex != null) {
                    sendAction(DiscoveryAction.LoadFailure(ex))
                } else {
                    sendAction(
                        DiscoveryAction.LoadSuccess(
                            privateContent =
                                privateContentResult.getOrDefault(emptyList()),
                            recommendPlaylist =
                                recommendPlaylistResult.getOrDefault(emptyList()),
                            topSongs = topSongsResult.getOrDefault(emptyList()),
                        ),
                    )
                }
                hideLoading()
            }
    }

    sealed interface DiscoveryUiEvent {
        data class CarouselItemClick(
            val data: PrivateContentModel,
        ) : DiscoveryUiEvent

        data class RecommendsItemClick(
            val data: PlaylistModel,
        ) : DiscoveryUiEvent

        data class PersonalItemClick(
            val data: TopSongModel,
        ) : DiscoveryUiEvent

        data object Refresh : DiscoveryUiEvent
    }

    sealed interface DiscoveryAction : BaseAction.Simple<DiscoveryUiState> {
        object ShowLoading : DiscoveryAction {
            override fun reduce(state: DiscoveryUiState): DiscoveryUiState {
                val uiState = state as DiscoveryUiState.Content
                return uiState.copy(isLoading = true)
            }
        }

        class LoadSuccess(
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

        class LoadFailure(
            private val err: ResultException,
        ) : DiscoveryAction {
            override fun reduce(state: DiscoveryUiState): DiscoveryUiState {
                val discoveryUiState = state as DiscoveryUiState.Content
                return discoveryUiState.copy(isLoading = false, exception = err)
            }
        }
    }

    @Keep
    sealed interface DiscoveryUiState : BaseState {
        data class Content(
            val privateContent: List<PrivateContentModel> = emptyList(),
            val recommendPlaylist: List<PlaylistModel> = emptyList(),
            val topSongs: List<TopSongModel> = emptyList(),
            val isLoading: Boolean = false,
            val exception: ResultException? = null,
        ) : DiscoveryUiState
    }
}
