package com.leovp.androidshowcase.ui.tabs.discovery.data

import com.leovp.androidshowcase.R
import com.leovp.androidshowcase.ui.tabs.discovery.iters.MarkType

/**
 * Author: Michael Leo
 * Date: 2023/7/24 15:33
 */
class DiscoveryLocalDataSource {
    val discoveryCarouselRecommendedList = listOf(
        CarouselItemModel(id = 1, thumbnail = R.drawable.app_carousel_4649613),
        CarouselItemModel(id = 2, thumbnail = R.drawable.app_carousel_4655710),
        CarouselItemModel(id = 3, thumbnail = R.drawable.app_carousel_4658295),
        CarouselItemModel(id = 4, thumbnail = R.drawable.app_carousel_4658837),
        CarouselItemModel(id = 5, thumbnail = R.drawable.app_carousel_4931127),
        // CarouselItemModel(id = 6, thumbnail = R.drawable.app_carousel_4931315),
        // CarouselItemModel(id = 7, thumbnail = R.drawable.app_carousel_4953208),
        // CarouselItemModel(id = 8, thumbnail = R.drawable.app_carousel_4954761),
        // CarouselItemModel(id = 9, thumbnail = R.drawable.app_carousel_4957728),
        // CarouselItemModel(id = 10, thumbnail =R.drawable.app_carousel_4683687),
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
            showTrailIcon = true,
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
            showTrailIcon = true,
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