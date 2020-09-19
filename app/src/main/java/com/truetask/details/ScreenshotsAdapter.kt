package com.truetask.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.truetask.R
import kotlinx.android.synthetic.main.item_screenshot.view.screenshotImageView

class ScreenshotsAdapter(
    private val data: List<String>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).onBind(data[position])
    }

    private class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_screenshot, parent, false)
    ) {

        private val screenshotImageView = itemView.screenshotImageView

        fun onBind(item: String) {
            Glide
                .with(screenshotImageView)
                .load(item.trim())
                .into(screenshotImageView)
        }
    }
}