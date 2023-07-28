package com.leovp.androidshowcase.ui.tabs.discovery.data

import com.leovp.module.common.RequestResult

/**
 * Author: Michael Leo
 * Date: 2023/7/25 08:37
 */

interface DiscoveryRepository {
    suspend fun getPersonalRecommends(): RequestResult<List<SimpleListItemModel>>
    suspend fun getCarouselRecommends(): RequestResult<List<CarouselItemModel>>
    suspend fun getEverydayRecommends(): RequestResult<List<EverydayItemModel>>
}