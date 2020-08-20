package com.craiovadata.android.sunshine.ui.adpterModels

import android.content.Context.MODE_PRIVATE
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.craiovadata.android.sunshine.CityData
import com.craiovadata.android.sunshine.R
import com.craiovadata.android.sunshine.ui.models.WebcamEntry
import com.jjoe64.graphview.GridLabelRenderer
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter
import kotlinx.android.synthetic.main.map_card.view.buttonZoomMinus
import kotlinx.android.synthetic.main.map_card.view.buttonZoomPlus
import kotlinx.android.synthetic.main.webcam_card.view.*
import java.util.*
import java.util.logging.Handler

data class Webcam(val webcams: List<WebcamEntry>?) : Base(-5, TYPE.WEBCAM, Date(0)) {

    companion object {

        @JvmStatic
        fun bindPhotoToUI(webcams:  List<WebcamEntry>?, itemView: View) {

            if (webcams.isNullOrEmpty()) {
                return
            }
//            itemView.visibility = View.VISIBLE

            val prefs = itemView.context.getSharedPreferences("_", MODE_PRIVATE)

            val key = "key_webcam_number"
            var webcamNo = prefs.getInt(key, 0)
            if (webcamNo >= webcams.size) webcamNo = 0

            loadWebcamPreview(
                webcams[webcamNo],
                itemView
            )

            if (webcams.size == 1) {
                // hide buttons and return
                itemView.buttonNextPreview.visibility = View.GONE
                return
            }

            itemView.buttonNextPreview.setOnClickListener{
                webcamNo++
                if (webcamNo >= webcams.size)
                    webcamNo = 0
                loadWebcamPreview(webcams[webcamNo], itemView)
                prefs.edit().putInt(key, webcamNo).apply()
            }

        }

        private fun loadWebcamPreview(webcam: WebcamEntry, itemView: View) {
            itemView.titleWebcam.text = " "
            val mapImageView = itemView.webcamImageView
//            val requestOptions = RequestOptions()
//                .placeholder(R.drawable.ic_image)
//                .error(R.drawable.ic_image)

            Glide.with(mapImageView)
                .load(webcam.previewUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        itemView.titleWebcam.text =
                            itemView.resources.getString(R.string.camera_preview_error)
                        return true
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
//                      val time = CityData.getFormatterCityTZ("yyyy-MM-dd HH:mm a zzzz").format(webcam.update)
//                      itemView.titleWebcam.text ="${webcam.title }\n$time"
                        itemView.titleWebcam.text = webcam.title
                        return false
                    }
                })
//                .apply(requestOptions)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(mapImageView)
        }

    }
}