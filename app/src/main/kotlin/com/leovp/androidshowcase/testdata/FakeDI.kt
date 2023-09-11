package com.leovp.androidshowcase.testdata

import com.leovp.androidshowcase.testdata.local_datasource.LocalMainDataSource
import com.leovp.androidshowcase.testdata.preview.PreviewMainRepository
import com.leovp.androidshowcase.ui.main.data.MainRepositoryImpl

/**
 * Author: Michael Leo
 * Date: 2023/7/25 10:02
 */
object FakeDI {
    val mainUnreadRepository by lazy {
        MainRepositoryImpl(LocalMainDataSource())
    }

    val previewMainUnreadRepository by lazy {
        PreviewMainRepository(LocalMainDataSource())
    }
}