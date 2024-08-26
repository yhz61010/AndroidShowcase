package com.leovp.feature_discovery.domain.repository

import com.leovp.feature_discovery.domain.model.SongItem
import com.leovp.module.common.Result

/**
  * Author: Michael Leo
  * Date: 2024/8/26 13:56
  */
interface PlayerRepository {
    suspend fun getSongInfo(artist: String, album: String): Result<SongItem>
}