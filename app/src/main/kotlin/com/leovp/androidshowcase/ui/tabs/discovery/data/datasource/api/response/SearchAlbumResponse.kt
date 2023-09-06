package com.leovp.androidshowcase.ui.tabs.discovery.data.datasource.api.response


import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import com.leovp.androidshowcase.ui.tabs.discovery.data.datasource.api.model.SearchAlbumResultsApiModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Immutable
@Serializable
data class SearchAlbumResponse(
    @SerialName("results")
    val results: SearchAlbumResultsApiModel
)