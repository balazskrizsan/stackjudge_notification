package com.kbalazsworks.oidc.entities

import com.fasterxml.jackson.annotation.JsonProperty

data class JwksKeyItem (
    @JsonProperty("kty")
    val kty: String,

    @JsonProperty("use")
    val use: String,

    @JsonProperty("kid")
    val kid: String,

    @JsonProperty("e")
    val e: String,

    @JsonProperty("n")
    val n: String,

    @JsonProperty("alg")
    val alg: String,
)
