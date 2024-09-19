package com.leovp.feature_discovery.data.datasource.api.response

import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import com.leovp.feature_discovery.data.datasource.api.model.HomePageBlockApiModel
import com.leovp.module.common.http.model.ApiResponseResult
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
  * Author: Michael Leo
  * Date: 2024/9/12 09:40
  */

@Keep
@Immutable
@OptIn(InternalSerializationApi::class)
@Serializable
data class HomePageBlockResponse(
    @SerialName("data") val result: HomePageBlockApiModel
) : ApiResponseResult()

// {
//   "code": 200,
//   "message": "",
//   "data": {
//     "cursor": null,
//     "blocks": [
//       {
//         "blockCode": "HOMEPAGE_BANNER",
//         "showType": "BANNER",
//         "dislikeShowType": 0,
//         "extInfo": {
//           "banners": [
//             {
//               "pic": "http://p1.music.126.net/e8wtQuscq74yweCGuhM8TA==/109951169664474779.jpg",
//               "targetId": 0,
//               "mainTitle": null,
//               "adid": null,
//               "targetType": 3000,
//               "titleColor": "blue",
//               "typeTitle": "独家策划",
//               "url": "https://y.music.163.com/g/yida/470cb610d27249f09f9f6c3d595b9457",
//               "adurlV2": null,
//               "exclusive": false,
//               "monitorImpress": null,
//               "monitorClick": null,
//               "monitorType": null,
//               "monitorImpressList": [],
//               "monitorClickList": [],
//               "monitorBlackList": null,
//               "extMonitor": null,
//               "extMonitorInfo": null,
//               "adSource": null,
//               "adLocation": null,
//               "encodeId": "0",
//               "program": null,
//               "event": null,
//               "video": null,
//               "dynamicVideoData": null,
//               "song": null,
//               "bannerId": "1717750651205666",
//               "alg": "banner-feature-1717750651205666",
//               "scm": "1.music-homepage-home.homepage_banner_force.banner.9432966.-2051253902.null",
//               "requestId": "",
//               "showAdTag": true,
//               "pid": null,
//               "showContext": null,
//               "adDispatchJson": null,
//               "s_ctrp": "syspf_resourceType_3000-syspf_resourceId_0",
//               "logContext": null,
//               "bannerBizType": "force_banner"
//             }
//           ]
//         },
//         "canClose": false,
//         "blockStyle": 0,
//         "canFeedback": false,
//         "blockDemote": false,
//         "sort": 0
//       }
//     ],
//     "hasMore": false,
//     "blockUUIDs": null,
//     "pageConfig": {
//       "refreshToast": "",
//       "nodataToast": "到底啦~",
//       "refreshInterval": 600000,
//       "title": null,
//       "fullscreen": false,
//       "abtest": [
//         "homepage-v7.3",
//         "homepage-v7.4"
//       ],
//       "songLabelMarkPriority": [
//         "vip",
//         "trial",
//         "exclusive",
//         "sq",
//         "dolby",
//         "pre_sale"
//       ],
//       "songLabelMarkLimit": 1,
//       "homepageMode": "PLAYLIST_MODE",
//       "showModeEntry": true,
//       "orderInfo": "PLAYLIST_MODE_92c41ae6-1f6a-4dca-9b19-75bbf5dfd7e7"
//     },
//     "internalTest": null,
//     "titles": [],
//     "blockCodeOrderList": null,
//     "demote": false
//   }
// }