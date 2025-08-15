package com.leovp.feature_discovery.data.datasource.api.response

import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import com.leovp.feature_discovery.data.datasource.api.model.PrivateContentApiModel
import com.leovp.feature.base.http.model.ApiResponseResult
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Author: Michael Leo
 * Date: 2023/9/15 14:43
 */

@Keep
@Immutable
@OptIn(InternalSerializationApi::class)
@Serializable
data class PrivateContentResponse(
    val name: String,
    @SerialName("result") val result: List<PrivateContentApiModel>
) : ApiResponseResult()

// {
//   "code": 200,
//   "name": "独家放送",
//   "result": [
//     {
//        "id": 14496631,
//        "url": "",
//        "picUrl": "https://p2.music.126.net/IVEDKgbd--myWBukXgreNg==/109951166983018052.jpg",
//        "sPicUrl": "https://p2.music.126.net/lx35fbctK2npbgPUwt2FXw==/109951166983009311.jpg",
//        "type": 5,
//        "copywriter": "《超级面对面》第252期 Alan Walker：和Walkers一起遨游宇宙",
//        "name": "《超级面对面》第252期 Alan Walker：和Walkers一起遨游宇宙",
//        "alg": "featured"
//     }
//   ]
// }