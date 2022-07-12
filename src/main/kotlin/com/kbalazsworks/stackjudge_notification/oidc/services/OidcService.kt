package com.kbalazsworks.stackjudge_notification.oidc.services

import com.kbalazsworks.stackjudge_notification.common.factories.SystemFactory
import com.kbalazsworks.stackjudge_notification.oidc.entities.JwksKeys
import com.kbalazsworks.stackjudge_notification.oidc.entities.OidcConfig
import com.kbalazsworks.stackjudge_notification.oidc.exceptions.OidcException
import org.slf4j.LoggerFactory

class OidcService(
    private val oidcConfig: OidcConfig,
    private val oidcHttpClientService: OidcHttpClientService,
    private val tokenService: TokenService,
    private val systemFactory: SystemFactory,
) : IOidcService {
    companion object {
        private val logger = LoggerFactory.getLogger(OidcService::class.toString())
    }

    override fun callJwksEndpoint(): JwksKeys {
        return oidcHttpClientService.getWithMap(
            oidcConfig.jwksUri,
            JwksKeys::class.java
        )
    }

    override fun isJwksVerifiedToken(token: String): Boolean {
        val oneKey = callJwksEndpoint().keys[0]
        val publicKey = tokenService.getPublicKey(oneKey.n, oneKey.e)
        val signature = tokenService.getSignature(token)
        val signedData = tokenService.getSignedData(token)

        return try {
            tokenService.isVerified(publicKey, signedData, signature)
        } catch (e: Exception) {
            logger.error("JWKS verification failed: {}", e.message)

            false
        }
    }

    override fun checkJwksVerifiedToken(token: String) {
        if (!isJwksVerifiedToken(token)) {
            throw OidcException("JWKS verification error")
        }
    }

    override fun isExpiredToken(token: String): Boolean {
        val expiration = tokenService.getJwtData(token).exp
        val now = systemFactory.getCurrentTimeMillis() / 1000

        return expiration < now
    }

    override fun checkExpiredToken(token: String) {
        if (isExpiredToken(token)) {
            throw OidcException("Expired token")
        }
    }

    override fun checkValidated(token: String) {
        checkExpiredToken(token)
        checkJwksVerifiedToken(token)
    }

    override fun hasScopesInToken(token: String, scopes: List<String>): Boolean {
        return tokenService.getJwtData(token).scope.containsAll(scopes)
    }

    override fun checkScopesInToken(token: String, scopes: List<String>) {
        if (!tokenService.getJwtData(token).scope.containsAll(scopes)) {
            throw OidcException("Scope missing from token")
        }
    }

//    fun isValidBefore() {
//    }

//    fun isIntrospectionValidToken(token: String, basicAuth: BasicAuthCredentials): Boolean {
//        return oidcHttpClientService.postWithMapAndBasicAuth(
//            oidcConfig.introspectionEndpoint,
//            mutableListOf(BasicNameValuePair("token", token)),
//            basicAuth,
//            IntrospectRawResponse::class.java
//        ).active
//    }

//    fun introspectionValidateToken(token: String, basicAuth: BasicAuthCredentials) {
//        val cleanToken = token.replace("Bearer ", "");
//
//        checkJwksVerifiedToken(token)
//
//        if (!isIntrospectionValidToken(cleanToken, basicAuth)) {
//            throw OidcException("Invalid token by introspection endpoint")
//        }
//    }
}
