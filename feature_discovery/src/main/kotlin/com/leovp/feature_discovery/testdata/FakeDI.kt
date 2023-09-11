package com.leovp.feature_discovery.testdata

import com.leovp.feature_discovery.data.repository.DiscoveryRepositoryImpl
import com.leovp.feature_discovery.domain.usecase.GetDiscoveryListUseCase
import com.leovp.feature_discovery.testdata.local_datasource.LocalDiscoveryDataSource
import com.leovp.feature_discovery.testdata.preview.PreviewDiscoveryRepository

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
}
