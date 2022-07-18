package com.kbalazsworks.oidc.entities

import com.fasterxml.jackson.annotation.JsonProperty

data class OidcConfig(
    @JsonProperty("issuer")
    val issuer: String? = null,

    @JsonProperty("jwks_uri")
    val jwksUri: String,

    @JsonProperty("authorization_endpoint")
    val authorizationEndpoint: String? = null,

    @JsonProperty("token_endpoint")
    val tokenEndpoint: String? = null,

    @JsonProperty("userinfo_endpoint")
    val userinfoEndpoint: String? = null,

    @JsonProperty("end_session_endpoint")
    val endSessionEndpoint: String? = null,

    @JsonProperty("check_session_iframe")
    val checkSessionIframe: String? = null,

    @JsonProperty("revocation_endpoint")
    val revocationEndpoint: String? = null,

    @JsonProperty("introspection_endpoint")
    val introspectionEndpoint: String,

    @JsonProperty("device_authorization_endpoint")
    val deviceAuthorizationEndpoint: String? = null,

    @JsonProperty("backchannel_authentication_endpoint")
    val backchannelAuthenticationEndpoint: String? = null,
)
