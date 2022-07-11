package com.kbalazsworks.stackjudge_notification.oidc.services

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.kbalazsworks.stackjudge_notification.oidc.entities.JwtData
import com.kbalazsworks.stackjudge_notification.oidc.exceptions.OidcException
import java.math.BigInteger
import java.security.KeyFactory
import java.security.NoSuchAlgorithmException
import java.security.PublicKey
import java.security.Signature
import java.security.spec.InvalidKeySpecException
import java.security.spec.RSAPublicKeySpec
import java.util.*
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class TokenService {
    private val objectMapper = ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

    fun getPublicKey(modulus: String, exponent: String): PublicKey {
        try {
            val exponentB = Base64.getUrlDecoder().decode(exponent)
            val modulusB = Base64.getUrlDecoder().decode(modulus)
            val bigExponent = BigInteger(1, exponentB)
            val bigModulus = BigInteger(1, modulusB)
            val publicKey = KeyFactory.getInstance("RSA").generatePublic(RSAPublicKeySpec(bigModulus, bigExponent));

            return publicKey;
        } catch (e: InvalidKeySpecException) {
            throw OidcException("Public key error: " + e.message)
        } catch (e: NoSuchAlgorithmException) {
            throw OidcException("Public key error: " + e.message)
        }
    }

    fun getSignedData(token: String): ByteArray {
        return token.substring(0, token.lastIndexOf(".")).toByteArray()
    }

    fun getJwtData(token: String): JwtData {
        val tokenParts = token.split(".")
        val dataPart = tokenParts[1].toByteArray()
        val decodedJwtData = Base64.getDecoder().decode(dataPart).decodeToString()

        return objectMapper.readValue(decodedJwtData, JwtData::class.java)
    }

    fun getSignature(token: String): ByteArray {
        val signatureB64u = token.substring(token.lastIndexOf(".") + 1, token.length)

        return Base64.getUrlDecoder().decode(signatureB64u)
    }

    fun isVerified(publicKey: PublicKey, signedData: ByteArray, signature: ByteArray): Boolean {
        val sig = Signature.getInstance("SHA256withRSA")
        sig.initVerify(publicKey)
        sig.update(signedData)

        return sig.verify(signature)
    }
}
