package uk.ac.tees.mad.tapnote.presentation.settings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import uk.ac.tees.mad.tapnote.data.local.database.TapNoteDatabase
import uk.ac.tees.mad.tapnote.data.preferences.SettingsPreferences
import uk.ac.tees.mad.tapnote.data.repository.NotesRepository

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = SettingsPreferences(application)
    private val repository =
        NotesRepository(TapNoteDatabase.getInstance(application).noteDao())
    private val auth = FirebaseAuth.getInstance()

    var hapticEnabled: Boolean
        get() = prefs.hapticEnabled
        set(value) { prefs.hapticEnabled = value }

    var shakeEnabled: Boolean
        get() = prefs.shakeEnabled
        set(value) { prefs.shakeEnabled = value }

    var shakeSensitivity: Float
        get() = prefs.shakeSensitivity
        set(value) { prefs.shakeSensitivity = value }

    fun clearAllNotes() {
        viewModelScope.launch { repository.clearAllNotes() }
    }

    fun logout() {
        auth.signOut()
    }
}