package com.leovp.discovery.presentation.player.base

import com.leovp.discovery.presentation.player.base.PlayerContract.PlayerUiEvent
import com.leovp.discovery.presentation.player.base.PlayerContract.PlayerUiEvent.ExtraEvent
import com.leovp.discovery.presentation.player.base.PlayerContract.PlayerUiEvent.PlayerEvent
import com.leovp.discovery.presentation.player.base.PlayerContract.PlayerUiEvent.SongEvent
import com.leovp.feature.base.ui.Screen
import com.leovp.mvvm.event.base.UiEvent
import com.leovp.mvvm.event.base.UiEventManager
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
    constructor(
        private val uiEventManager: UiEventManager,
    ) : PlayerEventDelegate<SongEvent> {
        override suspend fun handle(event: SongEvent) {
            when (event) {
                is SongEvent.CommentClick -> {
                    // uiEventManager.sendEvent(UiEvent.ShowToast(message = "Click on Comment"))
                    uiEventManager.sendEvent(
                        UiEvent.Navigate(
                            Screen.Comment(
                                songId = event.songInfo.id,
                                songName = event.songInfo.name,
                                artist = event.songInfo.getSongArtist(),
                            ),
                        ),
                    )
                }

                SongEvent.FavoriteClick -> Unit
                SongEvent.HotCommentClick -> Unit
                SongEvent.ArtistClick -> Unit
                SongEvent.MarkClick -> Unit
            }
        }
    }

class PlayerDelegate
    @Inject
    constructor() : PlayerEventDelegate<PlayerEvent> {
        override suspend fun handle(event: PlayerEvent) {
            when (event) {
                PlayerEvent.BackwardClick -> Unit
                PlayerEvent.ForwardClick -> Unit
                PlayerEvent.PlayPauseClick -> Unit
                PlayerEvent.PlaylistClick -> Unit
                PlayerEvent.QualityClick -> Unit
                PlayerEvent.RepeatClick -> Unit
            }
        }
    }

class PlayerExtraDelegate
    @Inject
    constructor() :
    PlayerEventDelegate<ExtraEvent> {
        override suspend fun handle(event: ExtraEvent) {
            when (event) {
                ExtraEvent.DownloadClick -> Unit
                ExtraEvent.InfoClick -> Unit
                ExtraEvent.MirrorClick -> Unit
            }
        }
    }
