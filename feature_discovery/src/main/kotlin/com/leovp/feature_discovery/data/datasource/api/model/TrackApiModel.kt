package com.leovp.feature_discovery.data.datasource.api.model

import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import com.leovp.feature_discovery.domain.enum.MarkType
import com.leovp.feature_discovery.domain.model.MusicItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Author: Michael Leo
 * Date: 2023/9/6 11:00
 */

@Keep
@Immutable
@Serializable
data class TrackApiModel(

    @SerialName("@attr") val attr: AttrApiModel,
    val artist: ArtistApiModel,
    val duration: Int?,
    @SerialName("image")
    val images: List<ImageApiModel>?,
    val listeners: Int,
    @SerialName("mbid") val mbId: String?, // nullable from api
    val name: String,
    val url: String,

    val playcount: Int?,
)

fun TrackApiModel.toDomainModel(index: Int): MusicItem {
    val images = this.images?.filterNot { it.size == ImageSizeApiModel.UNKNOWN || it.url.isBlank() }
        ?.map { it.toDomainModel() }

    return MusicItem(
        id = index,
        thumbnail = images ?: emptyList(),
        title = name,
        subTitle = artist.name,
        markText = "$listeners",
        showTrailIcon = false,
        type = MarkType.Hot,
    )
}