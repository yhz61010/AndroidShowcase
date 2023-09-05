package com.leovp.androidshowcase.ui.tabs.discovery.data.datasource.api.model

import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

/**
 * Author: Michael Leo
 * Date: 2023/9/6 11:00
 */

@Keep
@Immutable
@Serializable
data class Attr(
    val country: String?, // nullable from api
    val page: String,
    val perPage: String,
    val total: String,
    val totalPages: String,
)