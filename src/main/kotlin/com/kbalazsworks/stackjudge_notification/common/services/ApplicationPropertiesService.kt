package com.kbalazsworks.stackjudge_notification.common.services

import org.eclipse.microprofile.config.inject.ConfigProperty
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class ApplicationPropertiesService {
    @ConfigProperty(name = "pushover.app.token")
    var pushoverAppToken: String = ""
    @ConfigProperty(name = "main.stackjudge.app.host")
    var mainStackjudgeAppHost: String = ""
}
