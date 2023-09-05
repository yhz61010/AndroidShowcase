package com.leovp.androidshowcase.ui.tabs.discovery.data.repository

import com.drake.net.Net
import com.leovp.androidshowcase.ui.tabs.discovery.data.datasource.DiscoveryDataSource
import com.leovp.androidshowcase.ui.tabs.discovery.data.datasource.api.model.toEverydayItem
import com.leovp.androidshowcase.ui.tabs.discovery.data.datasource.api.model.toMusicItem
import com.leovp.androidshowcase.ui.tabs.discovery.data.datasource.api.response.GetEverydayMusicResponse
import com.leovp.androidshowcase.ui.tabs.discovery.data.datasource.api.response.GetPersonalMusicResponse
import com.leovp.androidshowcase.ui.tabs.discovery.domain.model.CarouselItem
import com.leovp.androidshowcase.ui.tabs.discovery.domain.model.EverydayItem
import com.leovp.androidshowcase.ui.tabs.discovery.domain.model.MusicItem
import com.leovp.androidshowcase.ui.tabs.discovery.domain.repository.DiscoveryRepository
import com.leovp.log.LogContext
import com.leovp.log.base.ITAG
import com.leovp.module.common.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Author: Michael Leo
 * Date: 2023/7/25 13:14
 */
class DiscoveryRepositoryImpl(
    private val dataSource: DiscoveryDataSource
) : DiscoveryRepository {

    override suspend fun getCarouselRecommends(): Result<List<CarouselItem>> =
        withContext(Dispatchers.IO) {
            Result.Success(dataSource.getCarouselRecommendedList())
        }

    override suspend fun getEverydayRecommends(): Result<List<EverydayItem>> =
        withContext(Dispatchers.IO) {
            val result: Result<List<EverydayItem>> = Net.get("./?method=geo.gettoptracks") {
                param("country", "china")
                param("limit", 10)
                param("page", 1) // start from 1
            }.toResult<GetEverydayMusicResponse>().fold(
                onSuccess = { res -> Result.Success(res.tracks.track.map { it.toEverydayItem() }) },
                onFailure = { err -> Result.Error(err) },
            )
            result
        }

    override suspend fun getPersonalRecommends(): Result<List<MusicItem>> =
        withContext(Dispatchers.IO) {
            val result: Result<List<MusicItem>> = Net.get("./?method=artist.gettoptracks") {
                param("artist", "Teresa Teng")
                param("limit", 10)
                param("page", 1) // start from 1
            }.toResult<GetPersonalMusicResponse>().fold(
                onSuccess = { res -> Result.Success(res.toptracks.track.map { it.toMusicItem() }) },
                onFailure = { err ->
                    LogContext.log.e(ITAG, "res err=$err", err)
                    Result.Error(err)
                },
            )
            result
        }
}
