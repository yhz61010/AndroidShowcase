package com.leovp.androidshowcase.presentation

import androidx.annotation.Keep
import androidx.lifecycle.viewModelScope
import com.leovp.androidshowcase.domain.model.UnreadModel
import com.leovp.androidshowcase.domain.usecase.MainUseCase
import com.leovp.androidshowcase.presentation.MainViewModel.MainUiEvent.SearchEvent
import com.leovp.androidshowcase.presentation.MainViewModel.MainUiEvent.TopAppBarEvent
import com.leovp.androidshowcase.presentation.MainViewModel.UiState
import com.leovp.androidshowcase.presentation.MainViewModel.UiState.Content
import com.leovp.androidshowcase.presentation.MainViewModel.UiState.Loading
import com.leovp.androidshowcase.ui.AppNavigationActions
import com.leovp.androidshowcase.ui.Screen
import com.leovp.feature.base.event.UiEventManager
import com.leovp.feature.base.framework.BaseAction
import com.leovp.feature.base.framework.BaseState
import com.leovp.feature.base.framework.BaseViewModel
import com.leovp.log.LogContext
import com.leovp.network.http.getOrDefault
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Author: Michael Leo
 * Date: 2023/9/4 14:08
 */

@HiltViewModel
class MainViewModel
@Inject
constructor(
    private val useCase: MainUseCase,
    uiEventManager: UiEventManager,
) : BaseViewModel<UiState, BaseAction<UiState>>(Loading, uiEventManager) {
    companion object {
        private const val TAG = "MainVM"
    }

    fun onEnter() {
        LogContext.log.i(TAG, "Main unread -> onEnter()")

        viewModelScope.launch {
            val unreadListDeferred = async { useCase.getUnreadList("1") }
            val unreadList =
                unreadListDeferred.await().getOrDefault(
                    emptyList(),
                )
            sendAction(Action.LoadSuccess(unreadList))
        }
    }

    fun handleTopAppBarEvent(event: TopAppBarEvent) {
        when (event) {
            TopAppBarEvent.RecordingClick -> {
                showToast("Recording is not yet implemented.")
            }

            TopAppBarEvent.MenuClick -> {
                error("MenuClick should be implemented in your Screen.")
            }
        }
    }

    fun handleTopAppBarContentEvent(
        navigationActions: AppNavigationActions,
        event: SearchEvent,
    ) {
        when (event) {
            SearchEvent.SearchClick -> {
                navigationActions.navigate(Screen.SearchScreen.route)
            }

            SearchEvent.ScanClick -> {
                showToast("Scan is not yet implemented.")
            }
        }
    }

    sealed interface MainUiEvent {
        sealed interface TopAppBarEvent : MainUiEvent {
            data object MenuClick : TopAppBarEvent

            data object RecordingClick : TopAppBarEvent
        }

        sealed interface SearchEvent : MainUiEvent {
            data object SearchClick : SearchEvent

            data object ScanClick : SearchEvent
        }

        data object Refresh : MainUiEvent
    }

    sealed interface Action : BaseAction.Simple<UiState> {
        class LoadSuccess(
            val unreadList: List<UnreadModel>,
        ) : Action {
            override fun reduce(state: UiState): UiState = Content(unreadList)
        }

        // object LoadFailure : Action {
        //     override fun execute(state: UiState): UiState = Error
        // }
    }

    @Keep
    sealed interface UiState : BaseState {
        data object Loading : UiState

        data class Content(
            val unreadList: List<UnreadModel> = emptyList(),
        ) : UiState
        // object Error : UiState
    }
}
