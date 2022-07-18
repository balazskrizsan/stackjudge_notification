package com.kbalazsworks.oidc.factories

import com.kbalazsworks.stackjudge_notification.common.factories.SystemFactory
import com.kbalazsworks.oidc.entities.OidcConfig
import com.kbalazsworks.oidc.services.OidcHttpClientService
import com.kbalazsworks.oidc.services.OidcService
import com.kbalazsworks.oidc.services.TokenService
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class OidcServiceFactory(
    private val oidcHttpClientService: OidcHttpClientService,
    private val tokenService: TokenService,
    private val systemFactory: SystemFactory,
) {
    companion object {
        private const val DISCOVERY_ENDPOINT = "/.well-known/openid-configuration"
    }

    fun create(host: String): OidcService {
        val oidcConfig = oidcHttpClientService.getWithMap(host + DISCOVERY_ENDPOINT, OidcConfig::class.java)

        return OidcService(oidcConfig, oidcHttpClientService, tokenService, systemFactory)
    }
}
