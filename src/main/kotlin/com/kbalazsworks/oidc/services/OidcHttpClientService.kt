package com.kbalazsworks.oidc.services

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.kbalazsworks.stackjudge_notification.common.factories.HttpClientFactory
import com.kbalazsworks.oidc.entities.BasicAuthCredentials
import com.kbalazsworks.oidc.exceptions.OidcException
import org.apache.http.NameValuePair
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.util.EntityUtils
import org.slf4j.LoggerFactory
import java.util.*
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class OidcHttpClientService(private val httpClientFactory: HttpClientFactory) {
    companion object {
        private val logger = LoggerFactory.getLogger(OidcService::class.toString())
    }

    private val objectMapper = ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

    fun <T> getWithMap(url: String, mapperClass: Class<T>): T {
        try {
            val nativeResponse = httpClientFactory.build().execute(HttpGet(url))

            return objectMapper.readValue(EntityUtils.toString(nativeResponse.entity), mapperClass)
        } catch (e: Exception) {
            logger.error("OIDC call error: {}", e.message)

            throw OidcException("OIDC call Error")
        }
    }

    fun <T> postWithMapAndBasicAuth(
        url: String,
        params: MutableList<NameValuePair>,
        basicAuth: BasicAuthCredentials,
        mapperClass: Class<T>
    ): T {
        try {
            val basicAuthString = (basicAuth.userName + ":" + basicAuth.password).toByteArray();
            val authHeader = "Basic " + Base64.getEncoder().encodeToString(basicAuthString)

            val httpPost = HttpPost(url)
            httpPost.setHeader("Authorization", authHeader)
            httpPost.entity = UrlEncodedFormEntity(params);

            val nativeResponse: CloseableHttpResponse;
            try {
                nativeResponse = httpClientFactory.build().execute(httpPost)
            } catch (e: Exception) {
                throw OidcException("OIDC call Error")
            }

            return objectMapper.readValue(EntityUtils.toString(nativeResponse.entity), mapperClass)
        } catch (e: Exception) {
            throw Exception("error: " + e.message)
        }
    }
}
