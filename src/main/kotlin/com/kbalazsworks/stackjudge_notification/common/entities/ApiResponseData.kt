package com.kbalazsworks.stackjudge_notification.common.entities

data class ApiResponseData<T> (
    var data: T? = null,
    var success: Boolean,
    var errorCode: Int,
    var requestId: String
)
