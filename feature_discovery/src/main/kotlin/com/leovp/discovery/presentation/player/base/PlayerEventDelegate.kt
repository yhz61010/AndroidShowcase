package com.leovp.discovery.presentation.player.base

import com.leovp.discovery.presentation.player.base.PlayerContract.PlayerUiEvent
import javax.inject.Inject

/**
 * Author: Michael Leo
 * Date: 2025/8/25 15:33
 */

internal interface PlayerEventDelegate<T : PlayerUiEvent> {
    suspend fun handle(event: T)
}

class SongEventDelegate
    @Inject
    constructor() :
    PlayerEventDelegate<PlayerUiEvent.SongEvent> {
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
    PlayerEventDelegate<PlayerUiEvent.PlayerEvent> {
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
    PlayerEventDelegate<PlayerUiEvent.ExtraEvent> {
        override suspend fun handle(event: PlayerUiEvent.ExtraEvent) {
            when (event) {
                PlayerUiEvent.ExtraEvent.DownloadClick -> Unit
                PlayerUiEvent.ExtraEvent.InfoClick -> Unit
                PlayerUiEvent.ExtraEvent.MirrorClick -> Unit
            }
        }
    }
