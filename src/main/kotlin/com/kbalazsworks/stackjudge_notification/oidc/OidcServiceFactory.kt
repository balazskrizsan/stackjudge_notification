package com.kbalazsworks.stackjudge_notification.oidc

import com.kbalazsworks.simple_oidc.Configuration
import com.kbalazsworks.simple_oidc.services.OidcService
import io.activej.inject.Injector
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class OidcServiceFactory {
    fun get(): OidcService {
        val module = Configuration().setUpDi()

        return Injector.of(module).getInstance(OidcService::class.java);
    }
}
