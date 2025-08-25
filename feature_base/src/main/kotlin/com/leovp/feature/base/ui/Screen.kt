package com.leovp.feature.base.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import com.leovp.feature.base.R

/**
 * Author: Michael Leo
 * Date: 2023/9/12 14:31
 */

/**
 * Define all the screens used in this application.
 */
sealed class Screen(
    val route: String,
    @param:StringRes val nameResId: Int = 0,
    val iconVector: ImageVector? = null,
    @param:DrawableRes val iconResId: Int = 0,
) {
    inline fun <reified T : Any> getIcon(): T = (iconVector ?: iconResId) as T

    val routeName: String = route.substringBefore("/", route)

    data object Splash : Screen("app_splash")

    data object Main : Screen("app_main")

    // ============================
    // ===== Home tab screens =====
    // ============================
    data object Discovery : Screen(
        route = "app_discovery",
        nameResId = R.string.bas_main_tab_discovery,
        iconResId = R.drawable.bas_library_music,
    )

    data object My : Screen(
        route = "app_my",
        nameResId = R.string.bas_main_tab_my,
        iconResId = R.drawable.bas_music_note,
    )

    data object Community : Screen(
        route = "app_community",
        nameResId = R.string.bas_main_tab_community,
        iconResId = R.drawable.bas_speaker_notes,
    )
    // ==============================

    // ===============================
    // ===== Drawer item screens =====
    // ===============================
    data object MemberCenterScreen : Screen(
        route = "drawer_member_center",
        nameResId = R.string.bas_drawer_member_center,
        iconResId = R.drawable.bas_credit_card,
    )

    data object MessageScreen : Screen(
        "drawer_messages",
        R.string.bas_drawer_message_label,
        iconResId = R.drawable.bas_mail,
    )

    data object SettingScreen : Screen(
        "drawer_setting",
        R.string.bas_drawer_settings_label,
        iconResId = R.drawable.bas_settings,
    )
    // ==============================

    data object SearchScreen : Screen(
        "search_screen",
        R.string.bas_dis_search_screen_title,
    )

    data object PlayerScreen : Screen(
        "player_screen/{id}/{artist}/{track}",
    )

    data object CommentScreen : Screen(
        "comment_screen/{songInfo}",
    )
}
