package com.kbalazsworks.stackjudge_notification.common.factories

import java.util.*
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class DateFactory {
    fun create() = Date()
    fun create(date: Long) = Date(date)
}
