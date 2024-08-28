package com.leovp.feature_discovery.data.datasource.api.model


import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import com.leovp.feature_discovery.domain.model.CarouselItem
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Immutable
@OptIn(InternalSerializationApi::class)
@Serializable
data class TopAlbumApiModel(
    @SerialName("artist") val artist: ArtistApiModel,
    @SerialName("name") val name: String,
    @SerialName("url") val url: String,
    @SerialName("image") val images: List<ImageApiModel>?,
)

fun TopAlbumApiModel.toDomainModel(index: Int): CarouselItem {
    val images =
        this.images?.filterNot { it.size == ImageSizeApiModel.UNKNOWN || it.url.isBlank() }
            ?.map { it.toDomainModel() }

    return CarouselItem(
        id = index,
        thumbnail = images ?: emptyList(),
    )
}
