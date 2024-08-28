package com.leovp.feature_discovery.domain.usecase

import com.leovp.feature_discovery.data.PlayerRepositoryImplement
import com.leovp.feature_discovery.domain.model.SongItem
import com.leovp.feature_discovery.domain.repository.PlayerRepository
import com.leovp.module.common.Result
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Author: Michael Leo
 * Date: 2024/8/26 14:00
 */
@Singleton
class PlayerUseCase @Inject constructor(
    @PlayerRepositoryImplement private val repository: PlayerRepository
) {
    suspend fun getSongInfo(artist: String, track: String): Result<SongItem> =
        repository.getSongInfo(artist = artist, track = track)
}
