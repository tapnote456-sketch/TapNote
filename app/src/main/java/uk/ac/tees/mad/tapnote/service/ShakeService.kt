package uk.ac.tees.mad.tapnote.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.IBinder
import uk.ac.tees.mad.tapnote.data.preferences.SettingsPreferences
import uk.ac.tees.mad.tapnote.presentation.quicknote.QuickNoteActivity

class ShakeService : Service() {

    private lateinit var sensorManager: SensorManager
    private lateinit var shakeDetector: ShakeDetector
    private lateinit var prefs: SettingsPreferences
    private lateinit var notificationManager: ShakeNotificationManager

    private var lastLaunchTime = 0L

    override fun onCreate() {
        super.onCreate()

        prefs = SettingsPreferences(this)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        notificationManager = ShakeNotificationManager(this)

        setupShakeDetection()
        startForeground(1, notificationManager.createNotification())
        registerSensor()
    }

    private fun setupShakeDetection() {
        shakeDetector = ShakeDetector(
            getThreshold = { prefs.shakeSensitivity },
            isShakeEnabled = { prefs.shakeEnabled },
            onShake = { launchQuickNote() }
        )
    }

    private fun registerSensor() {
        val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        sensorManager.registerListener(
            shakeDetector,
            accelerometer,
            SensorManager.SENSOR_DELAY_GAME
        )
    }

    private fun launchQuickNote() {
        val now = System.currentTimeMillis()
        if (now - lastLaunchTime < 1500) return
        lastLaunchTime = now

        val intent = Intent(this, QuickNoteActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }

        startActivity(intent)
    }

    override fun onDestroy() {
        sensorManager.unregisterListener(shakeDetector)
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onTaskRemoved(rootIntent: Intent?) {
        stopSelf()
        super.onTaskRemoved(rootIntent)
    }
}