package com.kbalazsworks.stackjudge_notification.main_app.entities

import com.fasterxml.jackson.annotation.JsonProperty

data class ResponseDataPushoverInfo(
    @JsonProperty("data")
    var data: PushoverInfo,
    @JsonProperty("success")
    var success: Boolean,
    @JsonProperty("errorCode")
    var errorCode: Int,
    @JsonProperty("requestId")
    var requestId: String
)
