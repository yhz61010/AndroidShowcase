package com.leovp.androidshowcase.testdata.preview

import com.leovp.androidshowcase.testdata.local_datasource.LocalDiscoveryDataSource
import com.leovp.androidshowcase.ui.tabs.discovery.domain.model.CarouselItem
import com.leovp.androidshowcase.ui.tabs.discovery.domain.model.EverydayItem
import com.leovp.androidshowcase.ui.tabs.discovery.domain.model.MusicItem
import com.leovp.androidshowcase.ui.tabs.discovery.domain.repository.DiscoveryRepository
import com.leovp.module.common.Result

/**
  * Author: Michael Leo
  * Date: 2023/9/4 13:55
  */
class PreviewDiscoveryRepository(
    private val dataSource: LocalDiscoveryDataSource
) : DiscoveryRepository {

    override suspend fun getCarouselRecommends(): Result<List<CarouselItem>> {
        return Result.Success(dataSource.getCarouselRecommendedList())
    }

    override suspend fun getEverydayRecommends(): Result<List<EverydayItem>> {
        return Result.Success(dataSource.getEverydayRecommendedList())
    }

    override suspend fun getPersonalRecommends(): Result<List<MusicItem>> {
        return Result.Success(dataSource.getPersonalRecommendedMusicList())
    }
}