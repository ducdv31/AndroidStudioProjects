package com.example.retrofit

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.retrofit.api.ApiServices
import com.example.retrofit.model.Currentcy
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var term: TextView
    lateinit var source: TextView
    lateinit var usdvnd: TextView
    lateinit var callApi: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        term = findViewById(R.id.term)
        source = findViewById(R.id.source)
        usdvnd = findViewById(R.id.usdvnd)
        callApi = findViewById(R.id.call_get_api)

        callApi.setOnClickListener {
            callApiGet()
        }
    }

    private fun callApiGet() {
        //link api:  http://apilayer.net/api/live?access_key=843d4d34ae72b3882e3db642c51e28e6&currencies=VND&source=USD&format=1
        ApiServices.apiServices.convertUusToVnd("843d4d34ae72b3882e3db642c51e28e6", "VND", "USD", 1)
            .enqueue(object : Callback<Currentcy> {
                override fun onResponse(call: Call<Currentcy>, response: Response<Currentcy>) {
                    Toast.makeText(this@MainActivity, "Call Ok", Toast.LENGTH_SHORT).show()
                    val currentcy: Currentcy? = response.body()
                    currentcy?.let {
                        if (currentcy.success){
                            term.text = currentcy.terms
                            source.text = currentcy.source
                            usdvnd.text = currentcy.quotes.USDVND.toString()
                        }
                    }
                }

                override fun onFailure(call: Call<Currentcy>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "Call failed", Toast.LENGTH_SHORT).show()
                }

            })
    }
}