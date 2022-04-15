package com.kbalazsworks.stackjudge_notification.pushover.services

import com.kbalazsworks.stackjudge_notification.common.services.ApplicationPropertiesService
import com.kbalazsworks.stackjudge_notification.common.services.UserDataApiService
import com.kbalazsworks.stackjudge_notification.push.value_objects.PushToUser
import com.kbalazsworks.stackjudge_notification.pushover.enum.ApiUrls
import io.vertx.core.json.JsonObject
import org.apache.http.HttpStatus
import org.slf4j.LoggerFactory
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class SendPushMessageService(
    private val applicationPropertiesService: ApplicationPropertiesService,
    private val userDataApiService: UserDataApiService
) {
    companion object {
        private val logger = LoggerFactory.getLogger(SendPushMessageService::class.toString())
    }

    fun sendPush(pushToUser: PushToUser) {
        logger.info("Push sent to user#{}", pushToUser.userId)

        val data = JsonObject()
            .put("token", applicationPropertiesService.pushoverAppToken)
            .put("user", userDataApiService.getUserTokenByUserId(pushToUser.userId))
            .put("title", pushToUser.title)
            .put("message", pushToUser.message)
            .toString()

        val http = URL(ApiUrls.SEND_ONE_MESSAGE_TO_USER.value).openConnection() as HttpURLConnection
        http.requestMethod = "POST"
        http.doOutput = true
        http.setRequestProperty("Content-Type", "application/json")

        val stream = http.outputStream
        stream.write(data.toByteArray(StandardCharsets.UTF_8))

        http.disconnect()

        if (http.responseCode !in setOf(HttpStatus.SC_OK, HttpStatus.SC_NO_CONTENT)) {
            logger.error(
                "Push send failed to user#{}; response code: {}; message: {}",
                pushToUser.userId,
                http.responseCode,
                http.responseMessage
            )
        }
    }
}
