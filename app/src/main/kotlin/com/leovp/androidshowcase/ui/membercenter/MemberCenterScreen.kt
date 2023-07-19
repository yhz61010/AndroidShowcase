package com.leovp.androidshowcase.ui.membercenter

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.leovp.androidshowcase.R

/**
 * Author: Michael Leo
 * Date: 2023/7/19 15:02
 */
@Composable
fun MemberCenterScreen(modifier: Modifier = Modifier, navController: NavHostController) {
    Column(modifier.fillMaxSize()) {
        Image(
            painterResource(R.drawable.app_beauty),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            alignment = Alignment.TopStart,
            contentScale = ContentScale.Fit
        )
    }
}

@Preview
@Composable
fun PreviewMemberCenterScreen() {
    MemberCenterScreen(navController = rememberNavController())
}