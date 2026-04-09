package uk.ac.tees.mad.tapnote.presentation.utils

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlin.math.sqrt

class ShakeDetector(
    private val getThreshold: () -> Float,
    private val isShakeEnabled: () -> Boolean,
    private val onShake: () -> Unit
) : SensorEventListener {

    private var lastShakeTime = 0L
    private val cooldown = 800

    override fun onSensorChanged(event: SensorEvent?) {
        event ?: return
        if (!isShakeEnabled()) return

        val x = event.values[0]
        val y = event.values[1]
        val z = event.values[2]

        val acceleration = sqrt(x * x + y * y + z * z) - SensorManager.GRAVITY_EARTH

        if (acceleration > getThreshold()) {
            val now = System.currentTimeMillis()

            if (now - lastShakeTime > cooldown) {
                lastShakeTime = now
                onShake()
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) = Unit
}