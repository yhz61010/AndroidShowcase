@file:Suppress("MaxLineLength")

package com.leovp.discovery.testdata.local.datasource

import com.leovp.discovery.data.datasource.PlayerDataSource
import com.leovp.discovery.domain.model.AlbumModel
import com.leovp.discovery.domain.model.ArtistModel
import com.leovp.discovery.domain.model.SongModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalPlayerDataSource
    @Inject
    constructor() : PlayerDataSource {
        override fun getSongInfo(vararg ids: Long): List<SongModel> =
            listOf(
                SongModel(
                    id = 2627039740,
                    name = "Tian Mi Mi",
                    artists =
                        listOf(
                            ArtistModel(
                                id = 12345678,
                                name = "Teresa Teng",
                            ),
                        ),
                    duration = 209000,
                    album =
                        AlbumModel(
                            id = 12345,
                            name = "Tian Mi Mi",
                            picUrl = "https://lastfm.freetls.fastly.net/i/u/300x300/fa98cc920f8fc322c5efb2a8986164fe.png",
                        ),
                    quality = SongModel.Quality.Jymaster,
                    fee = 8,
                    markText = "VIP",
                ),
            )

        override fun getMusicComment(
            id: Long,
            limit: Int,
            offset: Int,
        ): SongModel.CommentsModel =
            SongModel.CommentsModel(
                totalComments = 1567,
                hotComments =
                    listOf(
                        SongModel.Comment(1L, "热评：放给舍友听的时候，她只回一句这..."),
                    ),
            )

        override fun getSongRedCount(id: Long): SongModel.RedCountModel =
            SongModel.RedCountModel(
                count = 6483325,
                countDesc = "100w+",
            )

        override fun getSongUrlV1(
            id: Long,
            level: SongModel.Quality,
        ): List<SongModel.UrlModel> =
            listOf(
                SongModel.UrlModel(
                    id = 2627103194,
                    br = 128000,
                    url =
                        "http://m701.music.126.net/20240923110059/f769e180" +
                            "f6a62f6aea52ed2c62404552/jdymusic/obj/wo3DlMOGwrbDj" +
                            "j7DisKw/45913450048/2320/06c6/a30c/e2c13e2158a9d9ad" +
                            "8e8f1f5c8c82c3a2.mp3",
                    size = 11309805,
                    md5 = "e2c13e2158a9d9ad8e8f1f5c8c82c3a2",
                    type = "mp3",
                    gain = -9.8743f,
                    peak = 1,
                    level =
                        SongModel.Quality.Jymaster.name
                            .lowercase(),
                    encodeType = "mp3",
                    time = 282696,
                    code = 200,
                    fee = 8,
                    payed = 0,
                ),
            )

        override fun checkMusic(
            id: Long,
            br: Int,
        ): SongModel.MusicAvailableModel =
            SongModel.MusicAvailableModel(
                success = true,
                // message = "亲爱的,暂无版权"
            )
    }
