package com.leovp.feature_discovery.testdata.preview

import com.leovp.feature_discovery.domain.model.SongItem
import com.leovp.feature_discovery.domain.repository.PlayerRepository
import com.leovp.feature_discovery.testdata.local_datasource.LocalPlayerDataSource
import com.leovp.module.common.Result
import javax.inject.Inject

/**
 * Author: Michael Leo
 * Date: 2023/9/4 13:55
 */
class PreviewPlayerRepositoryImpl @Inject constructor(
    private val dataSource: LocalPlayerDataSource
) : PlayerRepository {
    override suspend fun getSongInfo(artist: String, track: String): Result<SongItem> {
        return Result.Success(dataSource.getSongInfo(artist = artist, track = track))
    }
}