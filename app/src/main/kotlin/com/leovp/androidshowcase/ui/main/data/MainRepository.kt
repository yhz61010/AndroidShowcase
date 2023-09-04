package com.leovp.androidshowcase.ui.main.data

import com.leovp.androidshowcase.ui.main.UnreadModel
import com.leovp.module.common.Result

interface MainRepository {
    suspend fun getUnreadList(uid: String): Result<List<UnreadModel>>
}
