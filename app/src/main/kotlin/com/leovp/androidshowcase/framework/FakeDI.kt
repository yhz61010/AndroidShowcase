package com.leovp.androidshowcase.framework

import com.leovp.androidshowcase.ui.main.data.FakeMainRepository
import com.leovp.androidshowcase.ui.main.data.MainLocalDataSource
import com.leovp.androidshowcase.ui.main.data.PreviewFakeMainRepository
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

    val mainUnreadRepository by lazy {
        FakeMainRepository(MainLocalDataSource())
    }

    // ==========

    val previewDiscoveryRepository by lazy {
        PreviewFakeDiscoveryRepository(DiscoveryLocalDataSource())
    }

    val previewMainUnreadRepository by lazy {
        PreviewFakeMainRepository(MainLocalDataSource())
    }
}