package com.leovp.androidshowcase.ui.tabs.discovery.data.datasource.api.model

import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import com.leovp.androidshowcase.ui.tabs.discovery.domain.model.Image
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Author: Michael Leo
 * Date: 2023/9/6 11:00
 */

@Keep
@Immutable
@Serializable
data class ImageApiModel(
    @SerialName("#text") val text: String,
    val size: ImageSizeApiModel
)

fun ImageApiModel.toEverydayItem() = Image(
    url = this.text,
    size = this.size.toEverydayItem(),
)