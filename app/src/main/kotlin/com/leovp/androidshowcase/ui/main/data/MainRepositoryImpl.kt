package com.leovp.androidshowcase.ui.main.data

import com.leovp.androidshowcase.testdata.local_datasource.LocalMainDataSource
import com.leovp.androidshowcase.ui.main.UnreadModel
import com.leovp.module.common.Result
import kotlinx.coroutines.delay

/**
 * Author: Michael Leo
 * Date: 2023/9/4 14:24
 */
class MainRepositoryImpl(private val dataSource: LocalMainDataSource): MainRepository {

    override suspend fun getUnreadList(uid: String): Result<List<UnreadModel>> {
        delay(1000)
        return Result.Success(dataSource.unreadList)
    }
}