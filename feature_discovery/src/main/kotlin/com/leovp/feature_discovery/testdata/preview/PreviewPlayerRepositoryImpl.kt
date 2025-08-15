package com.leovp.feature_discovery.testdata.preview

import com.leovp.feature_discovery.domain.model.SongModel
import com.leovp.feature_discovery.domain.repository.PlayerRepository
import com.leovp.feature_discovery.testdata.local_datasource.LocalPlayerDataSource
import com.leovp.network.http.Result
import javax.inject.Inject

/**
 * Author: Michael Leo
 * Date: 2023/9/4 13:55
 */
class PreviewPlayerRepositoryImpl @Inject constructor(
    private val dataSource: LocalPlayerDataSource
) : PlayerRepository {
    override suspend fun getSongInfo(vararg ids: Long): Result<List<SongModel>> {
        return Result.Success(dataSource.getSongInfo(*ids))
    }

    override suspend fun getMusicComment(
        id: Long,
        limit: Int,
        offset: Int
    ): Result<SongModel.CommentsModel> {
        return Result.Success(dataSource.getMusicComment(id = id, limit = limit, offset = offset))
    }

    override suspend fun getSongRedCount(id: Long): Result<SongModel.RedCountModel> {
        return Result.Success(dataSource.getSongRedCount(id))
    }

    override suspend fun getSongUrlV1(
        id: Long,
        level: SongModel.Quality
    ): Result<List<SongModel.UrlModel>> {
        return Result.Success(dataSource.getSongUrlV1(id, level))
    }

    override suspend fun checkMusic(
        id: Long,
        br: Int
    ): Result<SongModel.MusicAvailableModel> {
        return Result.Success(dataSource.checkMusic(id, br))
    }
}