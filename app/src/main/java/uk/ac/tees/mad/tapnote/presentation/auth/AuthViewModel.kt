package uk.ac.tees.mad.tapnote.presentation.auth

import android.util.Patterns
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class AuthViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState

    fun onEmailChange(email: String) {
        _uiState.update {
            it.copy(
                email = email,
                isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
            )
        }
    }

    fun onPasswordChange(password: String) {
        _uiState.update {
            it.copy(
                password = password,
                isPasswordValid = password.length >= 6
            )
        }
    }

    fun canSubmit(): Boolean {
        return _uiState.value.isEmailValid &&
                _uiState.value.isPasswordValid
    }
}
