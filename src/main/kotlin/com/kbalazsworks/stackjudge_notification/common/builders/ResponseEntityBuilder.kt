//package com.kbalazsworks.stackjudge_notification.common.builders
//
//import com.kbalazsworks.stackjudge_notification.common.entities.ApiResponseData
//import org.apache.http.HttpStatus
//
//class ResponseEntityBuilder<T> {
//    private var data: T? = null
//    private var errorCode: Int = 0
//    private var statusCode: Int = HttpStatus.SC_OK
////    private var headers: HttpHeaders = HttpHeadersImpl(emptyList()) @todo: implement
//
//    fun data(data: T): ResponseEntityBuilder<T> {
//        this.data = data
//
//        return this
//    }
//
//    fun errorCode(errorCode: Int): ResponseEntityBuilder<T> {
//        this.errorCode = errorCode
//
//        return this
//    }
//
//    fun statusCode(statusCode: Int): ResponseEntityBuilder<T> {
//        this.statusCode = statusCode
//
//        return this
//    }
//
//    fun build(): ApiResponseData<T> {
//        val success = errorCode == 0
//        if (errorCode > 0 && statusCode == HttpStatus.SC_OK) {
////            throw ApiException("Status code setup is needed for error response") @todo: implement
//        }
//
//        return ApiResponseData(data, success, errorCode, "1")
//    }
//}
