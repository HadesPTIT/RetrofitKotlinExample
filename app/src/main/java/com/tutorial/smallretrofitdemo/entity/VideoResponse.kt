package com.tutorial.smallretrofitdemo.entity

import com.google.gson.annotations.SerializedName

data class VideoResponse(@field:SerializedName("id") var id: String?,
                         @field:SerializedName("results") var videos: List<Video>?)