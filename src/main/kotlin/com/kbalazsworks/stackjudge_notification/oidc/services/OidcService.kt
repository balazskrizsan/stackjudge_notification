package com.kbalazsworks.stackjudge_notification.oidc.services

import com.kbalazsworks.stackjudge_notification.oidc.entities.BasicAuthCredentials
import com.kbalazsworks.stackjudge_notification.oidc.entities.IntrospectRawResponse
import com.kbalazsworks.stackjudge_notification.oidc.entities.JwksKeys
import com.kbalazsworks.stackjudge_notification.oidc.entities.OidcConfig
import com.kbalazsworks.stackjudge_notification.oidc.exceptions.OidcException
import org.apache.http.message.BasicNameValuePair

class OidcService(
    private val oidcConfig: OidcConfig,
    private val oidcHttpClientService: OidcHttpClientService
) {
    fun callJwksEndpoint(): JwksKeys {
        return oidcHttpClientService.getWithMap(
            oidcConfig.jwksUri,
            JwksKeys::class.java
        )
    }

    fun isValidToken(token: String, basicAuth: BasicAuthCredentials): Boolean {
        return oidcHttpClientService.postWithMapAndBasicAuth(
            oidcConfig.introspectionEndpoint,
            mutableListOf(BasicNameValuePair("token", token)),
            basicAuth,
            IntrospectRawResponse::class.java
        ).active
    }

    fun validateToken(token: String, basicAuth: BasicAuthCredentials) {
        val cleanToken = token.replace("Bearer ", "");
        if (!isValidToken(cleanToken, basicAuth)) {
            throw OidcException("Invalid token")
        }
    }
}
