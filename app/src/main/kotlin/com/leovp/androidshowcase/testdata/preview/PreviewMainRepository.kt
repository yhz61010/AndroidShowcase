package com.leovp.androidshowcase.testdata.preview

import com.leovp.androidshowcase.testdata.local_datasource.LocalMainDataSource
import com.leovp.androidshowcase.ui.main.UnreadModel
import com.leovp.androidshowcase.ui.main.data.MainRepository
import com.leovp.module.common.Result

/**
  * Author: Michael Leo
  * Date: 2023/9/4 14:28
  */
class PreviewMainRepository(private val dataSource: LocalMainDataSource): MainRepository {

    override suspend fun getUnreadList(uid: String): Result<List<UnreadModel>> {
        return Result.Success(dataSource.unreadList)
    }
}