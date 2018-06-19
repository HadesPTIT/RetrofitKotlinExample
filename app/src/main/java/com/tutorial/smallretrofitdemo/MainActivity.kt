package com.tutorial.smallretrofitdemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.tutorial.smallretrofitdemo.adapter.TrailerAdapter
import com.tutorial.smallretrofitdemo.entity.Video
import com.tutorial.smallretrofitdemo.entity.VideoResponse
import com.tutorial.smallretrofitdemo.network.APIClient
import com.tutorial.smallretrofitdemo.network.APIInterface
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var mCall: Call<VideoResponse>
    private var movieID: Int = 0
    private lateinit var trailerAdapter: TrailerAdapter
    private lateinit var mTrailers: ArrayList<Video>

    companion object {
        val API_KEY = "2d5c8f11ff012edecb1b55ad782b07f3"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        generateIDs()

        mTrailers = ArrayList()
        trailerAdapter = TrailerAdapter(this, mTrailers)
        rvTrailer.adapter = trailerAdapter
        rvTrailer.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


        val mService = APIClient.client.create(APIInterface::class.java)
        mCall = mService.getMovieVideos(movieID, API_KEY)


        mCall.enqueue(object : Callback<VideoResponse> {

            override fun onResponse(call: Call<VideoResponse>, response: Response<VideoResponse>) {
                if (!response.isSuccessful) {
                    mCall = call.clone()
                    mCall.enqueue(this)
                    return
                }
                if (response.body() == null) return
                if (response.body()!!.videos == null) {
                    Toast.makeText(this@MainActivity, "NO VIDEO AVAILABLE", Toast.LENGTH_SHORT).show()
                    return
                }

                response.body()!!.videos!!.filter {
                    it.site != null && it.site.equals("YouTube")
                }.forEach {
                    mTrailers.add(it)
                }
                pbLoading.visibility = View.GONE
                trailerAdapter.notifyDataSetChanged()

            }

            override fun onFailure(call: Call<VideoResponse>?, t: Throwable?) {
                Toast.makeText(this@MainActivity, "FAILED TO LOAD DATA", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun generateIDs() {
        movieID = 383498
    }

}
