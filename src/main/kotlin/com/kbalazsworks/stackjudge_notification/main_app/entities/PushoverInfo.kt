package com.kbalazsworks.stackjudge_notification.main_app.entities

import com.fasterxml.jackson.annotation.JsonProperty

data class PushoverInfo(
    @JsonProperty("userId")
    var userId: Long,
    @JsonProperty("pushoverUserToken")
    var pushoverUserToken: String
)
