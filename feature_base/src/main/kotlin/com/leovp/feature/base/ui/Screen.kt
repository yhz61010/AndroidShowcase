package com.leovp.feature.base.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import com.leovp.feature.base.R
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * Author: Michael Leo
 * Date: 2023/9/12 14:31
 */

/**
 * Define all the screens used in this application.
 */
@Serializable
sealed class Screen(
    val route: String,
    @param:StringRes val nameResId: Int = 0,
    @Transient val iconVector: ImageVector? = null,
    @param:DrawableRes val iconResId: Int = 0,
) {
    inline fun <reified T : Any> getIcon(): T = (iconVector ?: iconResId) as T

    // val routeName: String = route.substringBefore("/", route)

    @Serializable
    data object Splash : Screen("app_splash")

    @Serializable
    data object Main : Screen("app_main")

    @Serializable
    data object Search : Screen("search_screen", R.string.bas_dis_search_screen_title)

    // ============================
    // ===== Home tab screens =====
    // ============================
    @Serializable
    data object Discovery : Screen(
        route = "app_discovery",
        nameResId = R.string.bas_main_tab_discovery,
        iconResId = R.drawable.bas_library_music,
    )

    @Serializable
    data object My : Screen(
        route = "app_my",
        nameResId = R.string.bas_main_tab_my,
        iconResId = R.drawable.bas_music_note,
    )

    @Serializable
    data object Community : Screen(
        route = "app_community",
        nameResId = R.string.bas_main_tab_community,
        iconResId = R.drawable.bas_speaker_notes,
    )
    // ==============================

    // ===============================
    // ===== Drawer item screens =====
    // ===============================
    @Serializable
    data object MemberCenter : Screen(
        route = "drawer_member_center",
        nameResId = R.string.bas_drawer_member_center,
        iconResId = R.drawable.bas_credit_card,
    )

    @Serializable
    data object Message : Screen(
        "drawer_messages",
        R.string.bas_drawer_message_label,
        iconResId = R.drawable.bas_mail,
    )

    @Serializable
    data object Setting : Screen(
        "drawer_setting",
        R.string.bas_drawer_settings_label,
        iconResId = R.drawable.bas_settings,
    )
    // ==============================

    // ============================
    // ====== Player screens ======
    // ============================
    @Serializable
    data class Player(val id: Long, val artist: String, val track: String) :
        Screen("player_screen")

    @Serializable
    data class Comment(
        val songId: Long,
        val songName: String,
        val artist: String
    ) : Screen(
        route = "comment_screen/{songInfo}",
        nameResId = R.string.bas_discovery_tab_comment,
    )

    @Serializable
    data object Note : Screen(
        route = "note_screen/{songInfo}",
        nameResId = R.string.bas_discovery_tab_note,
    )
}

enum class MainBottomNavigationItems(
    val screenProvider: () -> Screen,
) {
    DISCOVERY({ Screen.Discovery }),

    MY({ Screen.My }),

    COMMUNITY({ Screen.Community }),
    ;

    val screen: Screen get() = screenProvider()
}

enum class CommentBottomNavigationItems(
    val screenProvider: () -> Screen,
) {
    COMMENT({ Screen.Comment(0L, "", "") }),
    NOTE({ Screen.Note }),
    ;

    val screen: Screen get() = screenProvider()
}
