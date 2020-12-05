package edu.gwu.androidtweets

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

class ShakeManager(context: Context) : SensorEventListener {

    private val SHAKE_THRESHOLD = 5

    private val sensorManager: SensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    private var callback: (() -> Unit)? = null

    fun detectShakes(callback: () -> Unit) {
        this.callback = callback
        if (sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER).isNotEmpty()) {
            val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

            sensorManager.registerListener(
                this,
                accelerometer,
                SensorManager.SENSOR_DELAY_UI
            )
        } else {
            Log.e("ShakeManager", "Device does not have an accelerometer!")
        }
    }

    fun stopDetectingShakes() {
        this.callback = null
        sensorManager.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}

    override fun onSensorChanged(event: SensorEvent) {
        val x = event.values[0].toDouble()
        val y = event.values[1].toDouble()
        val z = event.values[2].toDouble()

        val acceleration = sqrt(x.pow(2) + y.pow(2) + z.pow(2)) - SensorManager.GRAVITY_EARTH

        Log.d("ShakeManager", "[X, Y, Z] = [$x, $y, $z]; Acceleration = $acceleration")
        if (abs(acceleration) > SHAKE_THRESHOLD) {
            Log.d("ShakeManager", "Shake detected!")
            callback?.invoke()
        }
    }
}