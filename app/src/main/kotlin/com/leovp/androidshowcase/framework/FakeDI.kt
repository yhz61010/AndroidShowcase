package com.leovp.androidshowcase.framework

import com.leovp.androidshowcase.ui.tabs.discovery.data.FakeDiscoveryRepository
import com.leovp.androidshowcase.ui.tabs.discovery.data.DiscoveryLocalDataSource

/**
 * Author: Michael Leo
 * Date: 2023/7/25 10:02
 */
object FakeDI {
    val homeRepository by lazy {
        FakeDiscoveryRepository(DiscoveryLocalDataSource())
    }
}