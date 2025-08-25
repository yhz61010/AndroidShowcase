@file:Suppress("unused")

package com.leovp.feature.base.event

import com.leovp.compose.composable.event.UiEventManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

/**
 * Author: Michael Leo
 * Date: 2025/8/25 16:37
 */

@Module
@InstallIn(ViewModelComponent::class)
object UiEventModule {
    @Provides
    @ViewModelScoped
    fun provideUiEventManager(): UiEventManager {
        return UiEventManager()
    }
}
