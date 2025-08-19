package com.leovp.discovery.data.datasource

import com.leovp.discovery.domain.model.HomePageBlockModel
import com.leovp.discovery.domain.model.PlaylistModel
import com.leovp.discovery.domain.model.PrivateContentModel
import com.leovp.discovery.domain.model.TopSongModel

/**
 * Author: Michael Leo
 * Date: 2023/9/5 14:54
 */
interface DiscoveryDataSource {
    fun getHomePageBlock(): HomePageBlockModel

    fun getPrivateContent(): List<PrivateContentModel>

    fun getTopSong(type: Int): List<TopSongModel>

    fun getRecommendPlaylist(): List<PlaylistModel>
}
