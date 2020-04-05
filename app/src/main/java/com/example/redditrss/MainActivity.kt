package com.example.redditrss

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.redditrss.entry.Entry
import com.example.redditrss.model.Feed
import kotlinx.android.synthetic.main.activity_main.editText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

const val BASE_URL = "https://www.reddit.com/r/"
const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var inputText: EditText = findViewById(R.id.editText)
        var refreshButton: Button = findViewById(R.id.refreshButton)

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build()

        val feedApi: FeedApi = retrofit.create(FeedApi::class.java)

        refreshButton.setOnClickListener {

            val call: Call<Feed>? = feedApi.loadFeed(editText.text.toString())
            call?.enqueue(object : Callback<Feed> {
                override fun onFailure(call: Call<Feed>, t: Throwable) {
                    Log.e(TAG, "Unable to recieve log message" + t.message)
                    Toast.makeText(this@MainActivity, "An error occued", Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<Feed>, response: Response<Feed>) {
                    Log.d(TAG, "Response Feed" + response.body()?.toString())
                    Log.d(TAG, "Response server $response")
                    Toast.makeText(this@MainActivity, "Successful", Toast.LENGTH_LONG).show()

                    val posts: ArrayList<Post?> = ArrayList()

                    val entries: List<Entry> = response.body()?.entries ?: emptyList()
                    for (entry in entries) {
                        val extractXMLHref = ExtractXML("<a href=", entry.content)
                        val postContent = extractXMLHref.start()
                        val extractXMLImg = ExtractXML("<img src=", entry.content)
                        val imgContent = extractXMLImg.start()
                        if (imgContent.isNotEmpty()) {
                            postContent.add(imgContent.get(0))
                        }
                        posts.add(Post(entry.title, entry.author?.name, entry.updated, postContent.get(0), postContent.get(postContent.size - 1)))
                    }

                    val listView: ListView = findViewById(R.id.listView)
                    val customListAdapter = CustomListAdapter(this@MainActivity, R.layout.card_layout, posts)
                    listView.adapter = customListAdapter
                }
            })
        }
    }
}
