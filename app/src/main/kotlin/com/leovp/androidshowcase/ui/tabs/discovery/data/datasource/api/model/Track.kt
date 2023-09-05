package com.leovp.androidshowcase.ui.tabs.discovery.data.datasource.api.model

import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import com.leovp.androidshowcase.ui.tabs.discovery.domain.model.EverydayItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Author: Michael Leo
 * Date: 2023/9/6 11:00
 */

@Keep
@Immutable
@Serializable
data class Track(
    @SerialName("@attr") val attr: AttrX,
    val artist: Artist,
    val duration: String,
    val image: List<ImageApiModel>?,
    val listeners: String,
    val mbid: String,
    val name: String,
    val streamable: Streamable,
    val url: String,
)

fun Track.toEverydayItem(): EverydayItem {
    val images = this.image
        ?.filterNot { it.size == ImageSizeApiModel.UNKNOWN || it.text.isBlank() }
        ?.map { it.toEverydayItem() }

    return EverydayItem(
        id = attr.rank.toInt(),
        thumbnail = images ?: emptyList(),
        type = artist.name,
        title = name,
        icon = null,
    )
}
