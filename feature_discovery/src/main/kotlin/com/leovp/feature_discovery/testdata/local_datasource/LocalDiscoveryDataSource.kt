package com.leovp.feature_discovery.testdata.local_datasource

import com.leovp.feature_discovery.data.datasource.DiscoveryDataSource
import com.leovp.feature_discovery.domain.enum.ImageSize
import com.leovp.feature_discovery.domain.enum.MarkType
import com.leovp.feature_discovery.domain.model.CarouselItem
import com.leovp.feature_discovery.domain.model.EverydayItem
import com.leovp.feature_discovery.domain.model.Image
import com.leovp.feature_discovery.domain.model.MusicItem
import com.leovp.module.common.utils.monthDateFormat
import java.util.Date

class LocalDiscoveryDataSource : DiscoveryDataSource {
    override fun getCarouselRecommendedList() = listOf(
        CarouselItem(
            id = 1,
            thumbnail = "https://images.unsplash.com/photo-1584679109597-c656b19974c9?auto=crop&fit=crop&w=540&h=210&q=80"
        ),
        CarouselItem(
            id = 2,
            thumbnail = "https://images.unsplash.com/photo-1502773860571-211a597d6e4b?auto=crop&fit=crop&w=540&h=210&q=80"
        ),
        CarouselItem(
            id = 3,
            thumbnail = "https://images.unsplash.com/photo-1446057032654-9d8885db76c6?auto=crop&fit=crop&w=540&h=210&q=80"
        ),
        CarouselItem(
            id = 4,
            thumbnail = "https://images.unsplash.com/photo-1511379938547-c1f69419868d?auto=crop&fit=crop&w=540&h=210&q=80"
        ),
        CarouselItem(
            id = 5,
            thumbnail = "https://images.unsplash.com/photo-1618972676849-feed401eacc5?auto=crop&fit=crop&w=540&h=210&q=80"
        ),
    )

    override fun getEverydayRecommendedList() = listOf(
        EverydayItem(
            id = 1,
            thumbnail = listOf(
                Image(
                    url = "https://qpic.y.qq.com/music_cover/I28ic19Iwp8AicOQlxrIkEIia7YhYpI2c0v9DUT9JOhE0BGiayopLlnwXw/300?n=1",
                    size = ImageSize.EXTRA_LARGE
                )
            ),
            type = monthDateFormat.format(Date()),
            title = "每日推荐|从「是你」听起",
            icon = null // calendar_month
        ),
        EverydayItem(
            id = 2,
            thumbnail = listOf(
                Image(
                    url = "https://qpic.y.qq.com/music_cover/iaVNVUXOg73ua71Z0dtuz7Z6ZVOdf97nUUevQmSIDyw4ZlJ6wrvAXWA/300?n=1",
                    size = ImageSize.EXTRA_LARGE
                )
            ),
            type = "私人漫游",
            title = "从「簇拥烈日的花」开启无限漫游",
            icon = null
        ),
        EverydayItem(
            id = 3,
            thumbnail = listOf(
                Image(
                    url = "https://qpic.y.qq.com/music_cover/DW3wgydKNSuLicEooicQhGYvxf8xNKJZaVJla3cvpZe8icaludOwS1tKg/300?n=1",
                    size = ImageSize.EXTRA_LARGE
                )
            ),
            type = "摇滚日推",
            title = "带我走、情人、Episode 33",
            icon = null
        ),
        EverydayItem(
            id = 4,
            thumbnail = listOf(
                Image(
                    url = "https://qpic.y.qq.com/music_cover/7OvyVEUhXlyQibJIaz19cNYoPhSEl6FdRsGeHxWMcVtr1qQknxib2AhA/300?n=1",
                    size = ImageSize.EXTRA_LARGE
                )
            ),
            type = "民谣日推",
            title = "如果有来生、The Sound Of Silence、这一生关于你的风景",
            icon = null
        ),
        EverydayItem(
            id = 5,
            thumbnail = listOf(
                Image(
                    url = "https://qpic.y.qq.com/music_cover/hibibCWiaO6RZzGGCYYicdoVnxCjBdG4kZGhYouhAnaiarRCYbeA2E1FibYQ/300?n=1",
                    size = ImageSize.EXTRA_LARGE
                )
            ),
            type = "二次元日推",
            title = "ReOracle、Rolling star、summertime",
            icon = null
        ),
        EverydayItem(
            id = 6,
            thumbnail = listOf(
                Image(
                    url = "https://qpic.y.qq.com/music_cover/icXjXNAaErryuWaGROaiandGY1TowGKgyHx141ajhrMApvsicibjQo8VejxqXI1AxZLr/300?n=1",
                    size = ImageSize.EXTRA_LARGE
                )
            ),
            type = "乡村日推",
            title = "El condor pass、Take Me Home Country Roads、You Can Depend On Me",
            icon = null
        ),
    )

