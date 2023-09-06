package com.leovp.androidshowcase.ui.tabs.discovery.data.datasource.api.model


import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Immutable
@Serializable
data class SearchAlbumResultsApiModel(
    @SerialName("albummatches")
    val albumMatches: AlbumListApiModel,
    @SerialName("@attr")
    val attr: AttrApiModel,
    @SerialName("opensearch:itemsPerPage")
    val openSearchItemsPerPage: String,
    @SerialName("opensearch:Query")
    val openSearchQuery: OpenSearchQueryApiModel,
    @SerialName("opensearch:startIndex")
    val openSearchStartIndex: String,
    @SerialName("opensearch:totalResults")
    val openSearchTotalResults: String
)