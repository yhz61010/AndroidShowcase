package com.leovp.feature_discovery.testdata.preview

import com.leovp.feature_discovery.domain.model.HomePageBlockModel
import com.leovp.feature_discovery.domain.model.PlaylistModel
import com.leovp.feature_discovery.domain.model.PrivateContentModel
import com.leovp.feature_discovery.domain.model.TopSongModel
import com.leovp.feature_discovery.domain.repository.DiscoveryRepository
import com.leovp.feature_discovery.testdata.local_datasource.LocalDiscoveryDataSource
import com.leovp.network.http.Result
import javax.inject.Inject

/**
 * Author: Michael Leo
 * Date: 2023/9/4 13:55
 */
class PreviewDiscoveryRepositoryImpl @Inject constructor(
    private val dataSource: LocalDiscoveryDataSource
) : DiscoveryRepository {

    override suspend fun getHomePageBlock(): Result<HomePageBlockModel> {
        return Result.Success(dataSource.getHomePageBlock())
    }

    override suspend fun getPrivateContent(): Result<List<PrivateContentModel>> {
        return Result.Success(dataSource.getPrivateContent())
    }

    override suspend fun getRecommendPlaylist(): Result<List<PlaylistModel>> {
        return Result.Success(dataSource.getRecommendPlaylist())
    }

    override suspend fun getTopSongs(type: Int): Result<List<TopSongModel>> {
        return Result.Success(dataSource.getTopSong(type))
    }
}