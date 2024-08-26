package com.leovp.feature_discovery.data.datasource

import com.leovp.feature_discovery.domain.model.SongItem

/**
  * Author: Michael Leo
  * Date: 2024/8/26 14:02
  */
interface PlayerDataSource {
    fun getSongInfo(artist: String, album: String): SongItem
}