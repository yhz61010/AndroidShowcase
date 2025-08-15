package com.leovp.feature_discovery.presentation

import androidx.annotation.Keep
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leovp.feature_discovery.domain.model.SongModel
import com.leovp.feature_discovery.domain.usecase.PlayerUseCase
import com.leovp.json.toJsonString
import com.leovp.log.base.d
import com.leovp.log.base.e
import com.leovp.log.base.i
import com.leovp.log.base.w
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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Author: Michael Leo
 * Date: 2024/8/26 13:54
 */

@HiltViewModel
class PlayerViewModel @Inject constructor(private val useCase: PlayerUseCase) : ViewModel() {

    companion object {
        private const val TAG = "PlayerVM"
    }

    // Backing property to avoid state updates from other classes
    private val _uiState = MutableStateFlow(PlayerUiState())

    // UI state exposed to the UI
    val uiState: StateFlow<PlayerUiState> = _uiState.asStateFlow()

    private val _playPosition = MutableStateFlow(0f)
    val playPosition: StateFlow<Float> = _playPosition.asStateFlow()

    var loading = true
        private set

    private var job: Job? = null

    fun getData(ids: Array<Long>) {
        i(TAG) { "Player -> getData()  ids=$ids" }
        loading = true
        if (job != null) {
            job?.cancel()
            job = null
        }

        val firstSongId = ids[0]
        job = viewModelScope.launch {
            var ex: ResultException? = null

            var songCommentsDeferred: Deferred<Result<SongModel.CommentsModel>>? = null
            if (ids.isNotEmpty()) {
                songCommentsDeferred = async {
                    useCase.getMusicComment(
                        id = firstSongId, limit = 20, offset = 0
                    )
                }
            }

            var songRedCountDeferred: Deferred<Result<SongModel.RedCountModel>>? = null
            if (ids.isNotEmpty()) {
                songRedCountDeferred = async {
                    useCase.getSongRedCount(firstSongId)
                }
            }

            val songInfoDeferred = async { useCase.getSongInfo(*ids.toLongArray()) }
            val songAvailableDeferred = async { useCase.checkMusic(firstSongId, 999000) }

            val songInfoResult = songInfoDeferred.await()
            val songCommentsResult = songCommentsDeferred?.await()
            val songRedCountResult = songRedCountDeferred?.await()
            val songAvailableResult = songAvailableDeferred.await()

            val firstSong: SongModel? =
                songInfoResult.getOrNull()?.firstOrNull()?.also { firstSongRef ->
                    firstSongRef.commentsModel = songCommentsResult?.getOrNull()
                    firstSongRef.redCountModel = songRedCountResult?.getOrNull()
                }

            songAvailableResult.fold(
                onSuccess = { songAvailModel ->
                    if (songAvailModel.success) {
                        val songUrlDeferred =
                            async { useCase.getSongUrlV1(firstSongId, SongModel.Quality.Standard) }
                        val songUrlResult = songUrlDeferred.await()
                        firstSong?.urlModel = songUrlResult.getOrNull()?.firstOrNull()

                        d(TAG) { "---> UrlModel: ${firstSong?.toJsonString()}" }
                        if (firstSong?.getUrlSuccess() != true) {
                            w(TAG) { "Failed to get song url.  code=${firstSong?.getUrlCode()}  url=${firstSong?.getUrl()}" }
                            ex = ResultException(message = "Failed to get song url.")
                        }
                    } else {
                        ex = ResultException(message = songAvailModel.message)
                        e(TAG, ex) { "Song check business error.  msg=${ex?.message}" }
                    }
                },
                onFailure = { e ->
                    e(TAG, e) { "Song check error.  msg=${e.message}" }
                    ex = e
                }
            )

            ex = ex ?: songInfoResult.exceptionOrNull()
                    ?: songCommentsResult?.exceptionOrNull()
                    ?: songRedCountResult?.exceptionOrNull()

            if (ex != null) {
                e(TAG, throwable = ex) { "Exception while getData()" }
            }

            loading = false
            _uiState.update {
                it.copy(songInfo = firstSong, exception = ex)
            }
            d(TAG) { "Player -> getData() done." }
        }
    }

    fun updatePlayPos(pos: Float) {
        _playPosition.value = pos
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
}

/**
 * UI state for the Player screen
 */
@Keep
data class PlayerUiState(
    val songInfo: SongModel? = null,
    val exception: Throwable? = null,
) {
    fun getSongName(def: String = ""): String = songInfo?.name ?: def
    fun getSongArtist(def: String = ""): String = songInfo?.artists?.firstOrNull()?.name ?: def
    fun getSongDuration(): Long = songInfo?.duration ?: 0
    fun getSongQuality(): SongModel.Quality = songInfo?.quality ?: SongModel.Quality.Standard

    fun getSongRedCount(): Long = songInfo?.redCountModel?.count ?: 0
    fun getSongRedCountStr(): String? = songInfo?.redCountModel?.countDesc
    fun getSongCommentCount(): Long = songInfo?.commentsModel?.totalComments ?: 0

    fun getSongFullName(defArtist: String = "", defTrack: String = ""): String =
        "${getSongArtist(defArtist)}-${getSongName(defTrack)}"
}