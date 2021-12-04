package com.example.motionlayout

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.helper.widget.Carousel

class CarouselActivity : AppCompatActivity() {
    private lateinit var carousel: Carousel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carousel)

        carousel = findViewById(R.id.carousel)

        carousel.setAdapter(object : Carousel.Adapter {
            override fun count(): Int {
                // need to return the number of items we have in the carousel
                return 5
            }

            override fun populate(view: View, index: Int) {
                // need to implement this to populate the view at the given index
            }

            override fun onNewItem(index: Int) {
                // called when an item is set
            }
        })
    }
}