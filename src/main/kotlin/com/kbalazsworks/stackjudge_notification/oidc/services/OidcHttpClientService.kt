package com.kbalazsworks.stackjudge_notification.oidc.services

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.kbalazsworks.stackjudge_notification.common.factories.HttpClientFactory
import com.kbalazsworks.stackjudge_notification.oidc.entities.BasicAuthCredentials
import com.kbalazsworks.stackjudge_notification.oidc.exceptions.OidcException
import org.apache.http.NameValuePair
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.util.EntityUtils
import java.util.*
import javax.enterprise.context.ApplicationScoped


@ApplicationScoped
class OidcHttpClientService(private val httpClientFactory: HttpClientFactory) {

    private val objectMapper = ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

    fun <T> getWithMap(url: String, mapperClass: Class<T>): T {
        try {
            val httpGet = HttpGet(url)
            val nativeResponse: CloseableHttpResponse;
            try {
                nativeResponse = httpClientFactory.build().execute(httpGet)
            } catch (e: Exception) {
                throw OidcException("OIDC call Error")
            }

            return objectMapper.readValue(EntityUtils.toString(nativeResponse.entity), mapperClass)
        } catch (e: Exception) {
            throw Exception("error: " + e.message)
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
