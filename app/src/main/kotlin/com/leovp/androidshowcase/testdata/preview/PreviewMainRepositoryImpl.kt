package com.leovp.androidshowcase.testdata.preview

import com.leovp.androidshowcase.domain.model.UnreadModel
import com.leovp.androidshowcase.domain.repository.MainRepository
import com.leovp.androidshowcase.testdata.local.datasource.LocalMainDataSource
import com.leovp.network.http.Result
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
        override suspend fun getUnreadList(uid: String): Result<List<UnreadModel>> =
            Result.Success(dataSource.getUnreadList(uid))
    }
