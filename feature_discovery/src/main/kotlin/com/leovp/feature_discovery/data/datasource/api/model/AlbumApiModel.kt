package com.leovp.feature_discovery.data.datasource.api.model


import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import com.leovp.feature_discovery.domain.model.EverydayItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Immutable
@Serializable
data class AlbumApiModel(
    @SerialName("artist")
    val artist: String,
    @SerialName("image")
    val images: List<ImageApiModel>?,
    @SerialName("mbid")
    val mbId: String,
    @SerialName("name")
    val name: String,
    @SerialName("streamable")
    val streamable: String,
    @SerialName("url")
    val url: String
)

fun AlbumApiModel.toDomainModel(index: Int): EverydayItem {
    val images = this.images
        ?.filterNot { it.size == ImageSizeApiModel.UNKNOWN || it.url.isBlank() }
        ?.map { it.toDomainModel() }

    return EverydayItem(
        id = index,
        thumbnail = images ?: emptyList(),
        type = artist,
        title = name,
        icon = null,
    )
}
