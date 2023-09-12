package com.leovp.androidshowcase.domain.repository

import com.leovp.androidshowcase.domain.model.UnreadModel
import com.leovp.module.common.Result

interface MainRepository {
    suspend fun getUnreadList(uid: String): Result<List<UnreadModel>>
}
