package br.com.dleite.minhastarefas.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.dleite.minhastarefas.authentication.FirebaseAuthRepository
import br.com.dleite.minhastarefas.domain.errorModel.ResultRoot
import br.com.dleite.minhastarefas.domain.errorModel.UserDataValidator
import br.com.dleite.minhastarefas.ui.screens.uiGeneric.UiText
import br.com.dleite.minhastarefas.ui.screens.uiGeneric.asUiText
import br.com.dleite.minhastarefas.ui.states.SignUpUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val firebaseAuthRepository: FirebaseAuthRepository,
    private val userDataValidator: UserDataValidator
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState = _uiState.asStateFlow()
    private val _signUpIsSuccessful = MutableSharedFlow<Boolean>()
    val signUpIsSuccessful = _signUpIsSuccessful.asSharedFlow()

    init {
        _uiState.update { currentState ->
            currentState.copy(
                onEmailChange = { user ->
                    _uiState.update {
                        it.copy(email = user)
                    }
                },
                onPasswordChange = { password ->
                    _uiState.update {
                        it.copy(password = password)
                    }
                },
                onConfirmPasswordChange = { password ->
                    _uiState.update {
                        it.copy(confirmPassword = password)
                    }
                }
            )
        }
    }

    fun onCheckPassword(uiState: SignUpUiState): Boolean {
        var isPasswordValid = false
        viewModelScope.launch {
            when (val result =
                userDataValidator.validatePassword(uiState.password, uiState.confirmPassword)) {
                is ResultRoot.Error -> {
                    sendError(result.error.asUiText())
                    isPasswordValid = false
                }

                is ResultRoot.Success -> {
                    isPasswordValid = true
                }
            }
        }
        return isPasswordValid
    }


    suspend fun signUp() {
        if (onCheckPassword(_uiState.value)) {
            when (val result = firebaseAuthRepository.signUp(
                _uiState.value.email,
                _uiState.value.password
            )) {
                is ResultRoot.Error -> {
                    sendError(result.error.asUiText())
                }

                is ResultRoot.Success -> {
                    _signUpIsSuccessful.emit(true)
                }
            }
        }
    }

    private suspend fun sendError(text: UiText, timeMillis: Long = 3000) {
        _uiState.update {
            it.copy(
                error = text
            )
        }
        delay(timeMillis)
        _uiState.update {
            it.copy(
                error = null
            )
        }
    }
}
