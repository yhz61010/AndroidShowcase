package com.leovp.feature_discovery.data.datasource.api.model

import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import com.leovp.feature_discovery.domain.model.PrivateContentModel
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable

@Keep
@Immutable
@OptIn(InternalSerializationApi::class)
@Serializable
data class PrivateContentApiModel(
    val id: Long,
    val name: String,
    val url: String = "",
    val picUrl: String,
    val sPicUrl: String,
    val type: Int,
    val copywriter: String,
    val alg: String
)

fun PrivateContentApiModel.toDomainModel(typeName: String): PrivateContentModel {
    return PrivateContentModel(
        id = this.id,
        name = this.name,
        url = this.url,
        picUrl = this.picUrl,
    ).also {
        it.type = this.type
        it.typeName = typeName
    }
}
