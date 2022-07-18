package com.kbalazsworks.stackjudge_notification.common.factories

import com.kbalazsworks.stackjudge_notification.common.services.ApplicationPropertiesService
import org.apache.http.conn.ssl.TrustSelfSignedStrategy
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClients
import org.apache.http.ssl.SSLContextBuilder
import java.io.File
import java.io.FileInputStream
import java.security.KeyStore
import java.security.SecureRandom
import javax.enterprise.context.ApplicationScoped
import javax.net.ssl.SSLContext

@ApplicationScoped
class HttpClientFactory(private val applicationPropertiesService: ApplicationPropertiesService) {
    private fun keyStore(file: String, password: CharArray): KeyStore {
        val keyStore = KeyStore.getInstance("PKCS12")
        val key = File(file)
        FileInputStream(key).use { `in` -> keyStore.load(`in`, password) }

        return keyStore
    }

    private fun getSslContext(): SSLContext {
        val password: CharArray = "password".toCharArray()

        return SSLContextBuilder
            .create()
            .setSecureRandom(SecureRandom())
            .loadKeyMaterial(
                keyStore("${applicationPropertiesService.keystoreFullPath}\\sjdev.p12", password),
                password
            )
            .loadTrustMaterial(null, TrustSelfSignedStrategy())
            .build()
    }

    fun build(): CloseableHttpClient {
        return HttpClients.custom().setSSLContext(getSslContext()).build()
    }
}
