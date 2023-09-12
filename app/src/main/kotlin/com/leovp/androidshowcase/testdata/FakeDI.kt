package com.leovp.androidshowcase.testdata

import com.leovp.androidshowcase.domain.usecase.MainUseCase
import com.leovp.androidshowcase.testdata.local_datasource.LocalMainDataSource
import com.leovp.androidshowcase.testdata.preview.PreviewMainRepository

/**
 * Author: Michael Leo
 * Date: 2023/7/25 10:02
 */
object FakeDI {
    val previewMainUseCase by lazy {
        MainUseCase(PreviewMainRepository(LocalMainDataSource()))
    }
}