package com.leovp.androidshowcase.ui.tabs.discovery.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EventNote
import com.leovp.androidshowcase.ui.tabs.discovery.iters.MarkType
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

/**
 * Author: Michael Leo
 * Date: 2023/7/24 15:33
 */

private val monthDateFormat = SimpleDateFormat("MM-dd", Locale.CHINA).apply { timeZone = TimeZone.getDefault() }

class DiscoveryLocalDataSource {
    val discoveryCarouselRecommendedList = listOf(
        CarouselItemModel(
            id = 1,
            thumbnail = "http://p1.music.126.net/nuPhaKdShV1SbJRQxOR6uA==/109951168779030027.jpg?imageView&quality=89"
        ),
        CarouselItemModel(
            id = 2,
            thumbnail = "http://p1.music.126.net/iXS1MfFRpyjz4c8b7s1LNQ==/109951168783578441.jpg?imageView&quality=89"
        ),
        CarouselItemModel(
            id = 3,
            thumbnail = "http://p1.music.126.net/-4sI1BeEYt_tPvVn6UbTew==/109951168783491497.jpg?imageView&quality=89"
        ),
        CarouselItemModel(
            id = 4,
            thumbnail = "http://p1.music.126.net/qX05Xi8lfcDXUEgOfpGTdQ==/109951168783605345.jpg?imageView&quality=89"
        ),
        CarouselItemModel(
            id = 5,
            thumbnail = "http://p1.music.126.net/kw1TvDb83TdnCVFiMlksGw==/109951168783590288.jpg?imageView&quality=89"
        ),
    )

    val discoveryEverydayRecommendedList = listOf(
        EverydayItemModel(
            id = 1,
            thumbnail = "https://qpic.y.qq.com/music_cover/I28ic19Iwp8AicOQlxrIkEIia7YhYpI2c0v9DUT9JOhE0BGiayopLlnwXw/300?n=1",
            type = monthDateFormat.format(Date()),
            title = "每日推荐|从「是你」听起",
            icon = Icons.Default.EventNote
        ),
        EverydayItemModel(
            id = 2,
            thumbnail = "https://qpic.y.qq.com/music_cover/iaVNVUXOg73ua71Z0dtuz7Z6ZVOdf97nUUevQmSIDyw4ZlJ6wrvAXWA/300?n=1",
            type = "私人漫游",
            title = "从「簇拥烈日的花」开启无限漫游",
            icon = null
        ),
        EverydayItemModel(
            id = 3,
            thumbnail = "https://qpic.y.qq.com/music_cover/DW3wgydKNSuLicEooicQhGYvxf8xNKJZaVJla3cvpZe8icaludOwS1tKg/300?n=1",
            type = "摇滚日推",
            title = "带我走、情人、Episode 33",
            icon = null
        ),
        EverydayItemModel(
            id = 4,
            thumbnail = "https://qpic.y.qq.com/music_cover/7OvyVEUhXlyQibJIaz19cNYoPhSEl6FdRsGeHxWMcVtr1qQknxib2AhA/300?n=1",
            type = "民谣日推",
            title = "如果有来生、The Sound Of Silence、这一生关于你的风景",
            icon = null
        ),
        EverydayItemModel(
            id = 5,
            thumbnail = "https://qpic.y.qq.com/music_cover/hibibCWiaO6RZzGGCYYicdoVnxCjBdG4kZGhYouhAnaiarRCYbeA2E1FibYQ/300?n=1",
            type = "二次元日推",
            title = "ReOracle、Rolling star、summertime",
            icon = null
        ),
        EverydayItemModel(
            id = 6,
            thumbnail = "https://qpic.y.qq.com/music_cover/icXjXNAaErryuWaGROaiandGY1TowGKgyHx141ajhrMApvsicibjQo8VejxqXI1AxZLr/300?n=1",
            type = "乡村日推",
            title = "El condor pass、Take Me Home Country Roads、You Can Depend On Me",
            icon = null
        ),
    )

