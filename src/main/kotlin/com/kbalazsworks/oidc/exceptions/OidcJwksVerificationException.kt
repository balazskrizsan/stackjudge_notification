package com.kbalazsworks.oidc.exceptions

class OidcJwksVerificationException : OidcException {
    constructor() : super()
    constructor(message: String) : super(message)
}
