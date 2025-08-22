package com.leovp.androidshowcase.presentation

import androidx.lifecycle.viewModelScope
import com.leovp.androidshowcase.domain.model.UnreadModel
import com.leovp.androidshowcase.domain.usecase.MainUseCase
import com.leovp.androidshowcase.presentation.MainViewModel.MainUiEvent.SearchEvent
import com.leovp.androidshowcase.presentation.MainViewModel.MainUiEvent.TopAppBarEvent
import com.leovp.androidshowcase.presentation.MainViewModel.UiState
import com.leovp.androidshowcase.presentation.MainViewModel.UiState.Content
import com.leovp.feature.base.event.UiEventManager
import com.leovp.feature.base.framework.BaseAction
import com.leovp.feature.base.framework.BaseState
import com.leovp.feature.base.framework.BaseViewModel
import com.leovp.feature.base.ui.Screen
import com.leovp.log.base.i
import com.leovp.log.base.w
import com.leovp.network.http.getOrDefault
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
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
    ) : BaseViewModel<UiState, BaseAction<UiState>>(Content(), uiEventManager) {
        companion object {
            private const val TAG = "MainVM"
        }

        private var job: Job? = null

        // Avoid recompose when pop back to this screen.
        // Because when pop back to this screen,
        // the NavGraphBuilder.composable() will be called again.
        init {
            loadData()
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

        private fun loadData(forceRefresh: Boolean = false) {
            val uiState = uiStateFlow.value as Content
            i(TAG) {
                "loadData(forceRefresh=$forceRefresh) uiState.isLoading=${uiState.isLoading}"
            }
            if (uiState.isLoading && !forceRefresh) {
                w(TAG) { "The data is loading now. Ignore loading." }
                return
            }

            sendAction(Action.ShowLoading)
            if (job != null) {
                job?.cancel()
                job = null
            }

            job =
                viewModelScope.launch {
                    val unreadListDeferred = async { useCase.getUnreadList("1") }
                    val unreadList =
                        unreadListDeferred.await().getOrDefault(
                            emptyList(),
                        )
                    sendAction(Action.LoadSuccess(unreadList))
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
                    navigate(Screen.SearchScreen.route)
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
            object ShowLoading : Action {
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
