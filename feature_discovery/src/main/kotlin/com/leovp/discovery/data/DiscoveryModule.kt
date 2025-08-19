@file:Suppress("unused")

package com.leovp.discovery.data

import com.leovp.discovery.data.repository.DiscoveryRepositoryImpl
import com.leovp.discovery.domain.repository.DiscoveryRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Author: Michael Leo
 * Date: 2023/9/13 10:49
 */

// @Qualifier
// @Retention(AnnotationRetention.BINARY)
// annotation class DiscoveryRepositoryImplement

@Module
@InstallIn(SingletonComponent::class)
abstract class DiscoveryModule {
    @Singleton
    // @DiscoveryRepositoryImplement
    @Binds
    abstract fun bindRepository(
        repository: DiscoveryRepositoryImpl,
    ): DiscoveryRepository
}
