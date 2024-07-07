package br.com.dleite.minhastarefas.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.dleite.minhastarefas.R
import br.com.dleite.minhastarefas.ui.screens.uiGeneric.UiText
import br.com.dleite.minhastarefas.ui.states.SignUpUiState
import br.com.dleite.minhastarefas.ui.theme.MinhasTarefasTheme
import br.com.dleite.minhastarefas.ui.theme.Typography

@Composable
fun SignUpScreen(
    uiState: SignUpUiState,
    onSignUpClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        AnimatedVisibility(visible = uiState.error != null) {
            uiState.error?.let {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Red)
                ) {
                    Text(
                        text = it.asString(),
                        Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
        Text(
            text = "Cadastrando usuário",
            Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            style = Typography.titleLarge
        )
        Column(
            Modifier
                .fillMaxWidth(0.8f)
                .weight(1f)
                .padding(8.dp)
                .align(Alignment.CenterHorizontally),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OutlinedTextField(
                value = uiState.email,
                onValueChange = uiState.onEmailChange,
                Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(25),
                label = {
                    Text(text = "Email")
                }
            )
            OutlinedTextField(
                value = uiState.password,
                onValueChange = uiState.onPasswordChange,
                Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(25),
                label = {
                    Text(text = "Senha")
                },
                visualTransformation = PasswordVisualTransformation()
            )
            OutlinedTextField(
                value = uiState.confirmPassword,
                onValueChange = uiState.onConfirmPasswordChange,
                Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(25),
                label = {
                    Text(text = "Confirmar senha")
                },
                visualTransformation = PasswordVisualTransformation()
            )
            Button(
                onClick = onSignUpClick,
                Modifier.fillMaxWidth()
            ) {
                Text(text = "Cadastrar")
            }
        }
    }
}

@Preview(showBackground = true, name = "Default")
@Composable
fun SignUpScreenPreview() {
    MinhasTarefasTheme {
        SignUpScreen(
            uiState = SignUpUiState(),
            onSignUpClick = {}
        )
    }
}

@Preview(showBackground = true, name = "With error")
@Composable
fun SignUpScreen1Preview() {
    MinhasTarefasTheme {
        SignUpScreen(
            uiState = SignUpUiState(
                error = UiText.StringResource(R.string.error_invalid_email)
            ),
            onSignUpClick = {}
        )
    }
}