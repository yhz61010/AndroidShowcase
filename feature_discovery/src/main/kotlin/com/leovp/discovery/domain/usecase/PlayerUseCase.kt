package com.leovp.discovery.domain.usecase

import com.leovp.discovery.domain.model.SongModel
import com.leovp.discovery.domain.repository.PlayerRepository
import com.leovp.feature.base.http.model.ApiResponseModel.Companion.processApiResponseResult
import com.leovp.network.http.ResultBiz
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Author: Michael Leo
 * Date: 2024/8/26 14:00
 */
@Singleton
class PlayerUseCase
@Inject
constructor(
    // @PlayerRepositoryImplement
    private val repository: PlayerRepository,
) {
    suspend fun getSongInfo(vararg ids: Long): ResultBiz<List<SongModel>> {
        require(ids.isNotEmpty()) { "The parameter ids can't be empty." }
        return processApiResponseResult(repository.getSongInfo(*ids))
    }

    suspend fun getMusicComment(
        id: Long,
        limit: Int,
        offset: Int,
    ): ResultBiz<SongModel.CommentsModel> =
        processApiResponseResult(
            repository.getMusicComment(
                id = id,
                limit = limit,
                offset = offset
            )
        )

    suspend fun getSongRedCount(id: Long): ResultBiz<SongModel.RedCountModel> =
        processApiResponseResult(repository.getSongRedCount(id))

    suspend fun getSongUrlV1(
        id: Long,
        level: SongModel.Quality,
    ): ResultBiz<List<SongModel.UrlModel>> =
        processApiResponseResult(repository.getSongUrlV1(id, level))

    suspend fun checkMusic(
        id: Long,
        br: Int,
    ): ResultBiz<SongModel.MusicAvailableModel> =
        processApiResponseResult(repository.checkMusic(id, br))
}
