package com.kbalazsworks.stackjudge_notification.oidc

import com.kbalazsworks.stackjudge_notification.oidc.factories.OidcServiceFactory
import com.kbalazsworks.stackjudge_notification.oidc.services.OidcService
import io.quarkus.arc.DefaultBean
import javax.enterprise.context.Dependent
import javax.enterprise.inject.Produces

@Dependent
class OidcConfiguration(private val oidcServiceFactory: OidcServiceFactory) {
    @Produces
    @DefaultBean
    fun oidcService(): OidcService {
        return oidcServiceFactory.create("https://localhost:5001")
    }
}
