@file:Suppress("unused")

package com.leovp.androidshowcase.testdata

import com.leovp.androidshowcase.data.datasource.MainDataSource
import com.leovp.androidshowcase.domain.usecase.MainUseCase
import com.leovp.androidshowcase.testdata.local_datasource.LocalMainDataSource
import com.leovp.androidshowcase.testdata.preview.PreviewMainRepositoryImpl
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
abstract class PreviewMainModule {

    @Singleton
    @Binds
    abstract fun bindLocalDataSource(dataSource: LocalMainDataSource): MainDataSource

    companion object {
        val previewMainUseCase by lazy {
            MainUseCase(PreviewMainRepositoryImpl(LocalMainDataSource()))
        }
    }
}
