package br.com.dleite.minhastarefas.ui.states

import br.com.dleite.minhastarefas.ui.screens.uiGeneric.UiText

data class SignUpUiState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val onEmailChange: (String) -> Unit = {},
    val onPasswordChange: (String) -> Unit = {},
    val onConfirmPasswordChange: (String) -> Unit = {},
    val error: UiText? = null,
)