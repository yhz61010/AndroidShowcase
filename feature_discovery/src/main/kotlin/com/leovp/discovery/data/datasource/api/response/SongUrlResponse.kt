package com.leovp.discovery.data.datasource.api.response

import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import com.leovp.discovery.data.datasource.api.model.SongUrlApiModel
import com.leovp.feature.base.http.model.ApiResponseResult
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Author: Michael Leo
 * Date: 2024/9/23 10:40
 */

@Keep
@Immutable
@OptIn(InternalSerializationApi::class)
@Serializable
data class SongUrlResponse(
    @SerialName("data") val data: List<SongUrlApiModel>,
) : ApiResponseResult()

// {
//   "data": [
//     {
//       "id": 1413585838,
//       "url": "http://m701.music.126.net/20240923155926/e4b7af3b8c8191b69227e6
//       2a0db6cef6/jdymusic/obj/wo3DlMOGwrbDjj7DisKw/14096426135/4974/2ce7/a751
//       /bcd40908be84116ddb0bdc1c4879c44c.mp3",
//       "br": 320002,
//       "size": 9602656,
//       "md5": "bcd40908be84116ddb0bdc1c4879c44c",
//       "code": 200,
//       "expi": 1200,
//       "type": "mp3",
//       "gain": -7.6278,
//       "peak": 1,
//       "fee": 8,
//       "uf": null,
//       "payed": 0,
//       "flag": 2064388,
//       "canExtend": false,
//       "freeTrialInfo": null,
//       "level": "exhigh",
//       "encodeType": "mp3",
//       "channelLayout": null,
//       "freeTrialPrivilege": {
//         "resConsumable": false,
//         "userConsumable": false,
//         "listenType": null,
//         "cannotListenReason": null,
//         "playReason": null,
//         "freeLimitTagType": null
//       },
//       "freeTimeTrialPrivilege": {
//         "resConsumable": false,
//         "userConsumable": false,
//         "type": 0,
//         "remainTime": 0
//       },
//       "urlSource": 0,
//       "rightSource": 0,
//       "podcastCtrp": null,
//       "effectTypes": null,
//       "time": 240000,
//       "message": null,
//       "levelConfuse": null,
//       "musicId": "7238116483"
//     }
//   ],
//   "code": 200
// }

// ----------------------------------------

// {
//   "data": [
//     {
//       "id": 2627103194,
//       "url": "http://m701.music.126.net/20240923110059/f769e180f6a62f6aea52ed
//       2c62404552/jdymusic/obj/wo3DlMOGwrbDjj7DisKw/45913450048/2320/06c6/a30c
//       /e2c13e2158a9d9ad8e8f1f5c8c82c3a2.mp3",
//       "br": 320000,
//       "size": 11309805,
//       "md5": "e2c13e2158a9d9ad8e8f1f5c8c82c3a2",
//       "code": 200,
//       "expi": 1200,
//       "type": "mp3",
//       "gain": -9.8743,
//       "peak": 1,
//       "fee": 8,
//       "uf": null,
//       "payed": 0,
//       "flag": 1605892,
//       "canExtend": false,
//       "freeTrialInfo": null,
//       "level": "exhigh",
//       "encodeType": "mp3",
//       "channelLayout": null,
//       "freeTrialPrivilege": {
//         "resConsumable": false,
//         "userConsumable": false,
//         "listenType": null,
//         "cannotListenReason": null,
//         "playReason": null,
//         "freeLimitTagType": null
//       },
//       "freeTimeTrialPrivilege": {
//         "resConsumable": false,
//         "userConsumable": false,
//         "type": 0,
//         "remainTime": 0
//       },
//       "urlSource": 0,
//       "rightSource": 0,
//       "podcastCtrp": null,
//       "effectTypes": null,
//       "time": 282696,
//       "message": null,
//       "levelConfuse": null,
//       "musicId": "11460829031"
//     }
//   ],
//   "code": 200
// }
