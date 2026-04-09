package uk.ac.tees.mad.tapnote.presentation.quicknote

import android.app.Application
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import uk.ac.tees.mad.tapnote.data.local.database.TapNoteDatabase
import uk.ac.tees.mad.tapnote.data.local.entity.NoteEntity
import uk.ac.tees.mad.tapnote.data.preferences.SettingsPreferences
import uk.ac.tees.mad.tapnote.data.repository.NotesRepository

class QuickNoteViewModel(application: Application) : AndroidViewModel(application) {

    private val repository =
        NotesRepository(TapNoteDatabase.getInstance(application).noteDao())

    private val prefs = SettingsPreferences(application)

    private val vibrator: Vibrator by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val manager = application.getSystemService(VibratorManager::class.java)
            manager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            application.getSystemService(Vibrator::class.java)
        }
    }

    private fun vibrate() {
        if (!prefs.hapticEnabled) return

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(
                VibrationEffect.createOneShot(
                    40,
                    VibrationEffect.DEFAULT_AMPLITUDE
                )
            )
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(40)
        }
    }

    fun onPopupOpened() {
        vibrate()
    }

    fun saveNote(
        content: String,
        onDone: () -> Unit
    ) {
        if (content.isBlank()) return

        viewModelScope.launch {
            repository.insertNote(
                NoteEntity(
                    content = content,
                    timestamp = System.currentTimeMillis()
                )
            )

            vibrate()
            onDone()
        }
    }
}