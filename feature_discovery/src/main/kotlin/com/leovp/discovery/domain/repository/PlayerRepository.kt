package com.leovp.discovery.domain.repository

import com.leovp.discovery.domain.model.SongModel
import com.leovp.feature.base.http.model.ApiResponseModel
import com.leovp.network.http.Result

/**
 * Author: Michael Leo
 * Date: 2024/8/26 13:56
 */
interface PlayerRepository {
    suspend fun getSongInfo(vararg ids: Long): Result<ApiResponseModel<List<SongModel>>>

    suspend fun getMusicComment(
        id: Long,
        limit: Int,
        offset: Int,
    ): Result<ApiResponseModel<SongModel.CommentsModel>>

    suspend fun getSongRedCount(id: Long): Result<ApiResponseModel<SongModel.RedCountModel>>

    suspend fun getSongUrlV1(
        id: Long,
        level: SongModel.Quality,
    ): Result<ApiResponseModel<List<SongModel.UrlModel>>>

    suspend fun checkMusic(
        id: Long,
        br: Int,
    ): Result<ApiResponseModel<SongModel.MusicAvailableModel>>
}
