package com.leovp.androidshowcase.ui.tabs.discovery.domain.usecase

import com.leovp.androidshowcase.ui.tabs.discovery.domain.model.CarouselItem
import com.leovp.androidshowcase.ui.tabs.discovery.domain.model.EverydayItem
import com.leovp.androidshowcase.ui.tabs.discovery.domain.model.MusicItem
import com.leovp.androidshowcase.ui.tabs.discovery.domain.repository.DiscoveryRepository
import com.leovp.module.common.Result

/**
 * Author: Michael Leo
 * Date: 2023/9/4 17:02
 */
class GetDiscoveryListUseCase(
    private val repository: DiscoveryRepository
) {
    suspend fun getPersonalRecommends(): Result<List<MusicItem>> =
        repository.getPersonalRecommends()

    suspend fun getEverydayRecommends(): Result<List<EverydayItem>> =
        repository.getEverydayRecommends()

    suspend fun getCarouselRecommends(): Result<List<CarouselItem>> =
        repository.getCarouselRecommends()
}
