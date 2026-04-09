package uk.ac.tees.mad.tapnote.data.preferences

import android.content.Context
import androidx.core.content.edit

class SettingsPreferences(context: Context) {

    private val prefs =
        context.getSharedPreferences("tapnote_settings", Context.MODE_PRIVATE)

    var hapticEnabled: Boolean
        get() = prefs.getBoolean("haptic_enabled", true)
        set(value) = prefs.edit { putBoolean("haptic_enabled", value) }

    var shakeEnabled: Boolean
        get() = prefs.getBoolean("shake_enabled", true)
        set(value) = prefs.edit { putBoolean("shake_enabled", value) }

    var shakeSensitivity: Float
        get() = prefs.getFloat("shake_sensitivity", 8f)
        set(value) = prefs.edit { putFloat("shake_sensitivity", value) }
}