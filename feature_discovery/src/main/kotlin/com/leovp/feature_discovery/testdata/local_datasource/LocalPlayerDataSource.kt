@file:Suppress("ktlint:max-line-length") // String constants read better

package com.leovp.feature_discovery.testdata.local_datasource

import com.leovp.feature_discovery.data.datasource.PlayerDataSource
import com.leovp.feature_discovery.domain.model.AlbumModel
import com.leovp.feature_discovery.domain.model.ArtistModel
import com.leovp.feature_discovery.domain.model.SongModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalPlayerDataSource @Inject constructor() : PlayerDataSource {
    override fun getSongInfo(vararg ids: Long): List<SongModel> {
        return listOf(
            SongModel(
                id = 2627039740,
                name = "Tian Mi Mi",
                artists = listOf(
                    ArtistModel(
                        id = 12345678,
                        name = "Teresa Teng",
                    )
                ),
                duration = 209000,
                album = AlbumModel(
                    id = 12345,
                    name = "Tian Mi Mi",
                    picUrl = "https://lastfm.freetls.fastly.net/i/u/300x300/fa98cc920f8fc322c5efb2a8986164fe.png",
                ),
                quality = SongModel.Quality.JYMASTER,
                markText = "VIP",
            )
        )
    }

    override fun getMusicComment(
        id: Long,
        limit: Int,
        offset: Int
    ): SongModel.CommentsModel {
        return SongModel.CommentsModel(
            totalComments = 1567,
            hotComments = listOf(SongModel.Comment(1L, "热评：放给舍友听的时候，她只回一句这..."))
        )
    }

    override fun getSongRedCount(id: Long): SongModel.RedCountModel {
        return SongModel.RedCountModel(
            count = 6483325,
            countDesc = "100w+"
        )
    }
}