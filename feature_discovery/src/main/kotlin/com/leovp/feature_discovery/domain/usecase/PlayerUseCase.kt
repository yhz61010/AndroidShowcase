package com.leovp.feature_discovery.domain.usecase

import com.leovp.feature_discovery.data.PlayerRepositoryImplement
import com.leovp.feature_discovery.domain.model.SongModel
import com.leovp.feature_discovery.domain.repository.PlayerRepository
import com.leovp.module.common.Result
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Author: Michael Leo
 * Date: 2024/8/26 14:00
 */
@Singleton
class PlayerUseCase @Inject constructor(
    @PlayerRepositoryImplement private val repository: PlayerRepository
) {
    suspend fun getSongInfo(vararg ids: Long): Result<List<SongModel>> {
        require(ids.isNotEmpty()) { "The parameter ids can't be empty." }
        return repository.getSongInfo(*ids)
    }

    suspend fun getMusicComment(
        id: Long,
        limit: Int,
        offset: Int
    ): Result<SongModel.CommentsModel> {
        return repository.getMusicComment(id = id, limit = limit, offset = offset)
    }

    suspend fun getSongRedCount(id: Long): Result<SongModel.RedCountModel> {
        return repository.getSongRedCount(id)
    }

    suspend fun getSongUrlV1(id: Long, level: SongModel.Quality): Result<List<SongModel.UrlModel>> {
        return repository.getSongUrlV1(id, level)
    }

    suspend fun checkMusic(id: Long, br: Int): Result<SongModel.MusicAvailableModel> {
        return repository.checkMusic(id, br)
    }
}
