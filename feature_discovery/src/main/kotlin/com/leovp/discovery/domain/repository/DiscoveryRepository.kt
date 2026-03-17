package com.leovp.discovery.domain.repository

import com.leovp.discovery.domain.model.HomePageBlockModel
import com.leovp.discovery.domain.model.PlaylistModel
import com.leovp.discovery.domain.model.PrivateContentModel
import com.leovp.discovery.domain.model.TopSongModel
import com.leovp.feature.base.http.model.ApiResponseModel
import com.leovp.network.http.Result

/**
 * Author: Michael Leo
 * Date: 2023/7/25 08:37
 */

interface DiscoveryRepository {
    suspend fun getHomePageBlock(): Result<ApiResponseModel<HomePageBlockModel>>

    suspend fun getPrivateContent(): Result<ApiResponseModel<List<PrivateContentModel>>>

    suspend fun getRecommendPlaylist(): Result<ApiResponseModel<List<PlaylistModel>>>

    suspend fun getTopSongs(type: Int): Result<ApiResponseModel<List<TopSongModel>>>
}
