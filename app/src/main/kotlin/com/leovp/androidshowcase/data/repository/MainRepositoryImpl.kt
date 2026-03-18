package com.leovp.androidshowcase.data.repository

import com.leovp.androidshowcase.data.datasource.MainDataSource
import com.leovp.androidshowcase.domain.model.UnreadModel
import com.leovp.androidshowcase.domain.repository.MainRepository
import com.leovp.feature.base.http.model.ApiResponseModel
import com.leovp.network.http.Result
import com.leovp.network.http.net.result
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Author: Michael Leo
 * Date: 2023/9/4 14:24
 */

@Singleton
class MainRepositoryImpl
@Inject
constructor(
    private val dataSource: MainDataSource,
) : MainRepository {
    override suspend fun getUnreadList(uid: String): Result<ApiResponseModel<List<UnreadModel>>> =
        result {
            delay(1000)
            ApiResponseModel(
                code = 200,
                message = "",
                result = dataSource.getUnreadList("1")
            )
        }
}
