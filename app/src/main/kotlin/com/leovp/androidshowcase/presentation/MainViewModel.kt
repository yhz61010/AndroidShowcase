package com.leovp.androidshowcase.presentation

import androidx.annotation.Keep
import androidx.lifecycle.viewModelScope
import com.leovp.androidshowcase.domain.model.UnreadModel
import com.leovp.androidshowcase.domain.usecase.MainUseCase
import com.leovp.androidshowcase.presentation.MainViewModel.UiState
import com.leovp.androidshowcase.presentation.MainViewModel.UiState.Content
import com.leovp.androidshowcase.presentation.MainViewModel.UiState.Loading
import com.leovp.log.LogContext
import com.leovp.mvvm.viewmodel.BaseAction
import com.leovp.mvvm.viewmodel.BaseState
import com.leovp.mvvm.viewmodel.BaseViewModel
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
) : BaseViewModel<UiState, BaseAction<UiState>>(Loading) {
    companion object {
        private const val TAG = "MainVM"
    }

    init {
        onEnter()
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

    sealed interface MainUiEvent {
        data object NavigationClick : MainUiEvent
        data object ActionClick : MainUiEvent
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
