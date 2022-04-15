package com.kbalazsworks.stackjudge_notification.push.service

import com.kbalazsworks.stackjudge_notification.push.requests.PushToUserRequest
import com.kbalazsworks.stackjudge_notification.push.value_objects.PushToUser
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class PushMapperService {
    fun map(request: PushToUserRequest): PushToUser
    {
        return PushToUser(request.userId.toInt(), request.title, request.message)
    }
}
