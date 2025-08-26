package com.leovp.discovery.presentation.player.base

import com.leovp.discovery.presentation.player.base.PlayerContract.PlayerUiEvent
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

/**
 * Author: Michael Leo
 * Date: 2025/8/25 17:07
 */
@ViewModelScoped
class PlayerDelegateManager
    @Inject
    constructor(
        val songEventDelegate: SongEventDelegate,
        val playerDelegate: PlayerDelegate,
        val playerExtraDelegate: PlayerExtraDelegate,
    ) {
        suspend fun handleEvent(event: PlayerUiEvent) {
            when (event) {
                is PlayerUiEvent.SongEvent -> songEventDelegate.handle(event)
                is PlayerUiEvent.PlayerEvent -> playerDelegate.handle(event)
                is PlayerUiEvent.ExtraEvent -> playerExtraDelegate.handle(event)
            }
        }
    }
