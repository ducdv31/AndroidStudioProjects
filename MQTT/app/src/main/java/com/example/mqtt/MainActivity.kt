package com.example.mqtt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mqtt.abstract.MQTTBase

class MainActivity : MQTTBase() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}