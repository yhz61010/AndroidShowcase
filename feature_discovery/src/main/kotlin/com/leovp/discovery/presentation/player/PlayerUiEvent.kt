package com.leovp.discovery.presentation.player

import javax.inject.Inject

/**
 * Author: Michael Leo
 * Date: 2025/8/20 13:37
 */
sealed class PlayerUiEvent {
    sealed class SongEvent : PlayerUiEvent() {
        data object ArtistClick : SongEvent()

        data object FavoriteClick : SongEvent()

        data object CommentClick : SongEvent()

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

internal interface EventDelegate<T : PlayerUiEvent> {
    suspend fun handle(event: T)
}

class SongEventDelegate
    @Inject
    constructor() :
    EventDelegate<PlayerUiEvent.SongEvent> {
        override suspend fun handle(event: PlayerUiEvent.SongEvent) {
            when (event) {
                PlayerUiEvent.SongEvent.CommentClick -> Unit
                PlayerUiEvent.SongEvent.FavoriteClick -> Unit
                PlayerUiEvent.SongEvent.HotCommentClick -> Unit
                PlayerUiEvent.SongEvent.ArtistClick -> Unit
            }
        }
    }

class PlayerDelegate
    @Inject
    constructor() :
    EventDelegate<PlayerUiEvent.PlayerEvent> {
        override suspend fun handle(event: PlayerUiEvent.PlayerEvent) {
            when (event) {
                PlayerUiEvent.PlayerEvent.BackwardClick -> Unit
                PlayerUiEvent.PlayerEvent.ForwardClick -> Unit
                PlayerUiEvent.PlayerEvent.PlayPauseClick -> Unit
                PlayerUiEvent.PlayerEvent.PlaylistClick -> Unit
                PlayerUiEvent.PlayerEvent.QualityClick -> Unit
                PlayerUiEvent.PlayerEvent.RepeatClick -> Unit
            }
        }
    }

class PlayerExtraDelegate
    @Inject
    constructor() :
    EventDelegate<PlayerUiEvent.ExtraEvent> {
        override suspend fun handle(event: PlayerUiEvent.ExtraEvent) {
            when (event) {
                PlayerUiEvent.ExtraEvent.DownloadClick -> Unit
                PlayerUiEvent.ExtraEvent.InfoClick -> Unit
                PlayerUiEvent.ExtraEvent.MirrorClick -> Unit
            }
        }
    }
