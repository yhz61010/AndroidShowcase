package com.leovp.androidshowcase.ui.tabs.discovery.data

import com.leovp.module.common.Result

/**
 * Author: Michael Leo
 * Date: 2023/7/25 08:37
 */

interface DiscoveryRepository {
    suspend fun getPersonalRecommends(): Result<List<SimpleListItemModel>>
    suspend fun getCarouselRecommends(): Result<List<CarouselItemModel>>
    suspend fun getEverydayRecommends(): Result<List<EverydayItemModel>>
}