package com.bignerdranch.android.turchnova_prakt5_1

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val city = findViewById<EditText>(R.id.city)
        val button = findViewById<Button>(R.id.buttonWeather)
        val tvTemp = findViewById<TextView>(R.id.textTemperature)
        val tvPressure = findViewById<TextView>(R.id.textPressure)
        val tvWind = findViewById<TextView>(R.id.textWind)

        button.setOnClickListener {
            val cityName = city.text.toString()

            if (cityName == "") {
                Snackbar.make(
                    findViewById(android.R.id.content),
                    "Введите город",
                    Snackbar.LENGTH_LONG
                ).setActionTextColor(Color.RED).show()
            } else {
                getResult(cityName, tvTemp, tvPressure, tvWind)
            }
        }
    }

    private fun getResult(city: String, tvTemp: TextView, tvPressure: TextView, tvWind: TextView)
    {
        val key = "bcfe40c675822a36ca249c1592dcc06d"
        val url = "https://api.openweathermap.org/data/2.5/weather" + "?q=$city&appid=$key&units=metric&lang=ru"
        val queue = Volley.newRequestQueue(this)
        val request = StringRequest(
            Request.Method.GET,
            url,
            { response ->
                val obj = JSONObject(response)
                val main = obj.getJSONObject("main")
                val wind = obj.getJSONObject("wind")

                tvTemp.text = "Температура: ${main.getDouble("temp")} °C"
                tvPressure.text =
                    "Давление: ${main.getInt("pressure")} гПа"
                tvWind.text =
                    "Ветер: ${wind.getDouble("speed")} м/с"
            },
            {
                Snackbar.make(
                    findViewById(android.R.id.content),
                    "Не удалось получить погоду. Проверь город и API-ключ.",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        )

        queue.add(request)
    }
}
