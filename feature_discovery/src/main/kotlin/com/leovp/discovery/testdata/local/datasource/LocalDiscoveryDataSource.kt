@file:Suppress(
    "ktlint:standard:max-line-length", // for ktlint
    "MaximumLineLength", // for detekt
    "MaxLineLength", // for detekt
    "LongLine", // for detekt
)

package com.leovp.discovery.testdata.local.datasource

import com.leovp.discovery.data.datasource.DiscoveryDataSource
import com.leovp.discovery.domain.enum.MarkType
import com.leovp.discovery.domain.model.AlbumModel
import com.leovp.discovery.domain.model.ArtistModel
import com.leovp.discovery.domain.model.BannerModel
import com.leovp.discovery.domain.model.HomePageBlockModel
import com.leovp.discovery.domain.model.PlaylistModel
import com.leovp.discovery.domain.model.PrivateContentModel
import com.leovp.discovery.domain.model.TopSongModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDiscoveryDataSource
    @Inject
    constructor() : DiscoveryDataSource {
        override fun getHomePageBlock() =
            HomePageBlockModel(
                banners =
                    listOf(
                        BannerModel(
                            bannerId = "1717750651205666",
                            pic = "http://p1.music.126.net/e8wtQuscq74yweCGuhM8TA==/109951169664474779.jpg",
                            url = "https://y.music.163.com/g/yida/470cb610d27249f09f9f6c3d595b9457",
                            titleColor = "blue",
                            typeTitle = "独家策划",
                        ),
                        BannerModel(
                            bannerId = "1717750796593434",
                            pic = "http://p1.music.126.net/snMCiwfSKevsgQrl-PjwYQ==/109951169664482196.jpg",
                            url = "https://y.music.163.com/g/yida/6894c31af6f548ca9c852566f10bc4eb",
                            titleColor = "blue",
                            typeTitle = "独家策划",
                        ),
                        BannerModel(
                            bannerId = "1717750712447791",
                            pic = "http://p1.music.126.net/oRND1RziUgkrYBuSFgz5yQ==/109951169664471894.jpg",
                            url = null,
                            titleColor = "red",
                            typeTitle = "歌单",
                        ),
                    ),
            )

        override fun getPrivateContent() =
            listOf(
                PrivateContentModel(
                    id = 14514232,
                    name = "《超级面对面》第254期 keshi：我的音乐永远新鲜",
                    picUrl = "https://p2.music.126.net/ZfQoqzo0rTqe3sFKkDnGuw==/109951167211930348.jpg",
                ).apply {
                    type = 5
                    typeName = "独家放送"
                },
                PrivateContentModel(
                    id = 14496631,
                    name = "《超级面对面》第252期 Alan Walker：和Walkers一起遨游宇宙",
                    picUrl = "https://p2.music.126.net/IVEDKgbd--myWBukXgreNg==/109951166983018052.jpg",
                ).apply {
                    type = 5
                    typeName = "独家放送"
                },
                PrivateContentModel(
                    id = 14484135,
                    name = "《超级面对面》第250期 孙燕姿：在对的时间听对的歌",
                    picUrl = "https://p2.music.126.net/_q264UzyPqfepTgXfJcdig==/109951166780766424.jpg",
                ).apply {
                    type = 5
                    typeName = "独家放送"
                },
            )

        override fun getTopSong(type: Int) =
            listOf(
                TopSongModel(
                    id = 2625746775,
                    name = "壁上观",
                    album =
                        AlbumModel(
                            id = 247470291,
                            name = "壁上观",
                            picUrl = "http://p1.music.126.net/eZEjSm4b1iLIRpIeC9-dBA==/109951169950184961.jpg",
                        ),
                    artists =
                        listOf(
                            ArtistModel(
                                id = 7785,
                                name = "龚琳娜",
                                picId = 0,
                                picUrl = "http://p1.music.126.net/6y-UleORITEDbvrOLV0Q8A==/5639395138885805.jpg",
                            ),
                        ),
                ).apply {
                    showTrailIcon = true
                    markType = MarkType.HiRes
                },
                TopSongModel(
                    id = 2625779441,
                    name = "爱是唯一的选择 (舒楠监制 官方正式版) 中文版",
                    album =
                        AlbumModel(
                            id = 0,
                            name = "0",
                            picUrl = "http://p1.music.126.net/6y-UleORITEDbvrOLV0Q8A==/5639395138885805.jpg",
                        ),
                    artists =
                        listOf(
                            ArtistModel(
                                id = 12664439,
                                name = "希林娜依高",
                                picId = 0,
                                picUrl = "http://p1.music.126.net/6y-UleORITEDbvrOLV0Q8A==/5639395138885805.jpg",
                            ),
                        ),
                ),
                TopSongModel(
                    id = 2615100384,
                    name = "Summer Princess",
                    album =
                        AlbumModel(
                            id = 244358006,
                            name = "Summer Princess",
                            picUrl = "http://p1.music.126.net/ncJ0EuaBAo14yk1N4gJayw==/109951169851873933.jpg",
                        ),
                    artists =
                        listOf(
                            ArtistModel(
                                id = 14270,
                                name = "平井 大",
                                picId = 0,
                                picUrl = "http://p1.music.126.net/6y-UleORITEDbvrOLV0Q8A==/5639395138885805.jpg",
                            ),
                        ),
                ),
            )

        override fun getRecommendPlaylist() =
            listOf(
                PlaylistModel(
                    id = 123816281,
                    name = "「欧美Live」惊艳现场版，嗨到停不下来!",
                    picUrl = "https://p2.music.126.net/ZtQOTgvhqrcWYapiPj9NWQ==/19018252626210242.jpg",
                ),
                PlaylistModel(
                    id = 158010361,
                    name = "『1963-至今』日本经典动漫音乐大盘点",
                    picUrl = "https://p2.music.126.net/WPHAmuqQaaQGIcx199t5XQ==/3272146613241669.jpg",
                ),
                PlaylistModel(
                    id = 163150274,
                    name = "高能燃脂•假期健身跑步必备BGM",
                    picUrl = "https://p2.music.126.net/-4Ygb5Z9XfLOQFeldQlY2A==/1389782698058443.jpg",
                ),
                PlaylistModel(
                    id = 2476663154,
                    name = "经典粤语合集【无损音质】黑胶唱片会员专属",
                    picUrl = "https://p2.music.126.net/zAuVCW-cUvn29s9IxuQh-w==/109951167071306612.jpg",
                ),
                PlaylistModel(
                    id = 514947114,
                    name = "华语民谣 I 孤独的心诠释诗意和远方",
                    picUrl = "https://p2.music.126.net/UDpjFEHmXInxGd_xMAI12w==/109951162811986419.jpg",
                ),
            )
    }