    val discoveryPersonalRecommendedMusicList = listOf(
        SimpleListItemModel(
            id = 1,
            thumbnail = "https://is1-ssl.mzstatic.com/image/thumb/Features/19/fa/5d/dj.ashmqwlx.jpg/632x632bf.webp",
            title = "The end of the world",
            subTitle = "Skeeter Davis",
            markText = "超72%人播放>",
            showTrailIcon = true,
            type = MarkType.Hot
        ),
        SimpleListItemModel(
            id = 2,
            thumbnail = "https://is1-ssl.mzstatic.com/image/thumb/Music/v4/85/b5/d9/85b5d926-aa4b-a5ec-6ced-bbf6303d41ea/887396379664.tif/632x632bb.webp",
            title = "I Will Follow Him",
            subTitle = "Skeeter Davis",
            markText = "超43%人收藏>",
            showTrailIcon = false,
            type = MarkType.Hot
        ),
        SimpleListItemModel(
            id = 3,
            thumbnail = "https://is1-ssl.mzstatic.com/image/thumb/Music114/v4/a5/c7/56/a5c75619-5a46-2a9b-fd82-593893abfd04/00724355696854.jpg/632x632bb.webp",
            title = "Scarborough Fair",
            subTitle = "Sarah Brightman",
            markText = "超清母带",
            showTrailIcon = true,
            type = MarkType.Special
        ),
        SimpleListItemModel(
            id = 4,
            thumbnail = "https://is1-ssl.mzstatic.com/image/thumb/Music126/v4/ed/9b/75/ed9b75fd-e5af-64aa-1452-4c5390a6991b/5099706321323.jpg/632x632bb.webp",
            title = "My Heart Will Go On (Love Theme from \"Titanic\")",
            subTitle = "Céline Dion",
            markText = "VIP",
            showTrailIcon = true,
            type = MarkType.Vip
        ),
        SimpleListItemModel(
            id = 5,
            thumbnail = "https://is1-ssl.mzstatic.com/image/thumb/Music114/v4/32/95/52/329552f5-a018-ff8f-5021-30375eefa14d/cover.jpg/632x632bb.webp",
            title = "追梦人",
            subTitle = "凤飞飞",
            markText = "昨日万人播放>",
            showTrailIcon = false,
            type = MarkType.Hot
        ),
        SimpleListItemModel(
            id = 6,
            thumbnail = "https://is1-ssl.mzstatic.com/image/thumb/Music116/v4/9d/b3/7c/9db37ca6-2a80-45d0-f03d-0f7f134f893e/cover.jpg/632x632bb.webp",
            title = "盗将行",
            subTitle = "花粥/马雨阳",
            markText = "十万评论>",
            showTrailIcon = true,
            type = MarkType.Hot
        ),
        SimpleListItemModel(
            id = 7,
            thumbnail = "https://is1-ssl.mzstatic.com/image/thumb/Music125/v4/fd/2c/fa/fd2cfa89-c6c4-e564-8e6e-7f03a5377744/886447854761.jpg/632x632bb.webp",
            title = "Because You Loved Me",
            subTitle = "Céline Dion",
            markText = "十万红心>",
            showTrailIcon = true,
            type = MarkType.Hot
        ),
        SimpleListItemModel(
            id = 8,
            thumbnail = "https://is1-ssl.mzstatic.com/image/thumb/Music115/v4/9e/a4/e7/9ea4e751-f374-4173-2aca-faea06440e0c/s06.pnpmefxw.jpg/632x632bb.webp",
            title = "Exile",
            subTitle = "Enya",
            markText = "沉浸声",
            showTrailIcon = true,
            type = MarkType.Special
        ),
        SimpleListItemModel(
            id = 9,
            thumbnail = "https://is1-ssl.mzstatic.com/image/thumb/Music114/v4/a5/c7/56/a5c75619-5a46-2a9b-fd82-593893abfd04/00724355696854.jpg/632x632bb.webp",
            title = "Scarborough Fair",
            subTitle = "Sarah Brightman",
            markText = "小众佳作>",
            showTrailIcon = true,
            type = MarkType.Hot
        )
    )
}