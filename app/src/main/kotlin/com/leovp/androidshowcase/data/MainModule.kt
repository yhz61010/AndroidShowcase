@file:Suppress("unused")

package com.leovp.androidshowcase.data

import com.leovp.androidshowcase.data.repository.MainRepositoryImpl
import com.leovp.androidshowcase.domain.repository.MainRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

/**
 * Author: Michael Leo
 * Date: 2023/9/12 16:14
 */

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainRepositoryImplement

@Module
@InstallIn(SingletonComponent::class)
abstract class MainModule {

    @Singleton
    @MainRepositoryImplement
    @Binds
    abstract fun bindRepository(repository: MainRepositoryImpl): MainRepository
}
