package com.leovp.feature_discovery.presentation

import androidx.annotation.Keep
import androidx.lifecycle.viewModelScope
import com.leovp.feature_discovery.domain.model.SongModel
import com.leovp.feature_discovery.domain.usecase.PlayerUseCase
import com.leovp.feature_discovery.presentation.PlayerViewModel.Action
import com.leovp.feature_discovery.presentation.PlayerViewModel.Action.LoadContent
import com.leovp.feature_discovery.presentation.PlayerViewModel.UiState
import com.leovp.feature_discovery.presentation.PlayerViewModel.UiState.Content
import com.leovp.json.toJsonString
import com.leovp.log.base.d
import com.leovp.log.base.e
import com.leovp.log.base.i
import com.leovp.log.base.w
import com.leovp.mvvm.viewmodel.BaseAction
import com.leovp.mvvm.viewmodel.BaseState
import com.leovp.mvvm.viewmodel.BaseViewModel
import com.leovp.network.http.Result
import com.leovp.network.http.exception.ResultException
import com.leovp.network.http.exceptionOrNull
import com.leovp.network.http.fold
import com.leovp.network.http.getOrNull
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
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
class PlayerViewModel @Inject constructor(private val useCase: PlayerUseCase) :
    BaseViewModel<UiState, Action>(Content()) {

    companion object {
        private const val TAG = "PlayerVM"
    }

    private val _playPositionState = MutableStateFlow(0f)
    val playPositionState: StateFlow<Float> = _playPositionState.asStateFlow()

    private var job: Job? = null

    fun onEnter(ids: Array<Long>) {
        i(TAG) { "Player -> getData() ids=${ids.toJsonString()}" }
        if (job != null) {
            job?.cancel()
            job = null
        }

        val firstSongId = ids[0]
        job = viewModelScope.launch {
            val songAvailableResult = useCase.checkMusic(firstSongId, 999000)

            songAvailableResult.fold(
                onSuccess = { songAvailModel ->
                    if (songAvailModel.success) {
                        var songCommentsDeferred: Deferred<Result<SongModel.CommentsModel>>? = null
                        var songRedCountDeferred: Deferred<Result<SongModel.RedCountModel>>? = null
                        val songUrlDeferred =
                            async { useCase.getSongUrlV1(firstSongId, SongModel.Quality.Standard) }
                        val songInfoDeferred = async { useCase.getSongInfo(*ids.toLongArray()) }
                        if (ids.isNotEmpty()) {
                            songCommentsDeferred = async {
                                useCase.getMusicComment(
                                    id = firstSongId, limit = 20, offset = 0
                                )
                            }
                            songRedCountDeferred = async {
                                useCase.getSongRedCount(firstSongId)
                            }
                        }
                        val songUrlResult = songUrlDeferred.await()
                        val songInfoResult = songInfoDeferred.await()
                        val songCommentsResult = songCommentsDeferred?.await()
                        val songRedCountResult = songRedCountDeferred?.await()

                        val firstSong: SongModel? =
                            songInfoResult.getOrNull()?.firstOrNull()?.also { firstSongRef ->
                                firstSongRef.commentsModel = songCommentsResult?.getOrNull()
                                firstSongRef.redCountModel = songRedCountResult?.getOrNull()
                            }
                        firstSong?.urlModel = songUrlResult.getOrNull()?.firstOrNull()
                        d(TAG) { "---> UrlModel: ${firstSong?.toJsonString()}" }

                        var ex = songInfoResult.exceptionOrNull()
                            ?: songUrlResult.exceptionOrNull()
                            ?: songCommentsResult?.exceptionOrNull()
                            ?: songRedCountResult?.exceptionOrNull()

                        if (firstSong?.getUrlSuccess() != true) {
                            w(TAG) { "Failed to get song url.  code=${firstSong?.getUrlCode()}  url=${firstSong?.getUrl()}" }
                            ex = ResultException(message = "Failed to get song url.", cause = ex)
                        }
                        sendAction(LoadContent(songInfo = firstSong, exception = ex))
                    } else {
                        val ex = ResultException(message = songAvailModel.message)
                        e(TAG, ex) { "Song check business error.  msg=${ex.message}" }
                        sendAction(LoadContent(songInfo = null, exception = ex))
                    }
                    d(TAG) { "Player -> getData() done." }
                },
                onFailure = { e ->
                    e(TAG, e) { "Song check error. msg=${e.message}" }
                    sendAction(LoadContent(songInfo = null, exception = e))
                }
            )
        }
    }

    fun updatePlayPos(pos: Float) {
        _playPositionState.value = pos
    }

    fun onHotCommentClick() {
        i(TAG) { "Click on Hot Comment." }
    }

    fun onArtistClick(artist: String) {
        i(TAG) { "Click on Artist: $artist" }
    }

    fun onFavoriteClick() {
        i(TAG) { "Click on Favorite." }
    }

    fun onCommentClick() {
        i(TAG) { "Click on Comment." }
    }

    fun onQualityClick() {
        i(TAG) { "Click on Quality." }
    }

    fun onRepeatClick() {
        i(TAG) { "Click on Repeat." }
    }

    fun onBackwardClick() {
        i(TAG) { "Click on Backward." }
    }

    fun onPlayPauseClick() {
        i(TAG) { "Click on Play/Pause." }
    }

    fun onForwardClick() {
        i(TAG) { "Click on Forward." }
    }

    fun onPlaylistClick() {
        i(TAG) { "Click on Playlist." }
    }

    fun onMirrorClick() {
        i(TAG) { "Click on Mirror." }
    }

    fun onDownloadClick() {
        i(TAG) { "Click on Download." }
    }

    fun onInfoClick() {
        i(TAG) { "Click on Information." }
    }

    sealed interface Action : BaseAction<UiState> {
        class LoadContent(
            val songInfo: SongModel? = null,
            val exception: ResultException? = null,
        ) : Action {
            override fun execute(state: UiState): UiState = Content(
                songInfo = songInfo,
                exception = exception
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
