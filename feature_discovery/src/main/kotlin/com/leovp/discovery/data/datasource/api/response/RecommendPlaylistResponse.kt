package com.leovp.discovery.data.datasource.api.response

import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import com.leovp.discovery.data.datasource.api.model.PlaylistApiModel
import com.leovp.feature.base.http.model.ApiResponseResult
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Author: Michael Leo
 * Date: 2023/9/6 14:52
 */

@Keep
@Immutable
@OptIn(InternalSerializationApi::class)
@Serializable
data class RecommendPlaylistResponse(
    @SerialName("result") val result: List<PlaylistApiModel>,
) : ApiResponseResult()

// {
//   "hasTaste": false,
//   "code": 200,
//   "category": 0,
//   "result": [
//     {
//       "id": 2075587022,
//       "type": 0,
//       "name": "助眠辑 | 自然音，伴灵动乐符萦绕耳畔",
//       "copywriter": "",
//       "picUrl": "https://p1.music.126.net/sixunTcvD_IXeVqxZnpHkA==/109951163452086313.jpg",
//       "canDislike": true,
//       "trackNumberUpdateTime": 1533916733093,
//       "playCount": 29906432,
//       "trackCount": 104,
//       "highQuality": true,
//       "alg": "alg_high_quality"
//     }
//   ]
// }
