package com.leovp.feature_discovery.data.repository

import com.drake.net.Get
import com.leovp.feature_discovery.data.datasource.PlayerDataSource
import com.leovp.feature_discovery.data.datasource.api.model.toDomainModel
import com.leovp.feature_discovery.data.datasource.api.response.CommentResponse
import com.leovp.feature_discovery.data.datasource.api.response.SongDetailResponse
import com.leovp.feature_discovery.data.datasource.api.response.SongRedCountResponse
import com.leovp.feature_discovery.domain.model.SongModel
import com.leovp.feature_discovery.domain.repository.PlayerRepository
import com.leovp.module.common.GlobalConst
import com.leovp.module.common.Result
import com.leovp.module.common.result
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

/**
 * Author: Michael Leo
 * Date: 2024/8/26 14:02
 */

class PlayerRepositoryImpl @Inject constructor(
    private val dataSource: PlayerDataSource
) : PlayerRepository {
    override suspend fun getSongInfo(vararg ids: Long): Result<List<SongModel>> =
        result(Dispatchers.IO) {
            require(ids.isNotEmpty()) { "The parameter ids can't be empty." }
            Get<SongDetailResponse>(GlobalConst.HTTP_GET_SONG_DETAIL) {
                param("ids", ids.joinToString(","))
            }.await().songs.map { it.toDomainModel() }
        }

    override suspend fun getMusicComment(
        id: Long,
        limit: Int,
        offset: Int
    ): Result<SongModel.CommentsModel> = result(Dispatchers.IO) {
        val respResult = Get<CommentResponse>(GlobalConst.HTTP_GET_MUSIC_COMMENT) {
            param("id", id)
            param("limit", limit)
            param("offset", offset)
        }.await()

        SongModel.CommentsModel(
            totalComments = respResult.total,
            comments = respResult.comments.map { it.toDomainModel() },
            topComments = respResult.topComments.map { it.toDomainModel() },
            hotComments = respResult.hotComments.map { it.toDomainModel() }
        )
    }

    override suspend fun getSongRedCount(id: Long): Result<SongModel.RedCountModel> =
        result(Dispatchers.IO) {
            Get<SongRedCountResponse>(GlobalConst.HTTP_GET_SONG_RED_COUNT) {
                param("id", id)
            }.await().data.toDomainModel()
        }
}
