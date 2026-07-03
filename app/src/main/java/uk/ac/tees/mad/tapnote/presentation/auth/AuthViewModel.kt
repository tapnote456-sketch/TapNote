package uk.ac.tees.mad.tapnote.presentation.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class AuthViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState

    var emailError by mutableStateOf("")
        private set
    var passwordError by mutableStateOf("")
        private set

    fun onEmailChange(email: String) {
        emailError = ""
        _uiState.update { it.copy(email = email) }
    }

    fun onPasswordChange(password: String) {
        passwordError = ""
        _uiState.update { it.copy(password = password) }
    }

    fun toggleMode() =
        _uiState.update {
            it.copy(
                mode = if (it.mode == AuthMode.LOGIN)
                    AuthMode.REGISTER else AuthMode.LOGIN,
                errorMessage = null
            )
        }

    fun validateFields(email: String, password: String): Boolean {
        var valid = true

        emailError = when {
            email.isBlank() -> { valid = false; "Email cannot be empty" }
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() ->
                { valid = false; "Enter a valid email address" }
            else -> ""
        }

        passwordError = when {
            password.isBlank() -> { valid = false; "Password cannot be empty" }
            password.length < 6 -> { valid = false; "Password must be at least 6 characters" }
            else -> ""
        }

        return valid
    }

    fun submit(onSuccess: () -> Unit) {
        val state = _uiState.value
        if (!validateFields(state.email, state.password)) return
        _uiState.update { it.copy(isLoading = true) }

        val task = if (state.mode == AuthMode.LOGIN) {
            auth.signInWithEmailAndPassword(state.email, state.password)
        } else {
            auth.createUserWithEmailAndPassword(state.email, state.password)
        }

        task
            .addOnSuccessListener {
                _uiState.update { it.copy(isLoading = false) }
                onSuccess()
            }
            .addOnFailureListener { e ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.localizedMessage ?: "Auth failed"
                    )
                }
            }
    }

    fun clearError() =
        _uiState.update { it.copy(errorMessage = null) }
}
