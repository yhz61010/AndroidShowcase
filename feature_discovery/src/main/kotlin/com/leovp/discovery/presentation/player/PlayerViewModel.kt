package com.leovp.discovery.presentation.player

import androidx.annotation.Keep
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.leovp.discovery.domain.model.SongModel
import com.leovp.discovery.domain.usecase.PlayerUseCase
import com.leovp.discovery.presentation.player.PlayerViewModel.Action
import com.leovp.discovery.presentation.player.PlayerViewModel.UiState
import com.leovp.json.toJsonString
import com.leovp.log.base.d
import com.leovp.log.base.e
import com.leovp.log.base.i
import com.leovp.log.base.w
import com.leovp.mvvm.viewmodel.BaseAction
import com.leovp.mvvm.viewmodel.BaseState
import com.leovp.mvvm.viewmodel.BaseViewModel
import com.leovp.network.http.exception.ResultException
import com.leovp.network.http.exceptionOrNull
import com.leovp.network.http.fold
import com.leovp.network.http.getOrNull
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.net.URLDecoder
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
        private val useCase: PlayerUseCase,
        private val songEventDelegate: SongEventDelegate,
        private val playerDelegate: PlayerDelegate,
        private val playerExtraDelegate: PlayerExtraDelegate,
    ) : BaseViewModel<UiState, Action>(UiState.Content()) {
        companion object {
            private const val TAG = "PlayerVM"
        }

        val songId: Long = (savedStateHandle["id"] as? Long) ?: 0L
        val songArtist: String =
            URLDecoder.decode((savedStateHandle["artist"] as? String) ?: "", "UTF-8")
        val songTrack: String =
            URLDecoder.decode((savedStateHandle["track"] as? String) ?: "", "UTF-8")

        private val _playPositionState = MutableStateFlow(0f)
        val playPositionState: StateFlow<Float> = _playPositionState.asStateFlow()

        private var job: Job? = null

        fun onEnter(id: Long = songId) {
            i(TAG) { "Player -> getData() id=$id" }
            if (job != null) {
                job?.cancel()
                job = null
            }

            job =
                viewModelScope.launch {
                    val songAvailableResult = useCase.checkMusic(id, 999000)

                    songAvailableResult.fold(
                        onSuccess = { songAvailModel ->
                            onSongAvailableSuccess(id, songAvailModel)
                            d(TAG) { "Player -> getData() done." }
                        },
                        onFailure = { e ->
                            e(TAG, e) {
                                "Song check error. msg=${e.message}"
                            }
                            sendAction(Action.LoadContent(songInfo = null, exception = e))
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
                e(TAG, ex) { "Song check business error.  msg=${ex.message}" }
                sendAction(Action.LoadContent(songInfo = null, exception = ex))
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
                        firstSongRef.commentsModel = songCommentsResult.getOrNull()
                        firstSongRef.redCountModel = songRedCountResult.getOrNull()
                    }
            firstSong?.urlModel = songUrlResult.getOrNull()?.firstOrNull()
            d(TAG) { "---> UrlModel: ${firstSong?.toJsonString()}" }

            var ex =
                songInfoResult.exceptionOrNull()
                    ?: songUrlResult.exceptionOrNull()
                    ?: songCommentsResult.exceptionOrNull()
                    ?: songRedCountResult.exceptionOrNull()

            if (firstSong?.getUrlSuccess() != true) {
                w(TAG) {
                    "Failed to get song url.  code=${firstSong?.getUrlCode()}  url=${firstSong?.getUrl()}"
                }
                ex = ResultException(message = "Failed to get song url.", cause = ex)
            }
            sendAction(Action.LoadContent(songInfo = firstSong, exception = ex))
        }

        fun updatePlayPos(pos: Float) {
            _playPositionState.value = pos
        }

        fun onEvent(event: PlayerUiEvent) {
            viewModelScope.launch {
                when (event) {
                    is PlayerUiEvent.SongEvent -> songEventDelegate.handle(event)
                    is PlayerUiEvent.PlayerEvent -> playerDelegate.handle(event)
                    is PlayerUiEvent.ExtraEvent -> playerExtraDelegate.handle(event)
                }
            }
        }

        sealed interface Action : BaseAction.Simple<UiState> {
            class LoadContent(
                val songInfo: SongModel? = null,
                val exception: ResultException? = null,
            ) : Action {
                override fun reduce(state: UiState): UiState =
                    UiState.Content(
                        songInfo = songInfo,
                        exception = exception,
                    )
            }
        }

        @Keep
        sealed interface UiState : BaseState {
            data class Content(
                val songInfo: SongModel? = null,
                val exception: ResultException? = null,
            ) : UiState
        }
    }
