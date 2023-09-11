@file:Suppress("unused")

package com.leovp.feature_discovery.data.datasource.api.model

import com.leovp.feature_discovery.domain.enum.ImageSize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Author: Michael Leo
 * Date: 2023/9/6 11:18
 */

@Serializable
enum class ImageSizeApiModel {

    @SerialName("medium")
    MEDIUM,

    @SerialName("small")
    SMALL,

    @SerialName("large")
    LARGE,

    @SerialName("extralarge")
    EXTRA_LARGE,

    @SerialName("mega")
    MEGA,

    @SerialName("")
    UNKNOWN,
}

fun ImageSizeApiModel.toDomainModel() = ImageSize.valueOf(this.name)