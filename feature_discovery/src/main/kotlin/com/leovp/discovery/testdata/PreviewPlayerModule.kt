@file:Suppress("unused")

package com.leovp.discovery.testdata

import com.leovp.discovery.data.datasource.PlayerDataSource
import com.leovp.discovery.domain.usecase.PlayerUseCase
import com.leovp.discovery.testdata.local.datasource.LocalPlayerDataSource
import com.leovp.discovery.testdata.preview.PreviewPlayerRepositoryImpl
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
