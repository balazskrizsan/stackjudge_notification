package com.kbalazsworks.stackjudge_notification.oidc.services

interface IOidcService {
    fun isExpiredToken(token: String): Boolean
}
