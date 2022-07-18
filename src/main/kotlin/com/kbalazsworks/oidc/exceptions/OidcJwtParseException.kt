package com.kbalazsworks.oidc.exceptions

class OidcJwtParseException : OidcException {
    constructor() : super()
    constructor(message: String) : super(message)
}
