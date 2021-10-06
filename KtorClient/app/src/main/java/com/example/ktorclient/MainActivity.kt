package com.example.ktorclient

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.InetSocketAddress
import java.net.Proxy

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val client = HttpClient(Android) {
            engine {
                connectTimeout = 100_000
                socketTimeout = 100_000
            }
        }
        CoroutineScope(Dispatchers.IO).launch {
            val response: HttpResponse = client.get("http://localhost:8080/json/jackson") {

            }
            Log.e(
                "TAG",
                "onCreate: ${
                    response.content.totalBytesRead
                }",
            )
        }

    }
}