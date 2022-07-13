package com.kbalazsworks.stackjudge_notification.common.services.exception_handler_services

import com.kbalazsworks.stackjudge_notification.common.builders.ResponseEntityBuilder
import com.kbalazsworks.stackjudge_notification.oidc.exceptions.OidcException
import com.kbalazsworks.stackjudge_notification.oidc.exceptions.OidcExpiredTokenException
import com.kbalazsworks.stackjudge_notification.oidc.exceptions.OidcJwksVerificationException
import com.kbalazsworks.stackjudge_notification.oidc.exceptions.OidcJwtParseException
import org.slf4j.LoggerFactory
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider

@Provider
class OidcExceptionHandlerService : ExceptionMapper<OidcException> {
    companion object {
        private val logger = LoggerFactory.getLogger(OidcExceptionHandlerService::class.toString())
    }

    val ERROR_CODE_UNKNOWN_ID = 1100
    val ERROR_CODE_UNKNOWN_MSG = "Unkown OIDC error"
    val ERROR_CODE_EXPIRED_TOKEN_ID = 1101
    val ERROR_CODE_EXPIRED_TOKEN_MSG = "Token expired error"
    val ERROR_CODE_JWKS_VERIFICATION_ID = 1102
    val ERROR_CODE_JWKS_VERIFICATION_MSG = "JWKS Verification error"
    val ERROR_CODE_JWT_PARSE_ID = 1103
    val ERROR_CODE_JWT_PARSE_MSG = "JWT parse error"

    override fun toResponse(e: OidcException): Response {
        var errorStatus = Response.Status.INTERNAL_SERVER_ERROR.statusCode
        var errorCode = ERROR_CODE_UNKNOWN_ID
        var errorMessage = ERROR_CODE_UNKNOWN_MSG

        if (e is OidcExpiredTokenException) {
            errorStatus = Response.Status.UNAUTHORIZED.statusCode
            errorCode = ERROR_CODE_EXPIRED_TOKEN_ID
            errorMessage = ERROR_CODE_EXPIRED_TOKEN_MSG
        }

        if (e is OidcJwksVerificationException) {
            errorStatus = Response.Status.INTERNAL_SERVER_ERROR.statusCode
            errorCode = ERROR_CODE_JWKS_VERIFICATION_ID
            errorMessage = ERROR_CODE_JWKS_VERIFICATION_MSG
        }

        if (e is OidcJwtParseException) {
            errorStatus = Response.Status.INTERNAL_SERVER_ERROR.statusCode
            errorCode = ERROR_CODE_JWT_PARSE_ID
            errorMessage = ERROR_CODE_JWT_PARSE_MSG
        }

        logger.error("OIDC error: $errorMessage; #$errorCode", e)

        return Response
            .status(errorStatus)
            .type(MediaType.APPLICATION_JSON)
            .entity(
                ResponseEntityBuilder<String>()
                    .data(errorMessage)
                    .errorCode(errorCode)
                    .statusCode(errorStatus)
                    .build()
            )
            .build()
    }
}
