package com.leovp.feature_discovery.data.datasource.api.model

import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import com.leovp.feature_discovery.domain.model.BannerModel
import com.leovp.feature_discovery.domain.model.HomePageBlockModel
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable

/**
  * Author: Michael Leo
  * Date: 2024/9/12 10:04
  */

@Keep
@Immutable
@OptIn(InternalSerializationApi::class)
@Serializable
data class HomePageBlockApiModel(
    val cursor: String?,
    val blocks: List<BlockApiModel>,
    val hasMore: Boolean,
    val blockUUIDs:String?,
    val pageConfig: PageConfigApiModel,
    val internalTest: String?,
    val titles: List<String>,
    val blockCodeOrderList: String?,
    val exposedResource: List<String>,
    val demote: Boolean,
)

fun HomePageBlockApiModel.toDomainModel(): HomePageBlockModel {
    return HomePageBlockModel(
        banners = this.blocks.flatMap { block ->
            block.extInfo.banners.map { banner ->
                banner.toDomainModel()
            }
        }
    )
}

@Keep
@Immutable
@OptIn(InternalSerializationApi::class)
@Serializable
data class PageConfigApiModel(
    val refreshToast: String,
    val nodataToast: String,
    val refreshInterval: Long,
    val title: String?,
    val fullscreen: Boolean,
    val songLabelMarkPriority: List<String>,
    val songLabelMarkLimit: Int,
    val homepageMode: String,
    val showModeEntry: Boolean,
    val orderInfo: String,
)

@Keep
@Immutable
@OptIn(InternalSerializationApi::class)
@Serializable
data class BlockApiModel(
    val blockCode: String,
    val showType: String,
    val extInfo: ExtInfoApiModel,
    val canClose: Boolean,
    val blockStyle: Int,
    val canFeedback: Boolean,
    val blockDemote: String,
    val sort: Int
)

@Keep
@Immutable
@OptIn(InternalSerializationApi::class)
@Serializable
data class ExtInfoApiModel(val banners: List<BannerApiModel>)

@Keep
@Immutable
@OptIn(InternalSerializationApi::class)
@Serializable
data class BannerApiModel(
    val bannerId: String,
    val targetId: Long,
    val pic: String,
    val mainTitle: String?,
    val targetType: Long,
    val titleColor: String,
    val url: String?,
    val typeTitle: String,
    val alg: String,
    val scm: String,
    val showAdTag: Boolean,
    val bannerBizType: String,
)

fun BannerApiModel.toDomainModel(): BannerModel {
    return BannerModel(
        bannerId = this.bannerId,
        pic = this.pic,
        url = this.url,
        titleColor = this.titleColor,
        typeTitle = this.typeTitle,
    )
}