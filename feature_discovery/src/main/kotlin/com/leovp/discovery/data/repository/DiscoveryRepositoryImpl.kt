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
import com.leovp.feature.base.http.model.ApiResponseModel
import com.leovp.network.http.Result
import com.leovp.network.http.net.result
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
        override suspend fun getHomePageBlock(): Result<ApiResponseModel<HomePageBlockModel>> =
            result {
                val respResult = Get<HomePageBlockResponse>(
                    GlobalConst.HTTP_GET_HOMEPAGE_BLOCK_PAGE,
                ).await()

                ApiResponseModel(
                    code = respResult.code,
                    message = respResult.message,
                    result = respResult.result.toDomainModel()
                )
            }

        /** 获取独家放送 */
        override suspend fun getPrivateContent(): Result<ApiResponseModel<List<PrivateContentModel>>> =
            result {
                val respResult = Get<PrivateContentResponse>(
                    GlobalConst.HTTP_GET_PRIVATE_CONTENT,
                ).await()

                ApiResponseModel(
                    code = respResult.code,
                    message = respResult.message,
                    result = respResult.result.map { it.toDomainModel(respResult.name) }
                )
            }

        /** 获取推荐歌单 */
        override suspend fun getRecommendPlaylist(): Result<ApiResponseModel<List<PlaylistModel>>> =
            result {
                val respResult = Get<RecommendPlaylistResponse>(
                    GlobalConst.HTTP_GET_PERSONALIZED,
                ) {
                    param("limit", PLAYLIST_SONG_SIZE)
                }.await()

                ApiResponseModel(
                    code = respResult.code,
                    message = respResult.message,
                    result = respResult.result.map { it.toDomainModel() }
                )
            }

        /** 新歌速递 */
        override suspend fun getTopSongs(type: Int): Result<ApiResponseModel<List<TopSongModel>>> =
            result {
                val respResult = Get<TopSongResponse>(GlobalConst.HTTP_GET_TOP_SONG) {
                    param("type", type)
                }.await()

                ApiResponseModel(
                    code = respResult.code,
                    message = respResult.message,
                    result = respResult.result
                        .take(PLAYLIST_SONG_SIZE)
                        .map { it.toDomainModel() }
                )
            }
    }
