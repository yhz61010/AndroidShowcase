package com.leovp.discovery.testdata.preview

import com.leovp.discovery.domain.model.HomePageBlockModel
import com.leovp.discovery.domain.model.PlaylistModel
import com.leovp.discovery.domain.model.PrivateContentModel
import com.leovp.discovery.domain.model.TopSongModel
import com.leovp.discovery.domain.repository.DiscoveryRepository
import com.leovp.discovery.testdata.local.datasource.LocalDiscoveryDataSource
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
        override suspend fun getHomePageBlock(): Result<HomePageBlockModel> =
            Result.Success(dataSource.getHomePageBlock())

        override suspend fun getPrivateContent(): Result<List<PrivateContentModel>> =
            Result.Success(dataSource.getPrivateContent())

        override suspend fun getRecommendPlaylist(): Result<List<PlaylistModel>> =
            Result.Success(dataSource.getRecommendPlaylist())

        override suspend fun getTopSongs(type: Int): Result<List<TopSongModel>> =
            Result.Success(dataSource.getTopSong(type))
    }
