package com.leovp.androidshowcase.ui.tabs.home

import com.leovp.androidshowcase.ui.tabs.home.data.HomeLocalDataSource
import com.leovp.androidshowcase.ui.tabs.home.data.SimpleListItemModel

/**
 * Author: Michael Leo
 * Date: 2023/7/25 08:37
 */

class HomeRepository(private val dataSource: HomeLocalDataSource) {
    val personalRecommendedMusic: List<SimpleListItemModel> = dataSource.homePersonalRecommendedMusicList
}