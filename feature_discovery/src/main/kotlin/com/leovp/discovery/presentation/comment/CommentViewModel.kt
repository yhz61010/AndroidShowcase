package com.leovp.discovery.presentation.comment

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.leovp.discovery.domain.model.SongModel
import com.leovp.discovery.domain.usecase.GetDiscoveryListUseCase
import com.leovp.discovery.presentation.comment.CommentViewModel.CommentAction
import com.leovp.discovery.presentation.comment.CommentViewModel.CommentUiState
import com.leovp.feature.base.ui.Screen
import com.leovp.mvvm.BaseAction
import com.leovp.mvvm.BaseState
import com.leovp.mvvm.BaseViewModel
import com.leovp.mvvm.event.base.UiEventManager
import com.leovp.network.http.exception.ResultException
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Author: Michael Leo
 * Date: 2025/8/26 14:58
 */

@HiltViewModel
class CommentViewModel
@Inject
constructor(
    savedStateHandle: SavedStateHandle,
    private val useCase: GetDiscoveryListUseCase,
    uiEventManager: UiEventManager,
) : BaseViewModel<CommentUiState, CommentAction>(
    initialState = CommentUiState.Content(SongModel.CommentType.Recommended),
    uiEventManager = uiEventManager,
) {
    override fun getTagName() = "CmtVM"

    val songInfo = savedStateHandle.toRoute<Screen.Comment>()

    sealed interface CommentUiState : BaseState {
        data class Content(
            val commentType: SongModel.CommentType,
            val commentList: List<SongModel.CommentsModel> = emptyList(),
            val isLoading: Boolean = false,
            val exception: ResultException? = null,
        ) : CommentUiState
    }

    sealed interface CommentAction : BaseAction.Simple<CommentUiState> {
        data object ShowLoading : CommentAction {
            override fun reduce(state: CommentUiState): CommentUiState {
                val uiState = state as CommentUiState.Content
                return uiState.copy(isLoading = true)
            }
        }

        data class LoadSuccess(
            val commentType: SongModel.CommentType,
            val commentList: List<SongModel.CommentsModel> = emptyList(),
        ) : CommentAction {
            override fun reduce(state: CommentUiState): CommentUiState {
                val uiState = state as CommentUiState.Content
                return uiState.copy(
                    commentType = commentType,
                    commentList = commentList,
                )
            }
        }

        data class LoadFailure(
            private val err: ResultException,
        ) : CommentAction {
            override fun reduce(state: CommentUiState): CommentUiState {
                val uiState = state as CommentUiState.Content
                return uiState.copy(isLoading = false, exception = err)
            }
        }
    }
}
