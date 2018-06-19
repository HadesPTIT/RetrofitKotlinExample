package com.tutorial.smallretrofitdemo.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.tutorial.smallretrofitdemo.Constant
import com.tutorial.smallretrofitdemo.R
import com.tutorial.smallretrofitdemo.entity.Video
import kotlinx.android.synthetic.main.item_video.view.*

class TrailerAdapter(private val mCtx : Context, private val mVideos : List<Video>) : RecyclerView.Adapter<TrailerAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rootView = LayoutInflater.from(mCtx).inflate(R.layout.item_video,parent,false)
        return ViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Glide.with(mCtx.applicationContext).load(Constant.YOUTUBE_THUMBNAIL_BASE_URL + mVideos[position].key + Constant.YOUTUBE_THUMBNAIL_IMAGE_QUALITY)
                .asBitmap()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.itemView.imvTrailer!!)

        if (mVideos[position].name != null) {
            holder.itemView.tvTrailer.text = mVideos[position].name
        } else {
            holder.itemView.tvTrailer.text = ""
        }
    }

    override fun getItemCount(): Int {
        return mVideos.size
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        init {

            itemView.cvTrailer.setOnClickListener {
                val youtubeIntent = Intent(Intent.ACTION_VIEW, Uri.parse(Constant.YOUTUBE_WATCH_BASE_URL + mVideos[adapterPosition].key))
                mCtx.startActivity(youtubeIntent)
            }

        }
    }

}