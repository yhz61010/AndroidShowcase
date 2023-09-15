package com.leovp.feature_discovery.data.datasource

import com.leovp.feature_discovery.domain.model.CarouselItem
import com.leovp.feature_discovery.domain.model.EverydayItem
import com.leovp.feature_discovery.domain.model.MusicItem

/**
 * Author: Michael Leo
 * Date: 2023/9/5 14:54
 */
interface DiscoveryDataSource {
    fun getCarouselMusicList(): List<CarouselItem>

    fun getEverydayMusicList(): List<EverydayItem>

    fun getPersonalMusicList(): List<MusicItem>
}