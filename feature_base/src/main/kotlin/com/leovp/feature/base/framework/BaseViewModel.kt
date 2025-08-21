@file:Suppress("unused")

package com.leovp.feature.base.framework

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leovp.feature.base.BuildConfig
import com.leovp.feature.base.event.ToastDuration
import com.leovp.feature.base.event.UiEvent
import com.leovp.feature.base.event.UiEventManager
import com.leovp.mvvm.viewmodel.lifecycle.LifecycleAware
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

abstract class BaseViewModel<State : BaseState, Action : BaseAction<State>>(
    initialState: State,
    private val uiEventManager: UiEventManager? = null,
) : ViewModel(),
    LifecycleAware {
    companion object {
        private const val TAG = "BaseVM"
    }

    private val _uiStateFlow = MutableStateFlow(initialState)
    val uiStateFlow = _uiStateFlow.asStateFlow()

    private var stateTimeTravelDebugger: StateTimeTravelDebugger? = null

    // ===== UI Event - Start ==========
    val uiEvents: Flow<UiEvent>? = uiEventManager?.events
    val requireUiEvents: Flow<UiEvent> by lazy {
        uiEventManager?.events ?: throw NullPointerException("uiEventManager is null")
    }

    protected fun showToast(
        message: String,
        duration: ToastDuration = ToastDuration.SHORT,
    ) {
        viewModelScope.launch {
            uiEventManager?.sendEvent(UiEvent.ShowToast(message, duration))
        }
    }

    protected fun showSnackbar(
        message: String,
        actionLabel: String? = null,
        onAction: (() -> Unit)? = null,
    ) {
        viewModelScope.launch {
            uiEventManager?.sendEvent(
                UiEvent.ShowSnackbar(message, actionLabel, onAction),
            )
        }
    }

    protected fun navigate(route: String) {
        viewModelScope.launch {
            uiEventManager?.sendEvent(UiEvent.Navigate(route))
        }
    }

    protected fun navigateBack() {
        viewModelScope.launch {
            uiEventManager?.sendEvent(UiEvent.NavigateBack)
        }
    }

    protected fun showLoading() {
        viewModelScope.launch {
            uiEventManager?.sendEvent(UiEvent.ShowLoading)
        }
    }

    protected fun hideLoading() {
        viewModelScope.launch {
            uiEventManager?.sendEvent(UiEvent.HideLoading)
        }
    }

    protected fun showDialog(
        title: String,
        message: String,
        positiveButton: String,
        negativeButton: String? = null,
        onPositive: () -> Unit,
        onNegative: () -> Unit = {},
    ) {
        viewModelScope.launch {
            uiEventManager?.sendEvent(
                UiEvent.ShowDialog(
                    title = title,
                    message = message,
                    positiveButton = positiveButton,
                    negativeButton = negativeButton,
                    onPositive = onPositive,
                    onNegative = onNegative,
                ),
            )
        }
    }
    // ===== UI Event - End ==========

    init {
        @Suppress("SENSELESS_COMPARISON")
        if (BuildConfig.DEBUG) {
            stateTimeTravelDebugger = StateTimeTravelDebugger(this::class.java.simpleName)
        }
    }

    // Delegate handles state event deduplication (multiple states of the same type holding the same data
    // will not be emitted multiple times to UI)
    private var state by Delegates.observable(initialState) { _, old, new ->
        if (old != new) {
            viewModelScope.launch { _uiStateFlow.value = new }

            stateTimeTravelDebugger?.apply {
                addStateTransition(old, new)
                logLast()
            }
        }
    }

    protected fun sendAction(action: BaseAction.Simple<State>) {
        stateTimeTravelDebugger?.addAction(action)
        state = action.reduce(state)
    }

    protected fun <T : Any> sendAction(
        action: BaseAction.WithExtra<State, T>,
        obj: T,
    ) {
        stateTimeTravelDebugger?.addAction(action)
        state = action.reduce(state, obj)
    }

    protected fun <T> sendAction(
        action: BaseAction.WithOptional<State, T>,
        obj: T? = null,
    ) {
        stateTimeTravelDebugger?.addAction(action)
        state = action.reduce(state, obj)
    }
}
