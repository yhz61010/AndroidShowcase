package com.leovp.feature_discovery.domain.usecase

import com.leovp.feature_discovery.data.DiscoveryRepositoryImplement
import com.leovp.feature_discovery.domain.model.CarouselItem
import com.leovp.feature_discovery.domain.model.EverydayItem
import com.leovp.feature_discovery.domain.model.MusicItem
import com.leovp.feature_discovery.domain.repository.DiscoveryRepository
import com.leovp.module.common.Result
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Author: Michael Leo
 * Date: 2023/9/4 17:02
 */
@Singleton
class GetDiscoveryListUseCase @Inject constructor(
    @DiscoveryRepositoryImplement private val repository: DiscoveryRepository
) {

    suspend fun getPersonalMusic(): Result<List<MusicItem>> = repository.getPersonalMusic()

    suspend fun getEverydayMusic(): Result<List<EverydayItem>> = repository.getEverydayMusic()

    suspend fun getCarouselMusic(): Result<List<CarouselItem>> = repository.getCarouselMusic()
}
