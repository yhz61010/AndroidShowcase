@file:Suppress("unused")

package com.leovp.feature_discovery.testdata

import com.leovp.feature_discovery.data.datasource.PlayerDataSource
import com.leovp.feature_discovery.domain.usecase.PlayerUseCase
import com.leovp.feature_discovery.testdata.local_datasource.LocalPlayerDataSource
import com.leovp.feature_discovery.testdata.preview.PreviewPlayerRepositoryImpl
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
abstract class PreviewPlayerModule {

    @Singleton
    @Binds
    abstract fun bindLocalDataSource(dataSource: LocalPlayerDataSource): PlayerDataSource

    companion object {
        val previewPlayerUseCase by lazy {
            PlayerUseCase(PreviewPlayerRepositoryImpl(LocalPlayerDataSource()))
        }
    }
}
