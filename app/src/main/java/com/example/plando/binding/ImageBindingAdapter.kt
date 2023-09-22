package com.example.plando.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.plando.Utils
import com.squareup.picasso.Picasso

class ImageBindingAdapter(
    private val picasso: Picasso,
    private val baseUrl: String
) {
    @BindingAdapter("iconName")
    fun loadImage(imageView: ImageView, iconName: String) =
        Utils.loadImageOrEmpty(imageView, picasso, baseUrl, iconName)
}