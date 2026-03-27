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
import com.leovp.feature.base.http.model.ApiResponseModel
import com.leovp.network.http.Result
import com.leovp.network.http.net.result
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
        override suspend fun getSongInfo(
            vararg ids: Long,
        ): Result<ApiResponseModel<List<SongModel>>> =
            result {
                require(ids.isNotEmpty()) { "The parameter ids can't be empty." }
                val respResult =
                    Get<SongDetailResponse>(GlobalConst.HTTP_GET_SONG_DETAIL) {
                        param("ids", ids.joinToString(","))
                    }.await()

                ApiResponseModel(
                    code = respResult.code,
                    message = respResult.message,
                    result = respResult.songs.map { it.toDomainModel() },
                )
            }

        override suspend fun getMusicComment(
            id: Long,
            limit: Int,
            offset: Int,
        ): Result<ApiResponseModel<SongModel.CommentsModel>> =
            result {
                val respResult =
                    Get<CommentResponse>(GlobalConst.HTTP_GET_MUSIC_COMMENT) {
                        param("id", id)
                        param("limit", limit)
                        param("offset", offset)
                    }.await()

                ApiResponseModel(
                    code = respResult.code,
                    message = respResult.message,
                    result =
                        SongModel.CommentsModel(
                            totalComments = respResult.total,
                            comments = respResult.comments.map { it.toDomainModel() },
                            topComments =
                                respResult.topComments.map {
                                    it.toDomainModel()
                                },
                            hotComments =
                                respResult.hotComments.map {
                                    it.toDomainModel()
                                },
                        ),
                )
            }

        override suspend fun getSongRedCount(
            id: Long,
        ): Result<ApiResponseModel<SongModel.RedCountModel>> =
            result {
                val respResult =
                    Get<SongRedCountResponse>(GlobalConst.HTTP_GET_SONG_RED_COUNT) {
                        param("id", id)
                    }.await()

                ApiResponseModel(
                    code = respResult.code,
                    message = respResult.message,
                    result = respResult.data.toDomainModel(),
                )
            }

        override suspend fun getSongUrlV1(
            id: Long,
            level: SongModel.Quality,
        ): Result<ApiResponseModel<List<SongModel.UrlModel>>> =
            result {
                val respResult =
                    Get<SongUrlResponse>(GlobalConst.HTTP_GET_SONG_URL_V1) {
                        param("id", id)
                        param("level", level.name.lowercase())
                    }.await()

                ApiResponseModel(
                    code = respResult.code,
                    message = respResult.message,
                    result = respResult.data.map { it.toDomainModel() },
                )
            }

        override suspend fun checkMusic(
            id: Long,
            br: Int,
        ): Result<ApiResponseModel<SongModel.MusicAvailableModel>> =
            result {
                val respResult =
                    Get<MusicAvailableResponse>(GlobalConst.HTTP_GET_CHECK_MUSIC) {
                        param("id", id)
                        param("br", br)
                    }.await()

                ApiResponseModel(
                    code = respResult.code,
                    message = respResult.message,
                    result =
                        SongModel.MusicAvailableModel(
                            success = respResult.success,
                            message = respResult.message,
                        ),
                )
            }
    }
