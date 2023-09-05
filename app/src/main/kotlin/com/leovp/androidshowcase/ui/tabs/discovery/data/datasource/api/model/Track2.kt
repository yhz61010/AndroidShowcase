package com.leovp.androidshowcase.ui.tabs.discovery.data.datasource.api.model

import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import com.leovp.androidshowcase.ui.tabs.discovery.domain.enum.MarkType
import com.leovp.androidshowcase.ui.tabs.discovery.domain.model.MusicItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
  * Author: Michael Leo
  * Date: 2023/9/6 15:59
  */

@Keep
@Immutable
@Serializable
data class Track2(
    val name: String,
    val playcount: String,
    val listeners: String,
    val mbid: String?, // nullable from api
    val url: String,
    val streamable: String,
    val artist: Artist,
    val image: List<ImageApiModel>?,
    @SerialName("@attr") val attr: AttrX,
)

fun Track2.toMusicItem(): MusicItem {
    val images = this.image
        ?.filterNot { it.size == ImageSizeApiModel.UNKNOWN || it.text.isBlank() }
        ?.map { it.toEverydayItem() }

    return MusicItem(
        id = attr.rank.toInt(),
        thumbnail = images ?: emptyList(),
        title = name,
        subTitle = artist.name,
        markText = "$listeners 人正在收听",
        showTrailIcon = false,
        type = MarkType.Hot,
    )
}