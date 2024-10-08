package com.leovp.feature_discovery.domain.repository

import com.leovp.feature_discovery.domain.model.HomePageBlockModel
import com.leovp.feature_discovery.domain.model.PlaylistModel
import com.leovp.feature_discovery.domain.model.PrivateContentModel
import com.leovp.feature_discovery.domain.model.TopSongModel
import com.leovp.module.common.Result

/**
 * Author: Michael Leo
 * Date: 2023/7/25 08:37
 */

interface DiscoveryRepository {

    suspend fun getHomePageBlock(): Result<HomePageBlockModel>

    suspend fun getPrivateContent(): Result<List<PrivateContentModel>>

    suspend fun getRecommendPlaylist(): Result<List<PlaylistModel>>

    suspend fun getTopSongs(type: Int): Result<List<TopSongModel>>
}