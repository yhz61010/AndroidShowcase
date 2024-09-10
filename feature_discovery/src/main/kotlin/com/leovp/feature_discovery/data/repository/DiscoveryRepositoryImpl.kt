package com.leovp.feature_discovery.data.repository

import com.drake.net.Get
import com.leovp.feature_discovery.data.datasource.DiscoveryDataSource
import com.leovp.feature_discovery.data.datasource.api.model.toDomainModel
import com.leovp.feature_discovery.data.datasource.api.response.GetTopAlbumsResponse
import com.leovp.feature_discovery.data.datasource.api.response.GetTopTracksResponse
import com.leovp.feature_discovery.data.datasource.api.response.SearchAlbumResponse
import com.leovp.feature_discovery.domain.model.CarouselItem
import com.leovp.feature_discovery.domain.model.EverydayItem
import com.leovp.feature_discovery.domain.model.MusicItem
import com.leovp.feature_discovery.domain.repository.DiscoveryRepository
import com.leovp.module.common.GlobalConst
import com.leovp.module.common.Result
import com.leovp.module.common.result
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

/**
 * Author: Michael Leo
 * Date: 2023/7/25 13:14
 */
class DiscoveryRepositoryImpl @Inject constructor(
    @Suppress("unused") private val dataSource: DiscoveryDataSource
) : DiscoveryRepository {

    override suspend fun getCarouselMusic(): Result<List<CarouselItem>> = result(Dispatchers.IO) {
        Get<GetTopAlbumsResponse>(GlobalConst.HTTP_GET_ARTIST_TOP_ALBUMS) {
            param("artist", "Teresa Teng")
            param("limit", 5)
            param("page", 1) // start from 1
        }.await().topAlbums.album.mapIndexed { index, album ->
            album.toDomainModel(index)
        }
    }

    override suspend fun getEverydayMusic(): Result<List<EverydayItem>> = result(Dispatchers.IO) {
        Get<SearchAlbumResponse>(GlobalConst.HTTP_GET_SEARCH_ALBUM) {
            param("album", "Teresa Teng")
            param("limit", 10)
            param("page", 1) // start from 1
        }.await().results.albumMatches.album.mapIndexed { index, album ->
            album.toDomainModel(index)
        }
    }

    override suspend fun getPersonalMusic(): Result<List<MusicItem>> = result(Dispatchers.IO) {
        Get<GetTopTracksResponse>(GlobalConst.HTTP_GET_ARTIST_TOP_TRACKS) {
            param("artist", "Teresa Teng")
            param("limit", 10)
            param("page", 1) // start from 1
        }.await().toptracks.track.mapIndexed { index, track ->
            track.toDomainModel(index)
        }
    }
}
