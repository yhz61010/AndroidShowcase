@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package com.leovp.module.common

/**
 * Author: Michael Leo
 * Date: 2023/7/6 15:54
 */
object GlobalConst {
    // private const val TAG = "GlobalConst"

    @Suppress("SENSELESS_COMPARISON")
    const val DEBUG = BuildConfig.DEBUG_MODE
    const val VERSION_NAME = BuildConfig.VERSION_NAME

    const val PLAYLIST_SONG_SIZE = 27

    @JvmInline
    value class ImageThumb internal constructor(val value: Int) {
        companion object {
            private const val BANNER_RATIO = 0.6F

            val BANNER_WIDTH = ImageThumb((1080 * BANNER_RATIO).toInt())
            val BANNER_HEIGHT = ImageThumb((399 * BANNER_RATIO).toInt())

            val PLAYLIST_WIDTH = ImageThumb(256)
            val PLAYLIST_HEIGHT = ImageThumb(256)

            val TRACK_SMALL_WIDTH = ImageThumb(128)
            val TRACK_SMALL_HEIGHT = ImageThumb(128)

            val TRACK_LARGE_WIDTH = ImageThumb(512)
            val TRACK_LARGE_HEIGHT = ImageThumb(512)

            val ALBUM_WIDTH = ImageThumb(128)
            val ALBUM_HEIGHT = ImageThumb(128)
        }
    }

    // ===== Http request - Start =====
    const val API_BASE_URL = BuildConfig.GRADLE_API_BASE_URL
    // const val API_TOKEN = BuildConfig.GRADLE_API_TOKEN

    /** 获取独家放送 */
    const val HTTP_GET_PRIVATE_CONTENT = "/personalized/privatecontent"

    // /personalized?limit=30
    /** 获取推荐歌单 */
    const val HTTP_GET_PERSONALIZED = "/personalized"

    // type:
    //      全部: 0
    //      华语: 7
    //      欧美: 96
    //      日本: 8
    //      韩国: 16
    //
    // /top/song?type=7
    /** 新歌速递 */
    const val HTTP_GET_TOP_SONG = "/top/song"

    // refresh: false
    // cursor
    /** 首页-发现 */
    const val HTTP_GET_HOMEPAGE_BLOCK_PAGE = "/homepage/block/page"

    // ids=123
    // ids=123,456
    /** 获取歌曲详情 */
    const val HTTP_GET_SONG_DETAIL = "/song/detail"

    // id
    // limit: Default value 20
    // offset: 偏移数量 , 用于分页 , 如 :( 评论页数 -1)*20, 其中 20 为 limit 的值
    // before: 分页参数,取上一页最后一项的 time 获取下一页数据(获取超过 5000 条评论的时候需要用到)
    /** 歌曲评论 */
    const val HTTP_GET_MUSIC_COMMENT = "/comment/music"

    // id
    /** 歌曲红心数量 */
    const val HTTP_GET_SONG_RED_COUNT = "/song/red/count"
    // ===== Http request - End =====
}
