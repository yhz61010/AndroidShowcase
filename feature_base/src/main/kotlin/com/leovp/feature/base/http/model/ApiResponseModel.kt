package com.leovp.feature.base.http.model

import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import com.leovp.json.toJsonString
import com.leovp.log.base.d
import com.leovp.log.base.e
import com.leovp.log.base.w
import com.leovp.network.http.Result
import com.leovp.network.http.ResultBiz
import com.leovp.network.http.exception.ResultConvertException
import com.leovp.network.http.exception.business.BusinessException
import com.leovp.network.http.exception.business.DataNotFoundException
import com.leovp.network.http.exception.business.EmptyResponseException
import com.leovp.network.http.exception.business.ReloginException
import com.leovp.network.http.getOrNull
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Date: 2023/9/15 14:05
 * Author: Michael Leo
 */

@Keep
@Serializable
@OptIn(InternalSerializationApi::class)
@Immutable
open class ApiResponseModel<T>(
    @SerialName("code") val code: Int = 0,
    @SerialName("message") val message: String? = null,
    val result: T? = null,
) {
    open fun isBizSuccess(): Boolean = code == 200

    open fun shouldRelogin(): Boolean = isReloginCode(code)

    fun getBizErrorPair(): Pair<Int, String>? =
        if (this.isBizSuccess()) {
            null
        } else {
            Pair(this.code, this.message ?: "[Empty message]")
        }

    companion object {
        private const val TAG = "APR"

        /**
         * Attention: Business errors will also be regarded as exceptions.
         *
         * Attention: For returned value `ApiResponseModel`,
         * when `success` is `true` but `result` is `null`,
         * the type defaults to String.
         *
         * Only api response result is real successful which means no http error,
         * no business error, the `Result.Success` will be returned.
         * Otherwise, return the specific exception wrapped by `Result.Failure`.
         *
         * @return responseApiResult The type should be `Result<ApiResponseModel<T?>?>`
         *
         * @return If the returned result is `Result.Failure`,
         * it can be an instance of the following subclass:
         * - `DataNotFoundException`
         * - `EmptyResponseException`
         * - base `ResultException`
         * The request exception:
         * - `ResultResponseException`
         * - `ResultServerException`
         * - `ResultConvertException`
         * - `DataNotFoundException`
         */
        fun <T, R : ApiResponseModel<T>> processApiResponseResult(
            responseApiResult: Result<R>,
        ): ResultBiz<T> =
            when (responseApiResult) {
                // The network encounters exception
                is Result.Failure -> handleHttpFailure(responseApiResult)

                // Successfully receive network result
                is Result.Success -> handleApiResponseSuccess(responseApiResult)
            }

        private fun <T, R : ApiResponseModel<T>> handleHttpFailure(
            responseApiResult: Result.Failure,
        ): ResultBiz<T> {
            e(TAG, responseApiResult.exception) {
                val ex = responseApiResult.exception
                val rawRespString =
                    if (ex is ResultConvertException) {
                        ex.responseBodyString ?: ""
                    } else {
                        ""
                    }
                val extraRaw =
                    if (rawRespString.isBlank()) {
                        ""
                    } else {
                        ". Raw: $rawRespString"
                    }
                "Http exception$extraRaw"
            }
            return ResultBiz.Failure(responseApiResult.exception)
        }

        private fun <T, R : ApiResponseModel<T>> handleApiResponseSuccess(
            responseApiResult: Result.Success<R>,
        ): ResultBiz<T> {
            // Get `ApiResponseModel` api result not the real data result.
            val responseDataResult = responseApiResult.getOrNull()
            d {
                tag = TAG
                fullOutput = true
                block =
                    { "-----> Response=${responseDataResult?.toJsonString()}" }
            }

            return when {
                // This means `ApiResponseModel` itself is null,
                // not the real data is null.
                responseDataResult == null -> {
                    w(TAG) { "Response is failure." }
                    ResultBiz.Failure(EmptyResponseException())
                }

                !responseDataResult.isBizSuccess() -> {
                    val bizErr =
                        checkNotNull(getOnlyBizException(responseDataResult))
                    w(TAG) { "Found business error. BizErr=$bizErr" }

                    // val needRelogin = responseDataResult.shouldRelogin()
                    if (bizErr is ReloginException) {
                        ResultBiz.Relogin(
                            exception = bizErr,
                            data = responseDataResult.result,
                        )
                    } else {
                        ResultBiz.BusinessError(
                            exception = bizErr,
                            data = responseDataResult.result,
                        )
                    }
                }

                responseDataResult.result == null -> {
                    if (responseDataResult.isBizSuccess()) {
                        // For APIs that only need success status without data (e.g., delete, update)
                        // Return Unit as the data placeholder
                        d(TAG) {
                            "Response result is null but business success. " +
                                "Returning Unit as placeholder."
                        }
                        @Suppress("UNCHECKED_CAST")
                        (ResultBiz.Success(data = Unit) as ResultBiz<T>)
                    } else {
                        w(TAG) {
                            "Response result property is null. " +
                                "Response=${responseDataResult.toJsonString()}"
                        }
                        ResultBiz.Failure(DataNotFoundException())
                    }
                }

                else -> {
                    val dataResult = responseDataResult.result
                    // val pagination = responseDataResult.pagination
                    ResultBiz.Success(data = dataResult, extraData = null)
                }
            }
        }

        @Suppress("unused")
        fun areAllBizSuccess(vararg apiResults: Result<ApiResponseModel<*>>): Boolean {
            val hasError = apiResults.any { it.getOrNull()?.isBizSuccess() != true }
            return !hasError
        }

        /**
         * @return Only if the parameters contain any BUSINESS error,
         * the first business exception will be returned.
         * If there is no any BUSINESS error or the result of response is `null`,
         * the returned value will be `null`.
         * The returned exception can be an instance of the following subclass:
         * - `ReloginException`
         * - `BusinessException`
         */
        fun getOnlyBizException(
            vararg responseResultList: ApiResponseModel<*>?,
        ): BusinessException? {
            val businessError =
                responseResultList.firstNotNullOfOrNull { it?.getBizErrorPair() }
            if (businessError == null) return null
            val shouldReloginResp =
                responseResultList.firstOrNull { it?.shouldRelogin() == true }

            return if (shouldReloginResp != null) {
                ReloginException(
                    code = businessError.first.toString(),
                    message = businessError.second,
                )
            } else {
                // Other business error code
                BusinessException(
                    code = businessError.first.toString(),
                    message = businessError.second,
                )
            }
        }

        fun isReloginCode(code: Int): Boolean = false
    }
}
