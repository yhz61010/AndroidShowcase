package com.leovp.discovery.data.repository

import com.drake.net.Get
import com.leovp.discovery.data.datasource.DiscoveryDataSource
import com.leovp.discovery.data.datasource.api.model.toDomainModel
import com.leovp.discovery.data.datasource.api.response.HomePageBlockResponse
import com.leovp.discovery.data.datasource.api.response.PrivateContentResponse
import com.leovp.discovery.data.datasource.api.response.RecommendPlaylistResponse
import com.leovp.discovery.data.datasource.api.response.TopSongResponse
import com.leovp.discovery.domain.model.HomePageBlockModel
import com.leovp.discovery.domain.model.PlaylistModel
import com.leovp.discovery.domain.model.PrivateContentModel
import com.leovp.discovery.domain.model.TopSongModel
import com.leovp.discovery.domain.repository.DiscoveryRepository
import com.leovp.feature.base.GlobalConst
import com.leovp.feature.base.GlobalConst.PLAYLIST_SONG_SIZE
import com.leovp.network.http.Result
import com.leovp.network.http.net.result
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

/**
 * Author: Michael Leo
 * Date: 2023/7/25 13:14
 */
class DiscoveryRepositoryImpl
    @Inject
    constructor(
        @Suppress("unused") private val dataSource: DiscoveryDataSource,
    ) : DiscoveryRepository {
        /** 首页-发现 */
        override suspend fun getHomePageBlock(): Result<HomePageBlockModel> =
            result(Dispatchers.IO) {
                Get<HomePageBlockResponse>(
                    GlobalConst.HTTP_GET_HOMEPAGE_BLOCK_PAGE,
                ).await()
                    .result
                    .toDomainModel()
            }

        /** 获取独家放送 */
        override suspend fun getPrivateContent(): Result<List<PrivateContentModel>> =
            result(Dispatchers.IO) {
                Get<PrivateContentResponse>(
                    GlobalConst.HTTP_GET_PRIVATE_CONTENT,
                ).await()
                    .let { (typeName, result) ->
                        result.map { it.toDomainModel(typeName) }
                    }
            }

        /** 获取推荐歌单 */
        override suspend fun getRecommendPlaylist(): Result<List<PlaylistModel>> =
            result(Dispatchers.IO) {
                Get<RecommendPlaylistResponse>(
                    GlobalConst.HTTP_GET_PERSONALIZED,
                ) {
                    param("limit", PLAYLIST_SONG_SIZE)
                }.await()
                    .result
                    .mapIndexed { _, respData ->
                        respData.toDomainModel()
                    }
            }

        /** 新歌速递 */
        override suspend fun getTopSongs(type: Int): Result<List<TopSongModel>> =
            result(Dispatchers.IO) {
                Get<TopSongResponse>(GlobalConst.HTTP_GET_TOP_SONG) {
                    param("type", type)
                }.await()
                    .result
                    .take(PLAYLIST_SONG_SIZE)
                    .mapIndexed { _, respData ->
                        respData.toDomainModel()
                    }
            }
    }
