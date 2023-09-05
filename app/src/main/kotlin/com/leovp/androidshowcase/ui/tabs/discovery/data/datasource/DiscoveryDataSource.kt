package com.leovp.androidshowcase.ui.tabs.discovery.data.datasource

import com.leovp.androidshowcase.ui.tabs.discovery.domain.model.CarouselItem
import com.leovp.androidshowcase.ui.tabs.discovery.domain.model.EverydayItem
import com.leovp.androidshowcase.ui.tabs.discovery.domain.model.MusicItem

/**
 * Author: Michael Leo
 * Date: 2023/9/5 14:54
 */
interface DiscoveryDataSource {
    fun getCarouselRecommendedList(): List<CarouselItem>

    fun getEverydayRecommendedList(): List<EverydayItem>

    fun getPersonalRecommendedMusicList(): List<MusicItem>
}