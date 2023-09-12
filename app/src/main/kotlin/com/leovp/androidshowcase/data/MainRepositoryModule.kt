@file:Suppress("unused")

package com.leovp.androidshowcase.data

import com.leovp.androidshowcase.data.datasource.MainDataSource
import com.leovp.androidshowcase.data.repository.MainRepositoryImpl
import com.leovp.androidshowcase.domain.repository.MainRepository
import com.leovp.androidshowcase.testdata.local_datasource.LocalMainDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Author: Michael Leo
 * Date: 2023/9/12 16:14
 */

@Module
@InstallIn(SingletonComponent::class)
abstract class MainRepositoryModule {
    @Singleton
    @Binds
    abstract fun bindMainRepository(mainRepository: MainRepositoryImpl): MainRepository

    @Singleton
    @Binds
    abstract fun bindMainDataSource(dataSource: LocalMainDataSource): MainDataSource
}