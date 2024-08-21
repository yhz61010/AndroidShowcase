package com.leovp.androidshowcase.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import com.leovp.androidshowcase.R

/**
 * Author: Michael Leo
 * Date: 2023/9/12 14:31
 */

/**
 * Define all the screens used in this application.
 */
sealed class Screen(
    val route: String,
    @StringRes val nameResId: Int = 0,
    val iconVector: ImageVector? = null,
    @DrawableRes val iconResId: Int = 0,
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
        nameResId = R.string.app_main_tab_discovery,
        iconResId = R.drawable.app_library_music,
    )

    data object My : Screen(
        route = "app_my",
        nameResId = R.string.app_main_tab_my,
        iconResId = R.drawable.app_music_note
    )

    data object Community : Screen(
        route = "app_community",
        nameResId = R.string.app_main_tab_community,
        iconResId = R.drawable.app_speaker_notes
    )
    // ==============================

    // ===============================
    // ===== Drawer item screens =====
    // ===============================
    data object MemberCenterScreen : Screen(
        route = "drawer_member_center",
        nameResId = com.leovp.feature_main_drawer.R.string.drawer_member_center,
        iconResId = R.drawable.app_credit_card
    )

    data object MessageScreen : Screen(
        "drawer_messages",
        com.leovp.feature_main_drawer.R.string.drawer_message_label,
        iconResId = R.drawable.app_mail
    )

    data object SettingScreen : Screen(
        "drawer_setting",
        com.leovp.feature_main_drawer.R.string.drawer_settings_label,
        iconResId = R.drawable.app_settings
    )
    // ==============================

    data object SearchScreen : Screen(
        "search_screen",
        com.leovp.feature_discovery.R.string.dis_search_screen_title,
    )

    data object PlayerScreen : Screen(
        "player_screen/{id}/{title}"
    )
}