package com.leovp.discovery.data.datasource.api.model

import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import com.leovp.discovery.domain.model.SongModel
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable

/**
 * Author: Michael Leo
 * Date: 2024/9/19 13:45
 */

@Keep
@Immutable
@OptIn(InternalSerializationApi::class)
@Serializable
data class CommentApiModel(
    val user: UserApiModel,
    val status: Int,
    val commentId: Long,
    val content: String,
    val richContent: String? = null,
    val time: Long,
    val timeStr: String? = null,
    val needDisplayTime: Boolean = true,
    val likedCount: Long,
    val parentCommentId: Long = 0,
    val liked: Boolean,
    val ipLocation: IpLocationApiModel,
)

fun CommentApiModel.toDomainModel(): SongModel.Comment =
    SongModel.Comment(
        id = this.commentId,
        comment = this.content,
    )
