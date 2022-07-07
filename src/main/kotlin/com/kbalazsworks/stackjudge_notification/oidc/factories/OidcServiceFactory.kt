package com.kbalazsworks.stackjudge_notification.oidc.factories

import com.kbalazsworks.stackjudge_notification.oidc.services.OidcHttpClientService
import com.kbalazsworks.stackjudge_notification.oidc.services.OidcService
import com.kbalazsworks.stackjudge_notification.oidc.entities.OidcConfig
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class OidcServiceFactory(private val oidcHttpClientService: OidcHttpClientService) {
    companion object {
        private const val DISCOVERY_ENDPOINT = "/.well-known/openid-configuration"
    }

    fun create(host: String): OidcService {
        val oidcConfig = oidcHttpClientService.getWithMap(host + DISCOVERY_ENDPOINT, OidcConfig::class.java)

        return OidcService(oidcConfig, oidcHttpClientService)
    }
}
