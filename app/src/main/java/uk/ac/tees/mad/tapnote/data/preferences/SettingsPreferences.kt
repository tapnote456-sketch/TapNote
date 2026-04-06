package uk.ac.tees.mad.tapnote.data.preferences

import android.content.Context
import androidx.core.content.edit

class SettingsPreferences(context: Context) {

    private val prefs =
        context.getSharedPreferences("tapnote_settings", Context.MODE_PRIVATE)

    var hapticEnabled: Boolean
        get() = prefs.getBoolean("haptic_enabled", true)
        set(value) = prefs.edit { putBoolean("haptic_enabled", value) }
}
