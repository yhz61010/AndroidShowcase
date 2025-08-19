package com.leovp.discovery.data.datasource.api.response

import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import com.leovp.discovery.data.datasource.api.model.SongDetailApiModel
import com.leovp.feature.base.http.model.ApiResponseResult
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Author: Michael Leo
 * Date: 2024/9/18 10:52
 */

@Keep
@Immutable
@OptIn(InternalSerializationApi::class)
@Serializable
data class SongDetailResponse(
    @SerialName("songs") val songs: List<SongDetailApiModel>,
) : ApiResponseResult()

// {
//   "songs": [
//     {
//       "name": "腾空",
//       "id": 2627039740,
//       "pst": 0,
//       "t": 0,
//       "ar": [
//         {
//           "id": 5538,
//           "name": "汪苏泷",
//           "tns": [],
//           "alias": []
//         }
//       ],
//       "alia": [],
//       "pop": 5,
//       "st": 0,
//       "rt": "",
//       "fee": 8,
//       "v": 4,
//       "crbt": null,
//       "cf": "",
//       "al": {
//         "id": 247897771,
//         "name": "腾空",
//         "picUrl": "https://p1.music.126.net/axl3Tb6d1RgrzST4aUC1UA==/109951169958989852.jpg",
//         "tns": [],
//         "pic_str": "109951169958989852",
//         "pic": 109951169958989860
//       },
//       "dt": 318284,
//       "h": {
//         "br": 320000,
//         "fid": 0,
//         "size": 12733485,
//         "vd": -71574,
//         "sr": 48000
//       },
//       "m": {
//         "br": 192000,
//         "fid": 0,
//         "size": 7640109,
//         "vd": -69095,
//         "sr": 48000
//       },
//       "l": {
//         "br": 128000,
//         "fid": 0,
//         "size": 5093421,
//         "vd": -67686,
//         "sr": 48000
//       },
//       "sq": {
//         "br": 1109297,
//         "fid": 0,
//         "size": 44134035,
//         "vd": -71637,
//         "sr": 48000
//       },
//       "hr": {
//         "br": 1876795,
//         "fid": 0,
//         "size": 74669395,
//         "vd": -71525,
//         "sr": 48000
//       },
//       "a": null,
//       "cd": "01",
//       "no": 1,
//       "rtUrl": null,
//       "ftype": 0,
//       "rtUrls": [],
//       "djId": 0,
//       "copyright": 0,
//       "s_id": 0,
//       "mark": 17716748288,
//       "originCoverType": 1,
//       "originSongSimpleData": null,
//       "tagPicList": null,
//       "resourceState": true,
//       "version": 4,
//       "songJumpInfo": null,
//       "entertainmentTags": null,
//       "awardTags": null,
//       "single": 0,
//       "noCopyrightRcmd": null,
//       "mv": 0,
//       "rtype": 0,
//       "rurl": null,
//       "mst": 9,
//       "cp": 729013,
//       "publishTime": 0
//     }
//   ],
//   "privileges": [
//     {
//       "id": 2627039740,
//       "fee": 0,
//       "payed": 0,
//       "st": -100,
//       "pl": 0,
//       "dl": 0,
//       "sp": 7,
//       "cp": 1,
//       "subp": 1,
//       "cs": false,
//       "maxbr": 999000,
//       "fl": 0,
//       "toast": false,
//       "flag": 524292,
//       "preSell": false,
//       "playMaxbr": 999000,
//       "downloadMaxbr": 999000,
//       "maxBrLevel": "hires",
//       "playMaxBrLevel": "hires",
//       "downloadMaxBrLevel": "hires",
//       "plLevel": "none",
//       "dlLevel": "none",
//       "flLevel": "none",
//       "rscl": 0,
//       "freeTrialPrivilege": {
//         "resConsumable": false,
//         "userConsumable": false,
//         "listenType": null,
//         "cannotListenReason": 1,
//         "playReason": null,
//         "freeLimitTagType": null
//       },
//       "rightSource": 0,
//       "chargeInfoList": [
//         {
//           "rate": 128000,
//           "chargeUrl": null,
//           "chargeMessage": null,
//           "chargeType": 0
//         },
//         {
//           "rate": 192000,
//           "chargeUrl": null,
//           "chargeMessage": null,
//           "chargeType": 0
//         },
//         {
//           "rate": 320000,
//           "chargeUrl": null,
//           "chargeMessage": null,
//           "chargeType": 0
//         },
//         {
//           "rate": 999000,
//           "chargeUrl": null,
//           "chargeMessage": null,
//           "chargeType": 1
//         },
//         {
//           "rate": 1999000,
//           "chargeUrl": null,
//           "chargeMessage": null,
//           "chargeType": 1
//         }
//       ],
//       "code": 0,
//       "message": null
//     }
//   ],
//   "code": 200
// }
