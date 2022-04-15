package com.kbalazsworks.stackjudge_notification.push.controller

import com.kbalazsworks.stackjudge_notification.push.requests.PushToUserRequest
import com.kbalazsworks.stackjudge_notification.push.service.PushMapperService
import com.kbalazsworks.stackjudge_notification.push.service.SendPushMessageService
import org.jboss.resteasy.reactive.MultipartForm
import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/push/to-user")
class PostPushToUserAction(
    private val sendPushMessageService: SendPushMessageService,
    private val pushMapperService: PushMapperService
) {
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    fun action(@MultipartForm request: PushToUserRequest) {
        sendPushMessageService.sendPush(pushMapperService.map(request))
    }
}
