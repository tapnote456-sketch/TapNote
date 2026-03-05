package uk.ac.tees.mad.tapnote.presentation.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import uk.ac.tees.mad.tapnote.ui.theme.TapNoteTheme

@Composable
fun AuthScreen(
    isLogin: Boolean,
    viewModel: AuthViewModel = viewModel(),
    onToggleMode: () -> Unit = {}
) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = if (isLogin) "Welcome back" else "Create account",
            style = MaterialTheme.typography.displaySmall.copy(
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(Modifier.height(32.dp))

        OutlinedTextField(
            value = state.email,
            onValueChange = viewModel::onEmailChange,
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            isError = !state.isEmailValid
        )

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = state.password,
            onValueChange = viewModel::onPasswordChange,
            label = { Text("Password (min 6 chars)") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            isError = !state.isPasswordValid
        )

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = { },
            modifier = Modifier.fillMaxWidth(),
            enabled = viewModel.canSubmit()
        ) {
            Text(if (isLogin) "Login" else "Register")
        }

        Spacer(Modifier.height(16.dp))

        TextButton(onClick = onToggleMode) {
            Text(
                if (isLogin)
                    "New here? Create an account"
                else
                    "Already have an account? Login"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    TapNoteTheme {
        AuthScreen(isLogin = true)
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterPreview() {
    TapNoteTheme {
        AuthScreen(isLogin = false)
    }
}
