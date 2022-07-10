package com.kbalazsworks.stackjudge_notification.common.factories

import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class SystemFactory {
    fun getCurrentTimeMillis() = System.currentTimeMillis()
}
