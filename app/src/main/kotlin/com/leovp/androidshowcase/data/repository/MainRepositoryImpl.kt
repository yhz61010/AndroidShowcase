package com.leovp.androidshowcase.data.repository

import com.leovp.androidshowcase.data.datasource.MainDataSource
import com.leovp.androidshowcase.domain.model.UnreadModel
import com.leovp.androidshowcase.domain.repository.MainRepository
import com.leovp.module.common.Result
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Author: Michael Leo
 * Date: 2023/9/4 14:24
 */

@Singleton
class MainRepositoryImpl @Inject constructor(
    private val dataSource: MainDataSource
) : MainRepository {

    override suspend fun getUnreadList(uid: String): Result<List<UnreadModel>> {
        delay(1000)
        return Result.Success(dataSource.getUnreadList("1"))
    }
}