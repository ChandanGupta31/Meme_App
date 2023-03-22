package com.example.memeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    private lateinit var image : ImageView
    private lateinit var btn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        image = findViewById(R.id.image)
        btn = findViewById(R.id.btn)

        btn.setOnClickListener {
            val builder = Retrofit.Builder()
                .baseUrl("https://meme-api.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(InterfaceClass::class.java)

            val data = builder.getMeme()
            data.enqueue(object : Callback<Response?> {
                override fun onResponse(
                    call: Call<Response?>,
                    response: retrofit2.Response<Response?>
                ) {
                    val responseBody = response.body()
                    val responseImages = responseBody?.preview!!.toList()
                    val size = responseImages.size
                    val url = responseImages[size-1]
                    Glide.with(this@MainActivity).load(url).into(image)
                }

                override fun onFailure(call: Call<Response?>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "Failed", Toast.LENGTH_LONG).show()
                }

            })


        }

    }
}