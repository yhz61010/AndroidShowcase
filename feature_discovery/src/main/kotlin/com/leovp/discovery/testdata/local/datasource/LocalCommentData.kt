package com.leovp.discovery.testdata.local.datasource

import com.leovp.discovery.domain.model.AlbumModel
import com.leovp.discovery.domain.model.ArtistModel
import com.leovp.discovery.domain.model.SongModel
import com.leovp.discovery.domain.model.SongModel.Quality

/**
 * Author: Michael Leo
 * Date: 2025/8/27 08:48
 */
object LocalCommentData {
    val previewSongInfo =
        SongModel(
            id = 1,
            name = "青花",
            duration = 240000,
            artists = listOf(ArtistModel(id = 1, name = "周传雄")),
            album =
                AlbumModel(
                    id = 1,
                    name = "Album Name",
                    picUrl = "",
                ),
            quality = Quality.Jymaster,
            fee = 8,
            markText = "VIP",
        )

    val mockComments = listOf(
        SongModel.Comment(
            id = 1,
            userName = "马德拉岛翩翩美少年",
            userAvatar = "https://via.placeholder.com/40x40",
            isVip = true,
            location = "天津",
            comment = "父亲做生意失败了，母亲把首饰全部变现了。24年6月毕业，拿着第一份工资，给我妈重新买了个大金镯子，兄弟们，帅不帅",
            timeAgo = "2024-11-13",
            likeCount = 19147,
            isLiked = false,
            replyCount = 1368
        ),
        SongModel.Comment(
            id = 2,
            userName = "戴上耳机qwq",
            userAvatar = "https://via.placeholder.com/40x40",
            isVip = true,
            location = "上海",
            comment = "8月听 青花 的人都不会太差",
            timeAgo = "08-01",
            likeCount = 1460,
            isLiked = false,
            replyCount = 105
        ),
        SongModel.Comment(
            id = 3,
            userName = "勇士不必多言",
            userAvatar = "https://via.placeholder.com/40x40",
            isVip = true,
            location = "广西",
            comment = "守护边境已经1689天，祝祖国繁荣昌盛！！！",
            timeAgo = "2024-09-18",
            likeCount = 25293,
            isLiked = false,
            replyCount = 750
        )
    )
}
