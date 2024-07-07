package br.com.dleite.minhastarefas.ui.screens.uiGeneric

import br.com.dleite.minhastarefas.R
import br.com.dleite.minhastarefas.domain.errorModel.DataError
import br.com.dleite.minhastarefas.domain.errorModel.ResultRoot
import br.com.dleite.minhastarefas.domain.errorModel.UserDataValidator

fun DataError.asUiText(): UiText {
    return when (this) {
        DataError.Network.ERROR_EMAIL_ALREADY_IN_USE -> UiText.StringResource(
            R.string.error_email_already_in_use
        )

        DataError.Network.ERROR_INVALID_EMAIL -> UiText.StringResource(
            R.string.error_invalid_email
        )

        DataError.Network.UNKNOWN -> UiText.StringResource(R.string.error_unknown)
        DataError.Local.DISK_FULL -> TODO()
        DataError.Network.REQUEST_TIMEOUT -> TODO()
        DataError.Network.TOO_MANY_REQUESTS -> TODO()
        DataError.Network.NO_INTERNET -> TODO()
        DataError.Network.INVALID_RESPONSE -> TODO()
        DataError.Network.PAYLOAD_TOO_LARGE -> TODO()
        DataError.Network.SERVER_ERROR -> TODO()
        DataError.Network.SERIAILIZATION -> TODO()
    }
}

fun UserDataValidator.PasswordError.asUiText(): UiText {
    return when (this) {
        UserDataValidator.PasswordError.TOO_SHORT ->
            UiText.StringResource(R.string.error_password_too_short)

        UserDataValidator.PasswordError.NO_CONFIRMATION -> {
            UiText.StringResource(R.string.error_password_no_confirmation)
        }

        UserDataValidator.PasswordError.NO_UPPERCASE -> {
            UiText.StringResource(R.string.error_password_no_uppercase)
        }

        UserDataValidator.PasswordError.NO_DIGIT -> {
            UiText.StringResource(R.string.error_password_no_digit)
        }

    }
}

fun ResultRoot.Error<*, DataError>.asErrorUiText(): UiText {
    return error.asUiText()
}