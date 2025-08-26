package com.leovp.androidshowcase.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.leovp.androidshowcase.R
import com.leovp.feature.base.res.Dimen
import com.leovp.feature.base.ui.DrawerDestinations
import com.leovp.feature.base.ui.LocalNavigationActions
import com.leovp.feature.base.ui.PreviewWrapperNoTheme
import com.leovp.feature.base.ui.Screen
import com.leovp.log.base.d
import com.leovp.ui.theme.AppTheme
import com.leovp.feature.base.R as BaseR

/**
 * Author: Michael Leo
 * Date: 2023/7/17 10:07
 */

private const val TAG = "AppDrawer"

@Composable
fun AppDrawer(
    currentRoute: String,
    onCloseDrawer: () -> Unit,
    modifier: Modifier = Modifier,
) {
    d(TAG) { "=> Enter AppDrawer <=" }
    val navController = LocalNavigationActions.current
    ModalDrawerSheet(modifier) {
        AppLogo(
            modifier =
                Modifier.padding(
                    start = 24.dp,
                    top = Dimen.drawerHeaderPaddingTop,
                    end =
                        NavigationDrawerItemDefaults.ItemPadding
                            .calculateEndPadding(
                                LayoutDirection.Ltr,
                            ),
                    bottom = Dimen.drawerHeaderPaddingBottom,
                ),
        )
        DrawerItem(
            iconResId = Screen.MemberCenter.getIcon(),
            nameResId = Screen.MemberCenter.nameResId,
            selected = currentRoute == Screen.MemberCenter.route,
            onClick = {
                navController.navigate(Screen.MemberCenter.route)
                onCloseDrawer()
            },
        )
        DrawerItem(
            iconResId = Screen.Message.getIcon(),
            nameResId = Screen.Message.nameResId,
            selected = currentRoute == Screen.Message.route,
            onClick = {
                navController.navigate(Screen.Message.route)
                onCloseDrawer()
            },
        )
        DrawerItem(
            iconResId = Screen.Setting.getIcon(),
            nameResId = Screen.Setting.nameResId,
            selected = currentRoute == Screen.Setting.route,
            onClick = {
                navController.navigate(Screen.Setting.route)
                onCloseDrawer()
            },
        )
        DrawerItem(
            iconResId = R.drawable.app_exit_to_app,
            nameResId = BaseR.string.bas_drawer_exit_label,
            selected = false,
            onClick = {},
        )
    }
}

@Composable
private fun DrawerItem(
    @DrawableRes iconResId: Int,
    @StringRes nameResId: Int,
    selected: Boolean,
    onClick: () -> Unit,
) {
    NavigationDrawerItem(
        label = { Text(stringResource(id = nameResId)) },
        icon = {
            Icon(
                painter = painterResource(id = iconResId),
                contentDescription = null,
            )
        },
        selected = selected,
        onClick = onClick,
        // modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        modifier =
            Modifier
                .height(Dimen.drawerItemHeight)
                .padding(horizontal = Dimen.drawerItemHorizontalPadding),
    )
}

@Composable
private fun AppLogo(modifier: Modifier = Modifier) {
    d(TAG) { "=> Enter AppLogo <=" }
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painterResource(R.drawable.app_user_avatar),
            contentDescription = null,
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = "Michael Leo",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Preview("Drawer contents")
@Preview("Drawer contents (dark)", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewAppDrawer() {
    PreviewWrapperNoTheme {
        AppTheme(dynamicColor = false) {
            AppDrawer(
                currentRoute = DrawerDestinations.NO_ROUTE,
                onCloseDrawer = { },
            )
        }
    }
}
