package com.leovp.feature_discovery.data.repository

import com.drake.net.Get
import com.leovp.feature_discovery.data.datasource.PlayerDataSource
import com.leovp.feature_discovery.data.datasource.api.model.toSongDomainModel
import com.leovp.feature_discovery.data.datasource.api.response.GetTrackInfoResponse
import com.leovp.feature_discovery.domain.model.SongItem
import com.leovp.feature_discovery.domain.repository.PlayerRepository
import com.leovp.module.common.GlobalConst
import com.leovp.module.common.Result
import com.leovp.module.common.result
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

/**
  * Author: Michael Leo
  * Date: 2024/8/26 14:02
  */

class PlayerRepositoryImpl @Inject constructor(
    private val dataSource: PlayerDataSource
) : PlayerRepository {
    override suspend fun getSongInfo(artist: String, track: String): Result<SongItem> = result(Dispatchers.IO) {
        Get<GetTrackInfoResponse>(GlobalConst.HTTP_GET_TRACK_GET_INFO) {
            param("artist", artist)
            param("track", track)
        }.await().track.toSongDomainModel()
    }
}
