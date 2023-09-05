package com.leovp.androidshowcase.ui.tabs.discovery.domain.repository

import com.leovp.androidshowcase.ui.tabs.discovery.domain.model.CarouselItem
import com.leovp.androidshowcase.ui.tabs.discovery.domain.model.EverydayItem
import com.leovp.androidshowcase.ui.tabs.discovery.domain.model.MusicItem
import com.leovp.module.common.Result

/**
 * Author: Michael Leo
 * Date: 2023/7/25 08:37
 */

interface DiscoveryRepository {
    suspend fun getCarouselRecommends(): Result<List<CarouselItem>>

    suspend fun getEverydayRecommends(): Result<List<EverydayItem>>

    suspend fun getPersonalRecommends(): Result<List<MusicItem>>
}