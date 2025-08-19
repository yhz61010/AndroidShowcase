package com.leovp.discovery.testdata.preview

import com.leovp.discovery.domain.model.SongModel
import com.leovp.discovery.domain.repository.PlayerRepository
import com.leovp.discovery.testdata.local.datasource.LocalPlayerDataSource
import com.leovp.network.http.Result
import javax.inject.Inject

/**
 * Author: Michael Leo
 * Date: 2023/9/4 13:55
 */
class PreviewPlayerRepositoryImpl
    @Inject
    constructor(
        private val dataSource: LocalPlayerDataSource,
    ) : PlayerRepository {
        override suspend fun getSongInfo(vararg ids: Long): Result<List<SongModel>> =
            Result.Success(dataSource.getSongInfo(*ids))

        override suspend fun getMusicComment(
            id: Long,
            limit: Int,
            offset: Int,
        ): Result<SongModel.CommentsModel> =
            Result.Success(
                dataSource.getMusicComment(
                    id = id,
                    limit = limit,
                    offset = offset,
                ),
            )

        override suspend fun getSongRedCount(id: Long): Result<SongModel.RedCountModel> =
            Result.Success(dataSource.getSongRedCount(id))

        override suspend fun getSongUrlV1(
            id: Long,
            level: SongModel.Quality,
        ): Result<List<SongModel.UrlModel>> = Result.Success(dataSource.getSongUrlV1(id, level))

        override suspend fun checkMusic(
            id: Long,
            br: Int,
        ): Result<SongModel.MusicAvailableModel> = Result.Success(dataSource.checkMusic(id, br))
    }
