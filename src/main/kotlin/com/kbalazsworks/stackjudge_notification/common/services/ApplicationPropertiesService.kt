package com.kbalazsworks.stackjudge_notification.common.services

import org.eclipse.microprofile.config.inject.ConfigProperty
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class ApplicationPropertiesService {
    @field:ConfigProperty(name = "pushover.app.token")
    lateinit var pushoverAppToken: String

    @field:ConfigProperty(name = "main.stackjudge.app.host")
    lateinit var mainStackjudgeAppHost: String

    @field:ConfigProperty(name = "keystore.full_path")
    lateinit var keystoreFullPath: String
}
