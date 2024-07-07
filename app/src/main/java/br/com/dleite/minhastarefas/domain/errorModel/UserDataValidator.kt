package br.com.dleite.minhastarefas.domain.errorModel


class UserDataValidator {

    fun validatePassword(password: String, confirmation: String): ResultRoot<Unit, PasswordError> {
        if (password.length < 8) {
            return ResultRoot.Error(PasswordError.TOO_SHORT)
        }

        val hasDigit = password.any { it.isDigit() }
        if (!hasDigit) {
            return ResultRoot.Error(PasswordError.NO_DIGIT)
        }
        if (password != confirmation) {
            return ResultRoot.Error(PasswordError.NO_CONFIRMATION)
        }
        val hasUppercase = password.any { it.isUpperCase() }
        if (!hasUppercase) {
            return ResultRoot.Error(PasswordError.NO_UPPERCASE)
        }
        return ResultRoot.Success(Unit)
    }

    enum class PasswordError : Error {
        NO_CONFIRMATION,
        TOO_SHORT,
        NO_UPPERCASE,
        NO_DIGIT
    }
}