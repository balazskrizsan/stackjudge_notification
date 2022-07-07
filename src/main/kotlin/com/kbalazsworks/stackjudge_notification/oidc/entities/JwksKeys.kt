package com.kbalazsworks.stackjudge_notification.oidc.entities

import com.fasterxml.jackson.annotation.JsonProperty

data class JwksKeys (
    @JsonProperty("keys")
    val keys: List<JwksKeyItem>
)
