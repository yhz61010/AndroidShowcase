package com.leovp.feature_discovery.testdata.preview

import com.leovp.feature_discovery.domain.model.CarouselItem
import com.leovp.feature_discovery.domain.model.EverydayItem
import com.leovp.feature_discovery.domain.model.MusicItem
import com.leovp.feature_discovery.domain.repository.DiscoveryRepository
import com.leovp.feature_discovery.testdata.local_datasource.LocalDiscoveryDataSource
import com.leovp.module.common.Result
import javax.inject.Inject

/**
 * Author: Michael Leo
 * Date: 2023/9/4 13:55
 */
class PreviewDiscoveryRepositoryImpl @Inject constructor(
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