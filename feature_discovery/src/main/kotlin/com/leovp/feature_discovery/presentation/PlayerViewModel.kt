package com.leovp.feature_discovery.presentation

import androidx.annotation.Keep
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leovp.feature_discovery.domain.model.SongModel
import com.leovp.feature_discovery.domain.usecase.PlayerUseCase
import com.leovp.module.common.Result
import com.leovp.module.common.exceptionOrNull
import com.leovp.module.common.getOrNull
import com.leovp.module.common.log.d
import com.leovp.module.common.log.i
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
            val songInfoDeferred = async { useCase.getSongInfo(*ids.toLongArray()) }

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

            val songInfoResult = songInfoDeferred.await()
            val songCommentsResult = songCommentsDeferred?.await()
            val songRedCountResult = songRedCountDeferred?.await()

            val firstSong: SongModel? = songInfoResult.getOrNull()?.firstOrNull()
            firstSong?.commentsModel = songCommentsResult?.getOrNull()
            firstSong?.redCountModel = songRedCountResult?.getOrNull()

            val ex = songInfoResult.exceptionOrNull()
                ?: songCommentsResult?.exceptionOrNull()
                ?: songRedCountResult?.exceptionOrNull()

            d(TAG, throwable = ex) { "Exception while getData()" }

            loading = false
            _uiState.update {
                it.copy(
                    songInfo = firstSong, exception = ex
                )
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
    fun getSongQuality(): SongModel.Quality = songInfo?.quality ?: SongModel.Quality.STANDARD

    fun getSongRedCount(): Long = songInfo?.redCountModel?.count ?: 0
    fun getSongRedCountStr(): String? = songInfo?.redCountModel?.countDesc
    fun getSongCommentCount(): Long = songInfo?.commentsModel?.totalComments ?: 0

    fun getSongFullName(defArtist: String = "", defTrack: String = ""): String =
        "${getSongArtist(defArtist)}-${getSongName(defTrack)}"
}