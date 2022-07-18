package com.kbalazsworks.oidc.entities

import com.fasterxml.jackson.annotation.JsonProperty

data class JwtData(
    @JsonProperty("iss")
    val iss: String,
    @JsonProperty("nbf")
    val nbf: Int,
    @JsonProperty("iat")
    val iat: Int,
    @JsonProperty("exp")
    val exp: Int,
    @JsonProperty("aud")
    val aud: List<String>,
    @JsonProperty("scope")
    val scope: List<String>,
    @JsonProperty("client_id")
    val clientId: String,
    @JsonProperty("jti")
    val jti: String,
)
