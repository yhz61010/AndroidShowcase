package com.leovp.discovery.testdata.preview

import com.leovp.discovery.domain.model.HomePageBlockModel
import com.leovp.discovery.domain.model.PlaylistModel
import com.leovp.discovery.domain.model.PrivateContentModel
import com.leovp.discovery.domain.model.TopSongModel
import com.leovp.discovery.domain.repository.DiscoveryRepository
import com.leovp.discovery.testdata.local.datasource.LocalDiscoveryDataSource
import com.leovp.feature.base.http.model.ApiResponseModel
import com.leovp.network.http.Result
import javax.inject.Inject

/**
 * Author: Michael Leo
 * Date: 2023/9/4 13:55
 */
class PreviewDiscoveryRepositoryImpl
    @Inject
    constructor(
        private val dataSource: LocalDiscoveryDataSource,
    ) : DiscoveryRepository {
        override suspend fun getHomePageBlock(): Result<ApiResponseModel<HomePageBlockModel>> =
            Result.Success(ApiResponseModel(result = dataSource.getHomePageBlock()))

        override suspend fun getPrivateContent(): Result<ApiResponseModel<List<PrivateContentModel>>> =
            Result.Success(ApiResponseModel(result = dataSource.getPrivateContent()))

        override suspend fun getRecommendPlaylist(): Result<ApiResponseModel<List<PlaylistModel>>> =
            Result.Success(ApiResponseModel(result = dataSource.getRecommendPlaylist()))

        override suspend fun getTopSongs(type: Int): Result<ApiResponseModel<List<TopSongModel>>> =
            Result.Success(ApiResponseModel(result = dataSource.getTopSong(type)))
    }
