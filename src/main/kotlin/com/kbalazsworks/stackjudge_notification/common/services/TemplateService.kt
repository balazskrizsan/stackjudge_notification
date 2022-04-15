package com.kbalazsworks.stackjudge_notification.common.services

import com.kbalazsworks.stackjudge_notification.common.exceptions.TemplateException
import io.quarkus.qute.runtime.TemplateProducer
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class TemplateService(private val templateProducer: TemplateProducer) {
    @Throws(TemplateException::class)
    fun render(template: String, context: Map<String, Any>): String {
        try {
            return templateProducer.getInjectableTemplate(template).data(context).render()
        } // @todo3: test
        catch (e: Exception) {
            throw TemplateException("Tmplate error: " + e.message)
        }
    }
}