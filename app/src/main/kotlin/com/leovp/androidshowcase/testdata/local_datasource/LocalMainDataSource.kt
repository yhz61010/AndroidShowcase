package com.leovp.androidshowcase.testdata.local_datasource

import com.leovp.androidshowcase.data.datasource.MainDataSource
import com.leovp.androidshowcase.domain.model.UnreadModel
import javax.inject.Inject

/**
 * Author: Michael Leo
 * Date: 2023/9/4 14:25
 */

class LocalMainDataSource @Inject constructor() : MainDataSource {
    override fun getUnreadList(uid: String): List<UnreadModel> {
        return listOf(
            UnreadModel(UnreadModel.DISCOVERY, (0..<1000).random()),
            UnreadModel(UnreadModel.MY, (0..<1000).random()),
            UnreadModel(UnreadModel.COMMUNITY, (0..<1000).random()),

            UnreadModel(UnreadModel.MESSAGE, (0..<100).random()),
        )
    }
}