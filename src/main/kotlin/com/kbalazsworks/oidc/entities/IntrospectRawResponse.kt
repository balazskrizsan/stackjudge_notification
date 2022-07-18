package com.kbalazsworks.oidc.entities

import com.fasterxml.jackson.annotation.JsonProperty

data class IntrospectRawResponse(
    @JsonProperty("active")
    val active: Boolean = false,

    @JsonProperty("iss")
    val iss: String? = null,

    @JsonProperty("nbf")
    val nbf: Int? = null,

    @JsonProperty("iat")
    val iat: Int? = null,

    @JsonProperty("exp")
    val exp: Int? = null,

    @JsonProperty("aud")
    val aud: List<String>? = null,

    @JsonProperty("client_id")
    val clientId: String? = null,

    @JsonProperty("jti")
    val jti: String? = null,

    @JsonProperty("scope")
    val scope: String? = null,
)
