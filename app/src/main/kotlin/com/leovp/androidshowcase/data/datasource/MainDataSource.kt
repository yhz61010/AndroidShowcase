package com.leovp.androidshowcase.data.datasource

import com.leovp.androidshowcase.domain.model.UnreadModel

/**
 * Author: Michael Leo
 * Date: 2023/9/12 13:41
 */
interface MainDataSource {
    fun getUnreadList(uid: String): List<UnreadModel>
}