    override fun getPersonalRecommendedMusicList() = listOf(
        MusicItem(
            id = 100,
            thumbnail = listOf(
                Image(
                    url = "https://is1-ssl.mzstatic.com/image/thumb/Music126/v4/d2/7e/0c/d27e0c67-cac3-3b81-a3c3-484650c8bf9c/shangeliaozai.jpg/232x232bb.webp",
                    size = ImageSize.EXTRA_LARGE,
                )
            ),
            title = "罗刹海市",
            subTitle = "刀郎",
            markText = "万人评论>",
            showTrailIcon = false,
            type = MarkType.Hot
        ),
        MusicItem(
            id = 1,
            thumbnail = listOf(
                Image(
                    url = "https://is1-ssl.mzstatic.com/image/thumb/Features/19/fa/5d/dj.ashmqwlx.jpg/232x232bf.webp",
                    size = ImageSize.EXTRA_LARGE,
                )
            ),
            title = "The end of the world",
            subTitle = "Skeeter Davis",
            markText = "超72%人播放>",
            showTrailIcon = true,
            type = MarkType.Hot
        ),
        MusicItem(
            id = 2,
            thumbnail = listOf(
                Image(
                    url = "https://is1-ssl.mzstatic.com/image/thumb/Music/v4/85/b5/d9/85b5d926-aa4b-a5ec-6ced-bbf6303d41ea/887396379664.tif/232x232bb.webp",
                    size = ImageSize.EXTRA_LARGE,
                )
            ),
            title = "I Will Follow Him",
            subTitle = "Skeeter Davis",
            markText = "超43%人收藏>",
            showTrailIcon = false,
            type = MarkType.Hot
        ),
        MusicItem(
            id = 3,
            thumbnail = listOf(
                Image(
                    url = "https://is1-ssl.mzstatic.com/image/thumb/Music114/v4/a5/c7/56/a5c75619-5a46-2a9b-fd82-593893abfd04/00724355696854.jpg/232x232bb.webp",
                    size = ImageSize.EXTRA_LARGE,
                )
            ),
            title = "Scarborough Fair",
            subTitle = "Sarah Brightman",
            markText = "超清母带",
            showTrailIcon = true,
            type = MarkType.Special
        ),
        MusicItem(
            id = 4,
            thumbnail = listOf(
                Image(
                    url = "https://is1-ssl.mzstatic.com/image/thumb/Music126/v4/ed/9b/75/ed9b75fd-e5af-64aa-1452-4c5390a6991b/5099706321323.jpg/232x232bb.webp",
                    size = ImageSize.EXTRA_LARGE,
                )
            ),
            title = "My Heart Will Go On (Love Theme from \"Titanic\")",
            subTitle = "Céline Dion",
            markText = "VIP",
            showTrailIcon = true,
            type = MarkType.Vip
        ),
        MusicItem(
            id = 5,
            thumbnail = listOf(
                Image(
                    url = "https://is1-ssl.mzstatic.com/image/thumb/Music114/v4/32/95/52/329552f5-a018-ff8f-5021-30375eefa14d/cover.jpg/232x232bb.webp",
                    size = ImageSize.EXTRA_LARGE,
                )
            ),
            title = "追梦人",
            subTitle = "凤飞飞",
            markText = "昨日万人播放>",
            showTrailIcon = false,
            type = MarkType.Hot
        ),
        MusicItem(
            id = 6,
            thumbnail = listOf(
                Image(
                    url = "https://is1-ssl.mzstatic.com/image/thumb/Music116/v4/9d/b3/7c/9db37ca6-2a80-45d0-f03d-0f7f134f893e/cover.jpg/232x232bb.webp",
                    size = ImageSize.EXTRA_LARGE,
                )
            ),
            title = "盗将行",
            subTitle = "花粥/马雨阳",
            markText = "十万评论>",
            showTrailIcon = true,
            type = MarkType.Hot
        ),
        MusicItem(
            id = 7,
            thumbnail = listOf(
                Image(
                    url = "https://is1-ssl.mzstatic.com/image/thumb/Music125/v4/fd/2c/fa/fd2cfa89-c6c4-e564-8e6e-7f03a5377744/886447854761.jpg/232x232bb.webp",
                    size = ImageSize.EXTRA_LARGE,
                )
            ),
            title = "Because You Loved Me",
            subTitle = "Céline Dion",
            markText = "十万红心>",
            showTrailIcon = true,
            type = MarkType.Hot
        ),
        MusicItem(
            id = 8,
            thumbnail = listOf(
                Image(
                    url = "https://is1-ssl.mzstatic.com/image/thumb/Music115/v4/9e/a4/e7/9ea4e751-f374-4173-2aca-faea06440e0c/s06.pnpmefxw.jpg/232x232bb.webp",
                    size = ImageSize.EXTRA_LARGE,
                )
            ),
            title = "Exile",
            subTitle = "Enya",
            markText = "沉浸声",
            showTrailIcon = true,
            type = MarkType.Special
        ),
        MusicItem(
            id = 9,
            thumbnail = listOf(
                Image(
                    url = "https://is1-ssl.mzstatic.com/image/thumb/Music114/v4/a5/c7/56/a5c75619-5a46-2a9b-fd82-593893abfd04/00724355696854.jpg/232x232bb.webp",
                    size = ImageSize.EXTRA_LARGE,
                )
            ),
            title = "Scarborough Fair",
            subTitle = "Sarah Brightman",
            markText = "小众佳作>",
            showTrailIcon = true,
            type = MarkType.Hot
        )
    )
}