package com.leovp.androidshowcase.domain.model

import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import com.leovp.androidshowcase.ui.Screen

/**
 * Author: Michael Leo
 * Date: 2023/9/12 13:33
 */

@Keep
@Immutable
data class UnreadModel(
    val key: String,
    val value: Int,
) {
    companion object {
        val MESSAGE = Screen.MemberCenterScreen.route

        val DISCOVERY = Screen.Discovery.route
        val MY = Screen.My.route
        val COMMUNITY = Screen.Community.route
    }
}
