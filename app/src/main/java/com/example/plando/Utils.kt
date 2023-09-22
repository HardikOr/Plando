package com.example.plando

import android.widget.ImageView
import com.squareup.picasso.Picasso

object Utils {
    fun loadImageOrEmpty(
        imageView: ImageView,
        picasso: Picasso,
        baseUrl: String,
        iconName: String
    ) {
        if (iconName.isNotEmpty()) {
            picasso.load("$baseUrl/$iconName.png").into(imageView)
        } else {
            imageView.setImageResource(0)
        }
    }
}