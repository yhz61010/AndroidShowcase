package com.leovp.androidshowcase.testdata

import com.leovp.androidshowcase.testdata.local_datasource.LocalDiscoveryDataSource
import com.leovp.androidshowcase.testdata.local_datasource.LocalMainDataSource
import com.leovp.androidshowcase.testdata.preview.PreviewDiscoveryRepository
import com.leovp.androidshowcase.testdata.preview.PreviewMainRepository
import com.leovp.androidshowcase.ui.main.data.MainRepositoryImpl
import com.leovp.androidshowcase.ui.tabs.discovery.data.repository.DiscoveryRepositoryImpl
import com.leovp.androidshowcase.ui.tabs.discovery.domain.usecase.GetDiscoveryListUseCase

/**
 * Author: Michael Leo
 * Date: 2023/7/25 10:02
 */
object FakeDI {
    val discoveryListUseCase by lazy {
        GetDiscoveryListUseCase(DiscoveryRepositoryImpl(LocalDiscoveryDataSource()))
    }

    val previewDiscoveryListUseCase by lazy {
        GetDiscoveryListUseCase(PreviewDiscoveryRepository(LocalDiscoveryDataSource()))
    }

    // ==========

    val mainUnreadRepository by lazy {
        MainRepositoryImpl(LocalMainDataSource())
    }

    val previewMainUnreadRepository by lazy {
        PreviewMainRepository(LocalMainDataSource())
    }
}