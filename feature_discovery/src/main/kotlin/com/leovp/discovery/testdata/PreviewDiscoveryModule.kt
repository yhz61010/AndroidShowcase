@file:Suppress("unused")

package com.leovp.discovery.testdata

import com.leovp.discovery.data.datasource.DiscoveryDataSource
import com.leovp.discovery.domain.usecase.GetDiscoveryListUseCase
import com.leovp.discovery.testdata.local.datasource.LocalDiscoveryDataSource
import com.leovp.discovery.testdata.preview.PreviewDiscoveryRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Author: Michael Leo
 * Date: 2023/7/25 10:02
 */

@Module
@InstallIn(SingletonComponent::class)
abstract class PreviewDiscoveryModule {
    @Singleton
    @Binds
    abstract fun bindLocalDataSource(
        dataSource: LocalDiscoveryDataSource,
    ): DiscoveryDataSource

    companion object {
        val previewDiscoveryListUseCase by lazy {
            GetDiscoveryListUseCase(
                PreviewDiscoveryRepositoryImpl(LocalDiscoveryDataSource()),
            )
        }
    }
}
