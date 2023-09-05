package com.leovp.androidshowcase.ui.tabs.discovery.data.datasource.api.model

import com.leovp.androidshowcase.ui.tabs.discovery.domain.enum.ImageSize
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

fun ImageSizeApiModel.toEverydayItem() = ImageSize.valueOf(this.name)
