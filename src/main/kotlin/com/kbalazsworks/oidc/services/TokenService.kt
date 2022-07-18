package com.kbalazsworks.oidc.services

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.kbalazsworks.oidc.entities.JwtData
import com.kbalazsworks.oidc.exceptions.OidcException
import com.kbalazsworks.oidc.exceptions.OidcJwtParseException
import org.slf4j.LoggerFactory
import java.math.BigInteger
import java.security.KeyFactory
import java.security.PublicKey
import java.security.Signature
import java.security.spec.RSAPublicKeySpec
import java.util.*
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class TokenService {
    companion object {
        private val logger = LoggerFactory.getLogger(OidcService::class.toString())
    }

    private val objectMapper = ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

    fun getPublicKey(modulus: String, exponent: String): PublicKey {
        try {
            val exponentB = Base64.getUrlDecoder().decode(exponent)
            val modulusB = Base64.getUrlDecoder().decode(modulus)
            val bigExponent = BigInteger(1, exponentB)
            val bigModulus = BigInteger(1, modulusB)
            val publicKey = KeyFactory.getInstance("RSA").generatePublic(RSAPublicKeySpec(bigModulus, bigExponent));

            return publicKey
        } catch (e: Exception) {
            when(e) {
                is IllegalAccessException, is IndexOutOfBoundsException -> {
                    logger.error("Public key error: {}", e.message)

                    throw OidcException("Public key error")
                }
                else -> throw e
            }
        }
    }

    fun getSignedData(token: String): ByteArray {
        try {
            return token.substring(0, token.lastIndexOf(".")).toByteArray()
        } catch (e: Exception) {
            throw OidcJwtParseException(e.message ?: "")
        }
    }

    fun getJwtData(token: String): JwtData {
        try {
            val tokenParts = token.split(".")
            val dataPart = tokenParts[1].toByteArray()
            val decodedJwtData = Base64.getDecoder().decode(dataPart).decodeToString()

            return objectMapper.readValue(decodedJwtData, JwtData::class.java)
        } catch (e: Exception) {
            throw OidcJwtParseException(e.message ?: "")
        }
    }

    fun getSignature(token: String): ByteArray {
        try {
            val signatureB64u = token.substring(token.lastIndexOf(".") + 1, token.length)

            return Base64.getUrlDecoder().decode(signatureB64u)
        } catch (e: Exception) {
            throw OidcJwtParseException(e.message ?: "")
        }
    }

    fun isVerified(publicKey: PublicKey, signedData: ByteArray, signature: ByteArray): Boolean {
        val sig = Signature.getInstance("SHA256withRSA")
        sig.initVerify(publicKey)
        sig.update(signedData)

        return sig.verify(signature)
    }
}
