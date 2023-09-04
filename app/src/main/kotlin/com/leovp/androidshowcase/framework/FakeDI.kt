package com.leovp.androidshowcase.framework

import com.leovp.androidshowcase.ui.tabs.discovery.data.DiscoveryLocalDataSource
import com.leovp.androidshowcase.ui.tabs.discovery.data.FakeDiscoveryRepository
import com.leovp.androidshowcase.ui.tabs.discovery.data.PreviewFakeDiscoveryRepository

/**
 * Author: Michael Leo
 * Date: 2023/7/25 10:02
 */
object FakeDI {
    val discoveryRepository by lazy {
        FakeDiscoveryRepository(DiscoveryLocalDataSource())
    }

    val discoveryRepositoryPreview by lazy {
        PreviewFakeDiscoveryRepository(DiscoveryLocalDataSource())
    }
}