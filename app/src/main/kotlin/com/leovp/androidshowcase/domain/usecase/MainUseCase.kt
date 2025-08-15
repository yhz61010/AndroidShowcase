package com.leovp.androidshowcase.domain.usecase

import com.leovp.androidshowcase.domain.model.UnreadModel
import com.leovp.androidshowcase.domain.repository.MainRepository
import com.leovp.network.http.Result
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Author: Michael Leo
 * Date: 2023/9/12 13:36
 */
@Singleton
class MainUseCase @Inject constructor(
    /*@MainRepositoryImplement*/ private val repository: MainRepository
) {
    suspend fun getUnreadList(uid: String): Result<List<UnreadModel>> =
        repository.getUnreadList(uid)
}
