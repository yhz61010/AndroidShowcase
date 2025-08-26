package com.leovp.discovery.presentation.comment

import com.leovp.compose.composable.event.UiEventManager
import com.leovp.discovery.domain.model.SongModel
import com.leovp.discovery.domain.usecase.GetDiscoveryListUseCase
import com.leovp.discovery.presentation.comment.CommentViewModel.CommentAction
import com.leovp.discovery.presentation.comment.CommentViewModel.CommentUiState
import com.leovp.feature.base.framework.BaseAction
import com.leovp.feature.base.framework.BaseState
import com.leovp.feature.base.framework.BaseViewModel
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
        private val useCase: GetDiscoveryListUseCase,
        uiEventManager: UiEventManager,
    ) : BaseViewModel<CommentUiState, CommentAction>(
            initialState = CommentUiState.Content(SongModel.CommentType.Recommended),
            uiEventManager = uiEventManager,
        ) {
        companion object {
            private const val TAG = "CmtVM"
        }

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
