package com.leovp.androidshowcase.testdata.preview

import com.leovp.androidshowcase.domain.model.UnreadModel
import com.leovp.androidshowcase.domain.repository.MainRepository
import com.leovp.androidshowcase.testdata.local.datasource.LocalMainDataSource
import com.leovp.feature.base.http.model.ApiResponseModel
import com.leovp.network.http.Result
import com.leovp.network.http.net.result
import javax.inject.Inject

/**
 * Author: Michael Leo
 * Date: 2023/9/4 14:28
 */
class PreviewMainRepositoryImpl
@Inject
constructor(
    private val dataSource: LocalMainDataSource,
) : MainRepository {
    override suspend fun getUnreadList(uid: String): Result<ApiResponseModel<List<UnreadModel>>> =
        result {
            ApiResponseModel(
                code = 200,
                message = "",
                result = dataSource.getUnreadList(uid)
            )
        }
}
