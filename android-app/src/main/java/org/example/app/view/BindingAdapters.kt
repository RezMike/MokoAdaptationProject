package org.example.app.view

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import dev.icerock.moko.media.Bitmap

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("app:image")
    fun bindImage(view: ImageView, image: Bitmap?) {
        view.setImageBitmap(image?.platformBitmap)
    }
}