package com.example.retrofit

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.retrofit.api.ApiServices
import com.example.retrofit.model.Currentcy
import com.example.retrofit.model.Post
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    lateinit var term: TextView
    lateinit var source: TextView
    lateinit var usdvnd: TextView
    lateinit var callApi: TextView

    lateinit var postResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        term = findViewById(R.id.term)
        source = findViewById(R.id.source)
        usdvnd = findViewById(R.id.usdvnd)
        callApi = findViewById(R.id.call_get_api)
        postResult = findViewById(R.id.id_post_result)

        callApi.setOnClickListener {
            callApiGet()
            startPost()
        }
    }

    private fun callApiGet() {
        /* call api with map */
        //link api:  http://apilayer.net/api/live?access_key=843d4d34ae72b3882e3db642c51e28e6&currencies=VND&source=USD&format=1
        val valueApi: MutableMap<String, String> = mutableMapOf()
        valueApi["access_key"] = "843d4d34ae72b3882e3db642c51e28e6"
        valueApi["currencies"] = "VND"
        valueApi["source"] = "USD"
        valueApi["format"] = "1"
        Log.e("TAG", "callApiGet: $valueApi")
        ApiServices.apiServices.convertUusToVndMap(valueApi)
            .enqueue(object : Callback<Currentcy> {
                override fun onResponse(call: Call<Currentcy>, response: Response<Currentcy>) {
                    Toast.makeText(this@MainActivity, "Call Ok", Toast.LENGTH_SHORT).show()
                    val currentcy: Currentcy? = response.body()
                    currentcy?.let {
                        if (currentcy.success) {
                            term.text = currentcy.terms
                            source.text = currentcy.source
                            usdvnd.text = currentcy.quotes.USDVND.toString()
                        }
                    }
                    Log.e("TAG", "onResponse: $currentcy")
                }

                override fun onFailure(call: Call<Currentcy>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "Call failed", Toast.LENGTH_SHORT).show()
                }

            })
    }

    private fun startPost() {
        val post = Post(Random.nextInt(1, 1000), 1, "Duc", "Ok")
        ApiServices.apiServicesPost.sendPost(post)
            .enqueue(object : Callback<Post> {
                override fun onResponse(call: Call<Post>, response: Response<Post>) {
                    Toast.makeText(this@MainActivity, "Call Ok", Toast.LENGTH_SHORT).show()
                    val postRes: Post? = response.body()
                    postRes?.let {
                        postResult.text = postRes.toString()
                    }
                }

                override fun onFailure(call: Call<Post>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "Call failed", Toast.LENGTH_SHORT).show()
                }

            })
    }
}