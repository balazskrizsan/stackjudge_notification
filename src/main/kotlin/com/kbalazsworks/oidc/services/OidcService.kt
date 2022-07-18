package com.kbalazsworks.oidc.services

import com.kbalazsworks.oidc.entities.JwksKeys
import com.kbalazsworks.oidc.entities.OidcConfig
import com.kbalazsworks.oidc.exceptions.OidcException
import com.kbalazsworks.oidc.exceptions.OidcExpiredTokenException
import com.kbalazsworks.oidc.exceptions.OidcJwksVerificationException
import com.kbalazsworks.stackjudge_notification.common.factories.SystemFactory
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
        return try {
            checkJwksVerifiedTokenLogic(token)
        } catch (e: Exception) {
            logger.error("JWKS verification failed: {}", e.message)

            false
        }
    }

    override fun checkJwksVerifiedToken(token: String) {
        val isVerified: Boolean
        try {
            isVerified = checkJwksVerifiedTokenLogic(token)
        } catch (e: Exception) {
            throw OidcJwksVerificationException(e.message ?: "")
        }

        if (!isVerified) {
            throw OidcJwksVerificationException()
        }
    }

    private fun checkJwksVerifiedTokenLogic(token: String): Boolean {
        val oneKey = callJwksEndpoint().keys[0]
        val publicKey = tokenService.getPublicKey(oneKey.n, oneKey.e)
        val signature = tokenService.getSignature(token)
        val signedData = tokenService.getSignedData(token)

        return tokenService.isVerified(publicKey, signedData, signature)
    }

    override fun isExpiredToken(token: String): Boolean {
        val expiration = tokenService.getJwtData(token).exp
        val now = systemFactory.getCurrentTimeMillis() / 1000

        return expiration < now
    }

    override fun checkExpiredToken(token: String) {
        if (isExpiredToken(token)) {
            throw OidcExpiredTokenException()
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
        checkValidated(token)

        val matchedScopes = tokenService.getJwtData(token).scope.stream().filter { s -> scopes.contains(s) }

        if (matchedScopes.count() == 0L) {
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
