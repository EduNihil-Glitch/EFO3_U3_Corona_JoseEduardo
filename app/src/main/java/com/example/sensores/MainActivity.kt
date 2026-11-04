package com.example.sensores

import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var tvModo : TextView
    private lateinit var tvValor : TextView
    private lateinit var layout : LinearLayout
    private lateinit var tvSensor : TextView

    //Crear un atributo que nos permita el manejo de sensores
    private lateinit var sensorManager: SensorManager

    //Atributo que permite usar el sensor
    private var sensorLuz : Sensor? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        tvModo = findViewById(R.id.tvModo)
        tvValor = findViewById(R.id.tvValor)
        tvSensor = findViewById(R.id.tvSensor)
        layout = findViewById(R.id.layout)

        //Indicar que el sensor ocupara los servicios del sistema
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        //Hacer uso del sensor de luz
        sensorLuz = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

        //Verificar si el sensor de luz esta disponible
        if(sensorLuz == null){
            tvModo.text = "No hay sensor de Luz"
        }

    }

    //Metodos obligatorios de la clase
    //Metodo para ver los cambios en el sensor
    override fun onSensorChanged(event: SensorEvent?) {

        //Validar que el sensor sea el correcto
        if(event?.sensor?.type == Sensor.TYPE_LIGHT){

            //variable para guardar valores
            val luz = event.values[0]

            tvValor.text = "Luz: $luz lx"

            if(luz < 50){
                layout.setBackgroundColor(Color.BLACK)
                tvModo.setTextColor(Color.WHITE)
                tvValor.setTextColor(Color.WHITE)
                tvSensor.setTextColor(Color.WHITE)
            }else if(luz>500) {
                layout.setBackgroundColor(Color.WHITE)
                tvModo.setTextColor(Color.BLACK)
                tvValor.setTextColor(Color.BLACK)
                tvSensor.setTextColor(Color.BLACK)
            }

        }

    }

    //Metodo para hacer cambios en los valores matematicos
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    //Metodo para apagar el componente(Sensor)
    override fun onResume() {
        super.onResume()

        sensorLuz?.let {
            sensorManager.registerListener(
                this,
                it,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }

    }

}