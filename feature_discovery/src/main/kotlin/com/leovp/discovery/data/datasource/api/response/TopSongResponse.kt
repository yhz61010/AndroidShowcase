package com.leovp.discovery.data.datasource.api.response

import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import com.leovp.discovery.data.datasource.api.model.TopSongApiModel
import com.leovp.feature.base.http.model.ApiResponseResult
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Immutable
@OptIn(InternalSerializationApi::class)
@Serializable
data class TopSongResponse(
    @SerialName("data") val result: List<TopSongApiModel>,
) : ApiResponseResult()

// {
//   "data": [
//     {
//       "starred": false,
//       "popularity": 5,
//       "starredNum": 0,
//       "playedNum": 0,
//       "dayPlays": 0,
//       "hearTime": 0,
//       "albumData": null,
//       "mp3Url": "http://m2.music.126.net/hmZoNQaqzZALvVp0rE7faA==/0.mp3",
//       "rtUrls": null,
//       "privilege": {
//         "id": 2626041152,
//         "fee": 8,
//         "payed": 0,
//         "st": 0,
//         "pl": 128000,
//         "dl": 0,
//         "sp": 7,
//         "cp": 1,
//         "subp": 1,
//         "cs": false,
//         "maxbr": 999000,
//         "fl": 128000,
//         "toast": false,
//         "flag": 524292,
//         "preSell": false,
//         "playMaxbr": 999000,
//         "downloadMaxbr": 999000,
//         "maxBrLevel": "hires",
//         "playMaxBrLevel": "hires",
//         "downloadMaxBrLevel": "hires",
//         "plLevel": "standard",
//         "dlLevel": "none",
//         "flLevel": "standard",
//         "rscl": null,
//         "freeTrialPrivilege": {
//           "resConsumable": false,
//           "userConsumable": false,
//           "listenType": null,
//           "cannotListenReason": null,
//           "playReason": null
//         },
//         "rightSource": 0,
//         "chargeInfoList": [
//           {
//             "rate": 128000,
//             "chargeUrl": null,
//             "chargeMessage": null,
//             "chargeType": 0
//           },
//           {
//             "rate": 192000,
//             "chargeUrl": null,
//             "chargeMessage": null,
//             "chargeType": 1
//           },
//           {
//             "rate": 320000,
//             "chargeUrl": null,
//             "chargeMessage": null,
//             "chargeType": 1
//           },
//           {
//             "rate": 999000,
//             "chargeUrl": null,
//             "chargeMessage": null,
//             "chargeType": 1
//           },
//           {
//             "rate": 1999000,
//             "chargeUrl": null,
//             "chargeMessage": null,
//             "chargeType": 1
//           }
//         ]
//       },
//       "videoInfo": null,
//       "relatedVideo": null,
//       "st": 0,
//       "exclusive": false,
//       "album": {
//         "songs": null,
//         "paid": false,
//         "onSale": false,
//         "picId": 109951169951523280,
//         "picUrl": "http://p1.music.126.net/ogh2nZri7rQBrJd3Lil78A==/109951169951523284.jpg",
//         "publishTime": 1726070400000,
//         "artists": [
//           {
//             "img1v1Id": 18686200114669624,
//             "topicPerson": 0,
//             "picId": 0,
//             "picUrl": "http://p1.music.126.net/6y-UleORITEDbvrOLV0Q8A==/5639395138885805.jpg",
//             "followed": false,
//             "briefDesc": "",
//             "musicSize": 0,
//             "albumSize": 0,
//             "img1v1Url": "http://p1.music.126.net/VnZiScyynLG7atLIZ2YPkw==/18686200114669622.jpg",
//             "trans": "",
//             "alias": [],
//             "name": "吴克群",
//             "id": 5350,
//             "img1v1Id_str": "18686200114669622"
//           }
//         ],
//         "commentThreadId": "R_AL_3_247570693",
//         "briefDesc": "",
//         "artist": {
//           "img1v1Id": 18686200114669624,
//           "topicPerson": 0,
//           "picId": 0,
//           "picUrl": "http://p1.music.126.net/6y-UleORITEDbvrOLV0Q8A==/5639395138885805.jpg",
//           "followed": false,
//           "briefDesc": "",
//           "musicSize": 0,
//           "albumSize": 0,
//           "img1v1Url": "http://p1.music.126.net/VnZiScyynLG7atLIZ2YPkw==/18686200114669622.jpg",
//           "trans": "",
//           "alias": [],
//           "name": "",
//           "id": 0,
//           "img1v1Id_str": "18686200114669622"
//         },
//         "copyrightId": 2706776,
//         "company": "六度空间音乐有限公司",
//         "subType": "录音室版",
//         "blurPicUrl": "http://p1.music.126.net/ogh2nZri7rQBrJd3Lil78A==/109951169951523284.jpg",
//         "companyId": 0,
//         "pic": 109951169951523280,
//         "status": 1,
//         "description": "",
//         "alias": [],
//         "tags": "",
//         "name": "谢谢你爱我 (2024)",
//         "id": 247570693,
//         "type": "Single",
//         "size": 1,
//         "picId_str": "109951169951523284"
//       },
//       "fee": 8,
//       "artists": [
//         {
//           "img1v1Id": 18686200114669624,
//           "topicPerson": 0,
//           "picId": 0,
//           "picUrl": "http://p1.music.126.net/6y-UleORITEDbvrOLV0Q8A==/5639395138885805.jpg",
//           "followed": false,
//           "briefDesc": "",
//           "musicSize": 0,
//           "albumSize": 0,
//           "img1v1Url": "http://p1.music.126.net/VnZiScyynLG7atLIZ2YPkw==/18686200114669622.jpg",
//           "trans": "",
//           "alias": [],
//           "name": "吴克群",
//           "id": 5350,
//           "img1v1Id_str": "18686200114669622"
//         }
//       ],
//       "hMusic": {
//         "volumeDelta": -61286,
//         "playTime": 265013,
//         "bitrate": 320001,
//         "dfsId": 0,
//         "sr": 48000,
//         "name": null,
//         "id": 11444623477,
//         "size": 10603245,
//         "extension": "mp3"
//       },
//       "mMusic": {
//         "volumeDelta": -58752,
//         "playTime": 265013,
//         "bitrate": 192001,
//         "dfsId": 0,
//         "sr": 48000,
//         "name": null,
//         "id": 11444623474,
//         "size": 6361965,
//         "extension": "mp3"
//       },
//       "lMusic": {
//         "volumeDelta": -57245,
//         "playTime": 265013,
//         "bitrate": 128001,
//         "dfsId": 0,
//         "sr": 48000,
//         "name": null,
//         "id": 11444623473,
//         "size": 4241325,
//         "extension": "mp3"
//       },
//       "commentThreadId": "R_SO_4_2626041152",
//       "mvid": 22616300,
//       "score": 5,
//       "crbt": null,
//       "bMusic": {
//         "volumeDelta": -57245,
//         "playTime": 265013,
//         "bitrate": 128001,
//         "dfsId": 0,
//         "sr": 48000,
//         "name": null,
//         "id": 11444623473,
//         "size": 4241325,
//         "extension": "mp3"
//       },
//       "rtUrl": null,
//       "ftype": 0,
//       "audition": null,
//       "copyFrom": "",
//       "ringtone": "",
//       "disc": "01",
//       "no": 1,
//       "rtype": 0,
//       "rurl": null,
//       "copyrightId": 2706776,
//       "position": 0,
//       "duration": 265013,
//       "status": 0,
//       "alias": [],
//       "name": "谢谢你爱我 (2024)",
//       "id": 2626041152
//     }
//   ]
// }
