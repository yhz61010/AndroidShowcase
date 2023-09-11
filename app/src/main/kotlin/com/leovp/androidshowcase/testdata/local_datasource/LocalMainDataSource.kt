package com.leovp.androidshowcase.testdata.local_datasource

import com.leovp.androidshowcase.ui.main.UnreadModel

/**
 * Author: Michael Leo
 * Date: 2023/9/4 14:25
 */
class LocalMainDataSource {
    val unreadList: List<UnreadModel>
        get() = listOf(
            UnreadModel(UnreadModel.DISCOVERY, (0..<1000).random()),
            UnreadModel(UnreadModel.MY, (0..<1000).random()),
            UnreadModel(UnreadModel.COMMUNITY, (0..<1000).random()),

            UnreadModel(UnreadModel.MESSAGE, (0..<100).random()),
        )
}