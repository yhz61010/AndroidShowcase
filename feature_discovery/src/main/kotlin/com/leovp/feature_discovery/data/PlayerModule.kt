@file:Suppress("unused")

package com.leovp.feature_discovery.data

import com.leovp.feature_discovery.data.repository.PlayerRepositoryImpl
import com.leovp.feature_discovery.domain.repository.PlayerRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Author: Michael Leo
 * Date: 2024/8/26 14:00
 */

// @Qualifier
// @Retention(AnnotationRetention.BINARY)
// annotation class PlayerRepositoryImplement

@Module
@InstallIn(SingletonComponent::class)
abstract class PlayerModule {
    @Singleton
    // @PlayerRepositoryImplement
    @Binds
    abstract fun bindRepository(repository: PlayerRepositoryImpl): PlayerRepository
}