package com.leovp.feature_discovery.presentation

import android.content.res.Resources
import androidx.annotation.Keep
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leovp.feature_discovery.R
import com.leovp.feature_discovery.domain.model.SongItem
import com.leovp.feature_discovery.domain.usecase.PlayerUseCase
import com.leovp.module.common.exceptionOrNull
import com.leovp.module.common.getOrNull
import com.leovp.module.common.log.d
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

    var loading = true
        private set

    private var job: Job? = null

    fun getData(artist: String, track: String) {
        i(TAG) { "Player -> getData()" }
        loading = true
        if (job != null) {
            job?.cancel()
            job = null
        }

        job = viewModelScope.launch {
            val songInfoDeferred = async { useCase.getSongInfo(artist = artist, track = track) }

            val songInfoResult = songInfoDeferred.await()

            val ex = songInfoResult.exceptionOrNull()

            loading = false
            _uiState.update {
                it.copy(
                    songInfo = songInfoResult.getOrNull(),
                    exception = ex
                )
            }
            d(TAG) { "Player -> getData() done." }
        }
    }
}

/**
 * UI state for the Player screen
 */
@Keep
data class PlayerUiState(
    val songInfo: SongItem? = null,
    val exception: Throwable? = null,
) {
    fun getSongName(def: String = ""): String = songInfo?.name ?: def
    fun getSongArtist(def: String = ""): String = songInfo?.artist ?: def
    fun getSongDuration(): Long = songInfo?.duration ?: 0
    fun getSongQuality(): SongItem.Quality = songInfo?.quality ?: SongItem.Quality.STANDARD
    fun getSongQualityName(res: Resources): String =
        res.getStringArray(R.array.dis_player_song_quality_name)[getSongQuality().ordinal]

    fun getSongFavoriteCount(): Long = songInfo?.favoriteCount ?: 0
    fun getSongCommentCount(): Long = songInfo?.commentCount ?: 0

    fun getSongFullName(defArtist: String = "", defTrack: String = ""): String =
        "${getSongArtist(defArtist)}-${getSongName(defTrack)}"
}