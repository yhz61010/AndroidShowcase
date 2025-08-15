package com.leovp.feature_discovery.domain.usecase

import com.leovp.feature_discovery.data.DiscoveryRepositoryImplement
import com.leovp.feature_discovery.domain.model.PlaylistModel
import com.leovp.feature_discovery.domain.model.PrivateContentModel
import com.leovp.feature_discovery.domain.model.TopSongModel
import com.leovp.feature_discovery.domain.repository.DiscoveryRepository
import com.leovp.module.common.Result
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Author: Michael Leo
 * Date: 2023/9/4 17:02
 */
@Singleton
class GetDiscoveryListUseCase @Inject constructor(
    @param:DiscoveryRepositoryImplement private val repository: DiscoveryRepository
) {

    suspend fun getPrivateContent(): Result<List<PrivateContentModel>> = repository.getPrivateContent()

    suspend fun getRecommendPlaylist(): Result<List<PlaylistModel>> = repository.getRecommendPlaylist()

    /**
     * type:
     *      全部: 0
     *      华语: 7
     *      欧美: 96
     *      日本: 8
     *      韩国: 16
     */
    suspend fun getTopSongs(type: Int = 0): Result<List<TopSongModel>> = repository.getTopSongs(type)
}
