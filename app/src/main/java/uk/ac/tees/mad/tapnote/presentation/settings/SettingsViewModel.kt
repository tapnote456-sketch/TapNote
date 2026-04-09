package uk.ac.tees.mad.tapnote.presentation.settings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uk.ac.tees.mad.tapnote.data.local.database.TapNoteDatabase
import uk.ac.tees.mad.tapnote.data.preferences.SettingsPreferences
import uk.ac.tees.mad.tapnote.data.repository.NotesRepository

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = SettingsPreferences(application)
    private val repository =
        NotesRepository(TapNoteDatabase.getInstance(application).noteDao())
    private val auth = FirebaseAuth.getInstance()

    private val _hapticEnabled = MutableStateFlow(prefs.hapticEnabled)
    val hapticEnabled: StateFlow<Boolean> = _hapticEnabled

    private val _shakeEnabled = MutableStateFlow(prefs.shakeEnabled)
    val shakeEnabled: StateFlow<Boolean> = _shakeEnabled

    private val _shakeSensitivity = MutableStateFlow(prefs.shakeSensitivity)
    val shakeSensitivity: StateFlow<Float> = _shakeSensitivity

    fun setHapticEnabled(value: Boolean) {
        prefs.hapticEnabled = value
        _hapticEnabled.value = value
    }

    fun setShakeEnabled(value: Boolean) {
        prefs.shakeEnabled = value
        _shakeEnabled.value = value
    }

    fun setShakeSensitivity(value: Float) {
        prefs.shakeSensitivity = value
        _shakeSensitivity.value = value
    }

    fun clearAllNotes() {
        viewModelScope.launch { repository.clearAllNotes() }
    }

    fun logout() {
        auth.signOut()
    }
}