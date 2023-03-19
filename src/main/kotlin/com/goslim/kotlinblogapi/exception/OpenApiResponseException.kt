package com.goslim.kotlinblogapi.exception

class OpenApiResponseException : RuntimeException {
    constructor() : super()
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
}
