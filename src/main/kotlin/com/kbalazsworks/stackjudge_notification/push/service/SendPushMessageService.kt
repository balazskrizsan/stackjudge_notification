package com.kbalazsworks.stackjudge_notification.push.service

import com.kbalazsworks.stackjudge_notification.push.value_objects.PushToUser
import com.kbalazsworks.stackjudge_notification.pushover.services.SendPushMessageService
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class SendPushMessageService (private val sendPushMessageService: SendPushMessageService) {
    fun sendPush(pushToUser: PushToUser) {
        sendPushMessageService.sendPush(pushToUser)
    }
}
