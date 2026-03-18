package com.leovp.androidshowcase.testdata.local.datasource

import com.leovp.androidshowcase.data.datasource.MainDataSource
import com.leovp.androidshowcase.domain.model.UnreadModel
import com.leovp.feature.base.ui.Screen
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Author: Michael Leo
 * Date: 2023/9/4 14:25
 */

@Singleton
class LocalMainDataSource
    @Inject
    constructor() : MainDataSource {
        override fun getUnreadList(uid: String): List<UnreadModel> =
            listOf(
                UnreadModel(Screen.Discovery, (0..<1000).random()),
                UnreadModel(Screen.My, (0..<1000).random()),
                UnreadModel(Screen.Community, (0..<1000).random()),
                UnreadModel(Screen.MemberCenter, (0..<100).random()),
            )
    }
