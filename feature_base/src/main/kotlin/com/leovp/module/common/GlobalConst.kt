@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package com.leovp.module.common

/**
 * Author: Michael Leo
 * Date: 2023/7/6 15:54
 */
object GlobalConst {
    // private const val TAG = "GlobalConst"

    const val DEBUG = BuildConfig.DEBUG_MODE
    const val VERSION_NAME = BuildConfig.VERSION_NAME

    // ===== Http request - Start =====
    const val API_BASE_URL = BuildConfig.GRADLE_API_BASE_URL
    const val API_TOKEN = BuildConfig.GRADLE_API_TOKEN

    const val HTTP_GET_SEARCH_ALBUM = "./?method=album.search"
    const val HTTP_GET_ARTIST_TOP_TRACKS = "./?method=artist.gettoptracks"
    const val HTTP_GET_ARTIST_TOP_ALBUMS = "./?method=artist.gettopalbums"
    // ===== Http request - End =====
}
