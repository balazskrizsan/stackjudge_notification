package com.kbalazsworks.stackjudge_notification.common.services

import java.time.LocalDateTime
import java.time.ZoneOffset
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class DateTimeFormatterService {
    fun toEpoch(now: LocalDateTime) = now.toEpochSecond(ZoneOffset.UTC)
}
