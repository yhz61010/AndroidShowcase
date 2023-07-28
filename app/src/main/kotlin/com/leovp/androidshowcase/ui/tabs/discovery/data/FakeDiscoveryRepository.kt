package com.leovp.androidshowcase.ui.tabs.discovery.data

import com.leovp.module.common.RequestResult

/**
 * Author: Michael Leo
 * Date: 2023/7/25 13:14
 */
class FakeDiscoveryRepository(private val dataSource: DiscoveryLocalDataSource) : DiscoveryRepository {
    override suspend fun getPersonalRecommends(): RequestResult<List<SimpleListItemModel>> {
        return RequestResult.Success(dataSource.discoveryPersonalRecommendedMusicList)
    }

    override suspend fun getCarouselRecommends(): RequestResult<List<CarouselItemModel>> {
        return RequestResult.Success(dataSource.discoveryCarouselRecommendedList)
    }
}