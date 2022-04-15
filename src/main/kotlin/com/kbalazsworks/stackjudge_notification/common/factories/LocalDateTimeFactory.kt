package com.kbalazsworks.stackjudge_notification.common.factories

import java.time.ZoneId
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class LocalDateTimeFactory(private val dateFactory: DateFactory) {
    fun create() = dateFactory.create().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
}
