package com.leovp.feature_discovery.data.datasource.api.model

import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import com.leovp.feature_discovery.domain.model.Image
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Author: Michael Leo
 * Date: 2023/9/6 11:00
 */

@Keep
@Immutable
@Serializable
@OptIn(InternalSerializationApi::class)
data class ImageApiModel(
    @SerialName("#text") val url: String,
    val size: ImageSizeApiModel
)

@OptIn(InternalSerializationApi::class)
fun ImageApiModel.toDomainModel() = Image(
    url = this.url,
    size = this.size.toDomainModel(),
)