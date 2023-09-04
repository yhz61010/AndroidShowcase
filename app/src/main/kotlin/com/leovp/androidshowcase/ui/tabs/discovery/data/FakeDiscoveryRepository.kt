package com.leovp.androidshowcase.ui.tabs.discovery.data

import com.leovp.module.common.Result
import kotlinx.coroutines.delay

/**
 * Author: Michael Leo
 * Date: 2023/7/25 13:14
 */
class FakeDiscoveryRepository(
    private val dataSource: DiscoveryLocalDataSource
) : DiscoveryRepository {

    override suspend fun getPersonalRecommends(): Result<List<SimpleListItemModel>> {
        delay(1000)
        return Result.Success(dataSource.discoveryPersonalRecommendedMusicList)
    }

    override suspend fun getCarouselRecommends(): Result<List<CarouselItemModel>> {
        delay(1100)
        return Result.Success(dataSource.discoveryCarouselRecommendedList)
    }

    override suspend fun getEverydayRecommends(): Result<List<EverydayItemModel>> {
        delay(1200)
        return Result.Success(dataSource.discoveryEverydayRecommendedList)
    }

}