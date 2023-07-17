package com.leovp.androidshowcase.ui.main

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
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
import com.leovp.androidshowcase.ui.theme.AppTheme

/**
 * Author: Michael Leo
 * Date: 2023/7/17 10:07
 */

@Composable
fun AppDrawer(
    currentRoute: String,
    navigateToHome: () -> Unit,
    navigateToInterests: () -> Unit,
    navigateToMy: () -> Unit,
    closeDrawer: () -> Unit,
    modifier: Modifier = Modifier
) {
    ModalDrawerSheet(modifier) {
        AppLogo(
            modifier = Modifier
                .padding(
                    horizontal = NavigationDrawerItemDefaults.ItemPadding.calculateStartPadding(LayoutDirection.Ltr),
                    vertical = 24.dp
                )
        )
        NavigationDrawerItem(
            label = { Text(stringResource(id = R.string.app_menu_home)) },
            icon = { Icon(Icons.Outlined.Home, null) },
            selected = currentRoute == AppDestinations.HOME_ROUTE,
            onClick = { navigateToHome(); closeDrawer() },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        NavigationDrawerItem(
            label = { Text(stringResource(id = R.string.app_menu_interests)) },
            icon = { Icon(Icons.Outlined.FavoriteBorder, null) },
            selected = currentRoute == AppDestinations.INTERESTS_ROUTE,
            onClick = { navigateToInterests(); closeDrawer() },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        NavigationDrawerItem(
            label = { Text(stringResource(id = R.string.app_menu_my)) },
            icon = { Icon(Icons.Outlined.Person, null) },
            selected = currentRoute == AppDestinations.MY_ROUTE,
            onClick = { navigateToMy(); closeDrawer() },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
    }
}

@Composable
private fun AppLogo(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
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
    AppTheme {
        AppDrawer(
            currentRoute = AppDestinations.HOME_ROUTE,
            navigateToHome = {},
            navigateToInterests = {},
            navigateToMy = {},
            closeDrawer = { }
        )
    }
}
