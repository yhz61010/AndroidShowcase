package com.leovp.discovery.domain.usecase

import com.leovp.discovery.domain.model.PlaylistModel
import com.leovp.discovery.domain.model.PrivateContentModel
import com.leovp.discovery.domain.model.TopSongModel
import com.leovp.discovery.domain.repository.DiscoveryRepository
import com.leovp.feature.base.http.model.ApiResponseModel.Companion.processApiResponseResult
import com.leovp.network.http.ResultBiz
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Author: Michael Leo
 * Date: 2023/9/4 17:02
 */
@Singleton
class GetDiscoveryListUseCase
    @Inject
    constructor(
        // @DiscoveryRepositoryImplement
        private val repository: DiscoveryRepository,
    ) {
        suspend fun getPrivateContent(): ResultBiz<List<PrivateContentModel>> =
            processApiResponseResult(repository.getPrivateContent())

        suspend fun getRecommendPlaylist(): ResultBiz<List<PlaylistModel>> =
            processApiResponseResult(repository.getRecommendPlaylist())

        /**
         * type:
         *      全部: 0
         *      华语: 7
         *      欧美: 96
         *      日本: 8
         *      韩国: 16
         */
        suspend fun getTopSongs(type: Int = 0): ResultBiz<List<TopSongModel>> =
            processApiResponseResult(repository.getTopSongs(type))
    }
