package com.leovp.discovery.testdata.preview

import com.leovp.discovery.domain.model.SongModel
import com.leovp.discovery.domain.repository.PlayerRepository
import com.leovp.discovery.testdata.local.datasource.LocalPlayerDataSource
import com.leovp.feature.base.http.model.ApiResponseModel
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
        override suspend fun getSongInfo(
            vararg ids: Long,
        ): Result<ApiResponseModel<List<SongModel>>> =
            Result.Success(ApiResponseModel(result = dataSource.getSongInfo(*ids)))

        override suspend fun getMusicComment(
            id: Long,
            limit: Int,
            offset: Int,
        ): Result<ApiResponseModel<SongModel.CommentsModel>> =
            Result.Success(
                ApiResponseModel(
                    result =
                        dataSource.getMusicComment(
                            id = id,
                            limit = limit,
                            offset = offset,
                        ),
                ),
            )

        override suspend fun getSongRedCount(
            id: Long,
        ): Result<ApiResponseModel<SongModel.RedCountModel>> =
            Result.Success(ApiResponseModel(result = dataSource.getSongRedCount(id)))

        override suspend fun getSongUrlV1(
            id: Long,
            level: SongModel.Quality,
        ): Result<ApiResponseModel<List<SongModel.UrlModel>>> =
            Result.Success(ApiResponseModel(result = dataSource.getSongUrlV1(id, level)))

        override suspend fun checkMusic(
            id: Long,
            br: Int,
        ): Result<ApiResponseModel<SongModel.MusicAvailableModel>> =
            Result.Success(ApiResponseModel(result = dataSource.checkMusic(id, br)))
    }
