package com.kbalazsworks.stackjudge_notification.common.services

import com.fasterxml.jackson.databind.ObjectMapper
import com.kbalazsworks.stackjudge_notification.common.factories.HttpClientFactory
import com.kbalazsworks.stackjudge_notification.main_app.entities.ResponseDataPushoverInfo
import com.kbalazsworks.stackjudge_notification.pushover.exceptions.PushoverException
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.client.methods.HttpGet
import org.apache.http.util.EntityUtils
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class UserDataApiService(
    private val httpClientFactory: HttpClientFactory,
    private val applicationPropertiesService: ApplicationPropertiesService
) {
    @Throws(PushoverException::class)
    fun getUserTokenByUserId(userId: Int): String {
        val request = HttpGet(
            applicationPropertiesService.mainStackjudgeAppHost
                    + "/account/1/pushover/token-by-user-id"
        )
        val nativeResponse: CloseableHttpResponse;
        try {
            nativeResponse = httpClientFactory.build().execute(request)
        } catch (e: Exception) {
            throw PushoverException("Token request error.")
        }

        val response = ObjectMapper().readValue(
            EntityUtils.toString(nativeResponse.entity),
            ResponseDataPushoverInfo::class.java
        )

        return response.data.pushoverUserToken
    }
}
