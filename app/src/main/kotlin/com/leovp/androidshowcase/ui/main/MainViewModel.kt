package com.leovp.androidshowcase.ui.main

import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leovp.androidshowcase.ui.main.data.MainRepository
import com.leovp.log.LogContext
import com.leovp.module.common.successOr
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Author: Michael Leo
 * Date: 2023/9/4 14:08
 */

class MainViewModel(private val repository: MainRepository) : ViewModel() {
    companion object {
        private const val TAG = "MainVM"
    }

    // Backing property to avoid state updates from other classes
    private val _uiState = MutableStateFlow(MainUiState(loading = true))

    // UI state exposed to the UI
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    init {
        refreshAll()
    }

    fun refreshAll() {
        LogContext.log.i(TAG, "Main unread -> refreshAll()")
        _uiState.update { it.copy(loading = true) }

        viewModelScope.launch {
            val unreadListDeferred = async { repository.getUnreadList("1") }

            val unreadList = unreadListDeferred.await().successOr(emptyList())

            _uiState.update {
                it.copy(
                    loading = false,
                    unreadList = unreadList,
                )
            }
        }
    }
}

@Keep
@Immutable
data class UnreadModel(
    val key: String,
    val value: Int
) {
    companion object {
        val MESSAGE = Screen.MemberCenterScreen.route

        val DISCOVERY = Screen.Discovery.route
        val MY = Screen.My.route
        val COMMUNITY = Screen.Community.route
    }
}

/**
 * UI state for the Main screen
 */
@Keep
data class MainUiState(
    val unreadList: List<UnreadModel> = emptyList(),
    val loading: Boolean = false
)
