package com.leovp.feature_discovery.domain.repository

import com.leovp.feature_discovery.domain.model.SongModel
import com.leovp.module.common.Result

/**
  * Author: Michael Leo
  * Date: 2024/8/26 13:56
  */
interface PlayerRepository {
    suspend fun getSongInfo(vararg ids: Long): Result<List<SongModel>>

    suspend fun getMusicComment(id: Long, limit: Int, offset: Int): Result<SongModel.CommentsModel>

    suspend fun getSongRedCount(id: Long): Result<SongModel.RedCountModel>
}