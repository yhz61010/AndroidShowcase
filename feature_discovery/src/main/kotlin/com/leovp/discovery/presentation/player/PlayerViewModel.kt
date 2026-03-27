package com.leovp.discovery.presentation.player

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.leovp.discovery.domain.model.SongModel
import com.leovp.discovery.domain.usecase.PlayerUseCase
import com.leovp.discovery.presentation.player.PlayerViewModel.PlayerAction
import com.leovp.discovery.presentation.player.base.PlayerContract.PlayerUiEvent
import com.leovp.discovery.presentation.player.base.PlayerContract.PlayerUiState
import com.leovp.discovery.presentation.player.base.PlayerDelegateManager
import com.leovp.feature.base.ui.Screen
import com.leovp.json.toJsonString
import com.leovp.log.base.d
import com.leovp.log.base.e
import com.leovp.log.base.i
import com.leovp.log.base.w
import com.leovp.mvvm.BaseAction
import com.leovp.mvvm.BaseViewModel
import com.leovp.mvvm.event.base.UiEventManager
import com.leovp.network.http.exception.ResultException
import com.leovp.network.http.exceptionOrNull
import com.leovp.network.http.fold
import com.leovp.network.http.getOrNull
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Author: Michael Leo
 * Date: 2024/8/26 13:54
 */

@HiltViewModel
class PlayerViewModel
    @Inject
    constructor(
        savedStateHandle: SavedStateHandle,
        uiEventManager: UiEventManager,
        private val useCase: PlayerUseCase,
        private val delegateManager: PlayerDelegateManager,
    ) : BaseViewModel<PlayerUiState, PlayerAction>(
            PlayerUiState.Content(),
            uiEventManager,
        ) {
        override fun getTagName() = "PlayerVM"

        val playSongParam = savedStateHandle.toRoute<Screen.Player>()

        private val _playPositionState = MutableStateFlow(0f)
        val playPositionState: StateFlow<Float> = _playPositionState.asStateFlow()

        init {
            loadData(id = playSongParam.id)
        }

        fun onEvent(event: PlayerUiEvent) {
            viewModelScope.launch {
                delegateManager.handleEvent(event)
            }
        }

        fun loadData(id: Long) {
            i(tag) { "Player -> getData() id=$id" }

            viewModelScope.launch {
                val songAvailableResult = useCase.checkMusic(id, 999000)

                songAvailableResult.fold(
                    onSuccess = { songAvailModel ->
                        onSongAvailableSuccess(id, songAvailModel)
                        d(tag) { "Player -> getData() done." }
                    },
                    onFailure = { e ->
                        e(tag, e) { "Song check error. msg=${e.message}" }
                        sendAction(
                            PlayerAction.LoadContent(
                                songInfo = null,
                                exception = e,
                            ),
                        )
                    },
                )
            }
        }

        private suspend fun CoroutineScope.onSongAvailableSuccess(
            id: Long,
            songAvailModel: SongModel.MusicAvailableModel,
        ) {
            if (!songAvailModel.success) {
                val ex = ResultException(message = songAvailModel.message)
                e(tag, ex) { "Song check business error.  msg=${ex.message}" }
                sendAction(PlayerAction.LoadContent(songInfo = null, exception = ex))
                return
            }

            val songUrlDeferred =
                async { useCase.getSongUrlV1(id, SongModel.Quality.Standard) }
            val songInfoDeferred = async { useCase.getSongInfo(id) }
            val songCommentsDeferred =
                async { useCase.getMusicComment(id = id, limit = 20, offset = 0) }
            val songRedCountDeferred = async { useCase.getSongRedCount(id) }
            val songUrlResult = songUrlDeferred.await()
            val songInfoResult = songInfoDeferred.await()
            val songCommentsResult = songCommentsDeferred.await()
            val songRedCountResult = songRedCountDeferred.await()

            val firstSong: SongModel? =
                songInfoResult
                    .getOrNull()
                    ?.firstOrNull()
                    ?.also { firstSongRef ->
                        firstSongRef.urlModel = songUrlResult.getOrNull()?.firstOrNull()
                        d(
                            tag,
                        ) { "---> UrlModel: ${firstSongRef.urlModel.toJsonString()}" }
                        firstSongRef.commentsModel = songCommentsResult.getOrNull()
                        firstSongRef.redCountModel = songRedCountResult.getOrNull()
                    }

            var ex =
                songInfoResult.exceptionOrNull()
                    ?: songUrlResult.exceptionOrNull()
                    ?: songCommentsResult.exceptionOrNull()
                    ?: songRedCountResult.exceptionOrNull()

            if (firstSong?.getUrlSuccess() != true) {
                w(tag) {
                    "Failed to get song url. " +
                        "code=${firstSong?.getUrlCode()} " +
                        "url=${firstSong?.getUrl()}"
                }
                ex = ResultException(message = "Failed to get song url.", cause = ex)
            }
            sendAction(PlayerAction.LoadContent(songInfo = firstSong, exception = ex))
        }

        fun updatePlayPos(pos: Float) {
            _playPositionState.value = pos
        }

        // ==============================

        sealed interface PlayerAction : BaseAction.Simple<PlayerUiState> {
            data class LoadContent(
                val songInfo: SongModel? = null,
                val exception: ResultException? = null,
            ) : PlayerAction {
                override fun reduce(state: PlayerUiState): PlayerUiState =
                    PlayerUiState.Content(
                        songInfo = songInfo,
                        exception = exception,
                    )
            }
        }
    }
