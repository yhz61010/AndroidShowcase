package com.leovp.feature_discovery.data.datasource

import com.leovp.feature_discovery.domain.model.SongModel

/**
 * Author: Michael Leo
 * Date: 2024/8/26 14:02
 */
interface PlayerDataSource {
    fun getSongInfo(vararg ids: Long): List<SongModel>

    fun getMusicComment(id: Long, limit: Int, offset: Int): SongModel.CommentsModel

    fun getSongRedCount(id: Long): SongModel.RedCountModel
}