package com.kbalazsworks.stackjudge_notification.push.requests

import org.jboss.resteasy.reactive.PartType
import org.jboss.resteasy.reactive.RestForm
import javax.ws.rs.core.MediaType

@Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")
class PushToUserRequest {
    @RestForm
    @PartType(MediaType.TEXT_PLAIN)
    lateinit var userId: String

    @RestForm
    @PartType(MediaType.TEXT_PLAIN)
    lateinit var title: String

    @RestForm
    @PartType(MediaType.TEXT_PLAIN)
    lateinit var message: String
}
