package com.leovp.discovery.presentation.discovery.base

import com.leovp.discovery.domain.model.PlaylistModel
import com.leovp.discovery.domain.model.PrivateContentModel
import com.leovp.discovery.domain.model.TopSongModel
import com.leovp.feature.base.framework.BaseState
import com.leovp.network.http.exception.ResultException

/**
 * Author: Michael Leo
 * Date: 2025/8/25 15:04
 */

object DiscoveryContract {
    sealed interface DiscoveryUiState : BaseState {
        data class Content(
            val privateContent: List<PrivateContentModel> = emptyList(),
            val recommendPlaylist: List<PlaylistModel> = emptyList(),
            val topSongs: List<TopSongModel> = emptyList(),
            val isLoading: Boolean = false,
            val exception: ResultException? = null,
        ) : DiscoveryUiState
    }

    sealed interface DiscoveryUiEvent {
        data class CarouselItemClick(
            val data: PrivateContentModel,
        ) : DiscoveryUiEvent

        data class RecommendsItemClick(
            val data: PlaylistModel,
        ) : DiscoveryUiEvent

        data class PersonalItemClick(
            val data: TopSongModel,
        ) : DiscoveryUiEvent

        data object Refresh : DiscoveryUiEvent
    }
}
