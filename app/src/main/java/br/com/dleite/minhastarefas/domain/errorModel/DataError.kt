package br.com.dleite.minhastarefas.domain.errorModel

sealed interface DataError : Error {
    enum class Network : DataError {
        REQUEST_TIMEOUT,
        TOO_MANY_REQUESTS,
        NO_INTERNET,
        INVALID_RESPONSE,
        PAYLOAD_TOO_LARGE,
        SERVER_ERROR,
        SERIAILIZATION,
        ERROR_EMAIL_ALREADY_IN_USE,
        UNKNOWN,
        ERROR_INVALID_EMAIL
    }

    enum class Local : DataError {
        DISK_FULL,
    }


}