@file:Suppress("ktlint:max-line-length") // String constants read better

package com.leovp.feature_discovery.testdata.local_datasource

import com.leovp.feature_discovery.data.datasource.PlayerDataSource
import com.leovp.feature_discovery.domain.enum.ImageSize
import com.leovp.feature_discovery.domain.model.Image
import com.leovp.feature_discovery.domain.model.SongItem
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.String

@Singleton
class LocalPlayerDataSource @Inject constructor() : PlayerDataSource {
    override fun getSongInfo(artist: String, album: String): SongItem {
        return SongItem(
            mbid = "b7ec58ec-9865-4f8f-90cc-826f408d63f4",
            name = "甜蜜蜜",
            duration = 209000,
            artist = "鄧麗君",
            albumImages = listOf(
                Image(
                    url = "https://lastfm.freetls.fastly.net/i/u/300x300/fa98cc920f8fc322c5efb2a8986164fe.png",
                    size = ImageSize.EXTRA_LARGE,
                )
            )
        )
    }
}