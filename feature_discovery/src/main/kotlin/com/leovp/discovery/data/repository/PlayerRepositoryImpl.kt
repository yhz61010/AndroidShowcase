package com.leovp.discovery.data.repository

import com.drake.net.Get
import com.leovp.discovery.data.datasource.PlayerDataSource
import com.leovp.discovery.data.datasource.api.model.toDomainModel
import com.leovp.discovery.data.datasource.api.response.CommentResponse
import com.leovp.discovery.data.datasource.api.response.MusicAvailableResponse
import com.leovp.discovery.data.datasource.api.response.SongDetailResponse
import com.leovp.discovery.data.datasource.api.response.SongRedCountResponse
import com.leovp.discovery.data.datasource.api.response.SongUrlResponse
import com.leovp.discovery.domain.model.SongModel
import com.leovp.discovery.domain.repository.PlayerRepository
import com.leovp.feature.base.GlobalConst
import com.leovp.network.http.Result
import com.leovp.network.http.net.result
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

/**
 * Author: Michael Leo
 * Date: 2024/8/26 14:02
 */

class PlayerRepositoryImpl
    @Inject
    constructor(
        @Suppress("unused") private val dataSource: PlayerDataSource,
    ) : PlayerRepository {
        override suspend fun getSongInfo(vararg ids: Long): Result<List<SongModel>> =
            result(Dispatchers.IO) {
                require(
                    ids.isNotEmpty(),
                ) { "The parameter ids can't be empty." }
                Get<SongDetailResponse>(GlobalConst.HTTP_GET_SONG_DETAIL) {
                    param("ids", ids.joinToString(","))
                }.await().songs.map { it.toDomainModel() }
            }

        override suspend fun getMusicComment(
            id: Long,
            limit: Int,
            offset: Int,
        ): Result<SongModel.CommentsModel> =
            result(Dispatchers.IO) {
                val respResult =
                    Get<CommentResponse>(GlobalConst.HTTP_GET_MUSIC_COMMENT) {
                        param("id", id)
                        param("limit", limit)
                        param("offset", offset)
                    }.await()

                SongModel.CommentsModel(
                    totalComments = respResult.total,
                    comments = respResult.comments.map { it.toDomainModel() },
                    topComments =
                        respResult.topComments.map {
                            it
                                .toDomainModel()
                        },
                    hotComments =
                        respResult.hotComments.map {
                            it
                                .toDomainModel()
                        },
                )
            }

        override suspend fun getSongRedCount(id: Long): Result<SongModel.RedCountModel> =
            result(Dispatchers.IO) {
                Get<SongRedCountResponse>(GlobalConst.HTTP_GET_SONG_RED_COUNT) {
                    param("id", id)
                }.await().data.toDomainModel()
            }

        override suspend fun getSongUrlV1(
            id: Long,
            level: SongModel.Quality,
        ): Result<List<SongModel.UrlModel>> =
            result(Dispatchers.IO) {
                Get<SongUrlResponse>(GlobalConst.HTTP_GET_SONG_URL_V1) {
                    param("id", id)
                    param("level", level.name.lowercase())
                }.await().data.map { it.toDomainModel() }
            }

        override suspend fun checkMusic(
            id: Long,
            br: Int,
        ): Result<SongModel.MusicAvailableModel> =
            result(Dispatchers.IO) {
                Get<MusicAvailableResponse>(GlobalConst.HTTP_GET_CHECK_MUSIC) {
                    param("id", id)
                    param("br", br)
                }.await().let {
                    SongModel.MusicAvailableModel(
                        success = it.success,
                        message = it.message,
                    )
                }
            }
    }
