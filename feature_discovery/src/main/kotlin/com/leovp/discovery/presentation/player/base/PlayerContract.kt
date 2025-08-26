package com.leovp.discovery.presentation.player.base

import com.leovp.discovery.domain.model.SongModel
import com.leovp.feature.base.framework.BaseState
import com.leovp.network.http.exception.ResultException

/**
 * Author: Michael Leo
 * Date: 2025/8/20 13:37
 */
object PlayerContract {
    sealed interface PlayerUiState : BaseState {
        data class Content(
            val songInfo: SongModel? = null,
            val exception: ResultException? = null,
        ) : PlayerUiState
    }

    sealed class PlayerUiEvent {
        sealed class SongEvent : PlayerUiEvent() {
            data object ArtistClick : SongEvent()

            data object MarkClick : SongEvent()

            data object FavoriteClick : SongEvent()

            data class CommentClick(
                val songInfo: SongModel,
            ) : SongEvent()

            data object HotCommentClick : SongEvent()
        }

        sealed class PlayerEvent : PlayerUiEvent() {
            data object RepeatClick : PlayerEvent()

            data object BackwardClick : PlayerEvent()

            data object PlayPauseClick : PlayerEvent()

            data object ForwardClick : PlayerEvent()

            data object PlaylistClick : PlayerEvent()

            data object QualityClick : PlayerEvent()
        }

        sealed class ExtraEvent : PlayerUiEvent() {
            data object MirrorClick : ExtraEvent()

            data object DownloadClick : ExtraEvent()

            data object InfoClick : ExtraEvent()
        }
    }
}
