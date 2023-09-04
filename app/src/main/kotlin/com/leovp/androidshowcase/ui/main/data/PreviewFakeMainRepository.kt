package com.leovp.androidshowcase.ui.main.data

import com.leovp.androidshowcase.ui.main.UnreadModel
import com.leovp.module.common.Result

/**
  * Author: Michael Leo
  * Date: 2023/9/4 14:28
  */
class PreviewFakeMainRepository(private val dataSource: MainLocalDataSource): MainRepository {

    override suspend fun getUnreadList(uid: String): Result<List<UnreadModel>> {
        return Result.Success(dataSource.unreadList)
    }
}