package com.leovp.feature_discovery.domain.usecase

import com.leovp.feature_discovery.domain.model.CarouselItem
import com.leovp.feature_discovery.domain.model.EverydayItem
import com.leovp.feature_discovery.domain.model.MusicItem
import com.leovp.feature_discovery.domain.repository.DiscoveryRepository
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
