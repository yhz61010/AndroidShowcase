package com.leovp.feature_discovery.data.datasource.api.response


import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import com.leovp.feature_discovery.data.datasource.api.model.SearchAlbumResultsApiModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Immutable
@Serializable
data class SearchAlbumResponse(
    @SerialName("results")
    val results: SearchAlbumResultsApiModel
)