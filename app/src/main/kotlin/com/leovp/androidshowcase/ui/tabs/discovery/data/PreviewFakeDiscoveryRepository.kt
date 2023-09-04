package com.leovp.androidshowcase.ui.tabs.discovery.data

import com.leovp.module.common.Result

/**
  * Author: Michael Leo
  * Date: 2023/9/4 13:55
  */
class PreviewFakeDiscoveryRepository(
    private val dataSource: DiscoveryLocalDataSource
) : DiscoveryRepository {

    override suspend fun getPersonalRecommends(): Result<List<SimpleListItemModel>> {
        return Result.Success(dataSource.discoveryPersonalRecommendedMusicList)
    }

    override suspend fun getCarouselRecommends(): Result<List<CarouselItemModel>> {
        return Result.Success(dataSource.discoveryCarouselRecommendedList)
    }

    override suspend fun getEverydayRecommends(): Result<List<EverydayItemModel>> {
        return Result.Success(dataSource.discoveryEverydayRecommendedList)
    }
}