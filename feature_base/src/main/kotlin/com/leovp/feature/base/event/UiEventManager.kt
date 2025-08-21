@file:Suppress("unused")

package com.leovp.feature.base.event

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Author: Michael Leo
 * Date: 2025/8/21 10:33
 */

@Singleton
class UiEventManager
    @Inject
    constructor() {
        private val eventChannel = Channel<UiEvent>(Channel.UNLIMITED)
        val events = eventChannel.receiveAsFlow()

        suspend fun sendEvent(event: UiEvent) {
            eventChannel.send(event)
        }

        fun sendEventSync(event: UiEvent) {
            eventChannel.trySend(event)
        }
    }
