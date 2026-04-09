package uk.ac.tees.mad.tapnote

import android.content.Context
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import uk.ac.tees.mad.tapnote.data.preferences.SettingsPreferences
import uk.ac.tees.mad.tapnote.navigation.QuickNote
import uk.ac.tees.mad.tapnote.navigation.TapNoteNavGraph
import uk.ac.tees.mad.tapnote.presentation.utils.ShakeDetector
import uk.ac.tees.mad.tapnote.ui.theme.TapNoteTheme

class MainActivity : ComponentActivity() {

    private lateinit var sensorManager: SensorManager
    private var shakeDetector: ShakeDetector? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        setContent {
            TapNoteTheme {
                val navController = rememberNavController()
                val prefs = SettingsPreferences(this)
                shakeDetector = ShakeDetector(
                    getThreshold = { prefs.shakeSensitivity },
                    isShakeEnabled = { prefs.shakeEnabled },
                    onShake = {
                        navController.navigate(QuickNote)
                    }
                )
                TapNoteNavGraph(navController = navController)
            }
        }
    }

    override fun onResume() {
        super.onResume()

        val accelerometer =
            sensorManager.getDefaultSensor(android.hardware.Sensor.TYPE_ACCELEROMETER)

        shakeDetector?.let {
            sensorManager.registerListener(
                it,
                accelerometer,
                SensorManager.SENSOR_DELAY_UI
            )
        }
    }

    override fun onPause() {
        super.onPause()

        shakeDetector?.let { sensorManager.unregisterListener(it) }
    }
}