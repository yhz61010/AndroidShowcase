package com.leovp.androidshowcase.framework

import com.leovp.androidshowcase.ui.tabs.home.HomeRepository
import com.leovp.androidshowcase.ui.tabs.home.data.HomeLocalDataSource

/**
 * Author: Michael Leo
 * Date: 2023/7/25 10:02
 */
object FakeDI {
    val homeRepository by lazy {
        HomeRepository(HomeLocalDataSource())
    }
}