package uk.ac.tees.mad.tapnote.presentation.auth

data class AuthUiState(
    val email: String = "",
    val password: String = "",
    val isEmailValid: Boolean = true,
    val isPasswordValid: Boolean = true,
)
