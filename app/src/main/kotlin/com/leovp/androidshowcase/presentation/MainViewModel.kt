package com.leovp.androidshowcase.presentation

import androidx.lifecycle.viewModelScope
import com.leovp.androidshowcase.domain.model.UnreadModel
import com.leovp.androidshowcase.domain.usecase.MainUseCase
import com.leovp.androidshowcase.presentation.MainViewModel.MainUiEvent.SearchEvent
import com.leovp.androidshowcase.presentation.MainViewModel.MainUiEvent.TopAppBarEvent
import com.leovp.androidshowcase.presentation.MainViewModel.UiState
import com.leovp.androidshowcase.presentation.MainViewModel.UiState.Content
import com.leovp.feature.base.ui.Screen
import com.leovp.json.toJsonString
import com.leovp.log.base.i
import com.leovp.log.base.w
import com.leovp.mvvm.BaseAction
import com.leovp.mvvm.BaseState
import com.leovp.mvvm.BaseViewModel
import com.leovp.mvvm.event.base.UiEventManager
import com.leovp.mvvm.http.dispatchBizResult
import dagger.hilt.android.lifecycle.HiltViewModel
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
) : BaseViewModel<UiState, BaseAction<UiState>>(Content(), uiEventManager) {
    override fun getTagName() = "MainVM"

    // Avoid recompose when pop back to this screen.
    // Because when pop back to this screen,
    // the NavGraphBuilder.composable() will be called again.
    init {
        onEvent(MainUiEvent.Refresh)
    }

    fun onEvent(event: MainUiEvent) {
        viewModelScope.launch {
            when (event) {
                is SearchEvent -> handleSearchEvent(event)
                is TopAppBarEvent -> handleAppBarEvent(event)
                MainUiEvent.Refresh -> loadData(forceRefresh = true)
            }
        }
    }

    @Suppress("SameParameterValue")
    private fun loadData(forceRefresh: Boolean = false) {
        val uiState = uiStateFlow.value as Content
        i(tag) {
            "loadData(forceRefresh=$forceRefresh) uiState.isLoading=${uiState.isLoading}"
        }
        if (uiState.isLoading && !forceRefresh) {
            w(tag) { "The data is loading now. Ignore loading." }
            return
        }
        sendAction(Action.ShowLoading)
        viewModelScope.launch {
            val unreadListBizResult = useCase.getUnreadList("1")
            dispatchBizResult(
                uiEventManager = uiEventManager,
                bizResult = unreadListBizResult,
                onSuccess = { unreadList, _ ->
                    i(tag) { "unreadList=${unreadList.toJsonString()}" }
                    sendAction(Action.LoadSuccess(unreadList))
                }
            )
        }
    }

    private fun handleAppBarEvent(event: TopAppBarEvent) {
        when (event) {
            TopAppBarEvent.RecordingClick -> {
                showToast("Recording is not yet implemented.")
            }

            TopAppBarEvent.MenuClick -> {
                error("MenuClick should be implemented in your Screen.")
            }
        }
    }

    private fun handleSearchEvent(event: SearchEvent) {
        when (event) {
            SearchEvent.SearchClick -> {
                navigate(Screen.Search)
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
        data object ShowLoading : Action {
            override fun reduce(state: UiState): UiState {
                val uiState = state as Content
                return uiState.copy(isLoading = true)
            }
        }

        data class LoadSuccess(
            val unreadList: List<UnreadModel>,
        ) : Action {
            override fun reduce(state: UiState): UiState =
                Content(
                    unreadList = unreadList,
                    isLoading = false,
                )
        }

        // data object LoadFailure : Action {
        //     override fun execute(state: UiState): UiState = Error
        // }
    }

    sealed interface UiState : BaseState {
        data class Content(
            val unreadList: List<UnreadModel> = emptyList(),
            val isLoading: Boolean = false,
        ) : UiState
        // data object Error : UiState
    }
}
