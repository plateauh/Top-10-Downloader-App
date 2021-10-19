package com.najed.top10downloaderapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.najed.top10downloaderapp.model.Feed
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var retrofit: Retrofit
    private lateinit var call: Call<Feed?>
    private lateinit var feed: Feed
    private val BASE_URL = "http://ax.itunes.apple.com/"

    private lateinit var feedTitleTextView: TextView
    private lateinit var appsRecyclerView: RecyclerView
    private lateinit var updateBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        feedTitleTextView = findViewById(R.id.feed_title)

        appsRecyclerView = findViewById(R.id.apps_rv)
        appsRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity)

        updateBtn = findViewById(R.id.update_btn)
        updateBtn.setOnClickListener {
            CoroutineScope(IO).launch {
                setData(10)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.top_10_item -> {
                updateBtn.setOnClickListener {
                    CoroutineScope(IO).launch {
                        setData(10)
                    }
                }
            }
            R.id.top_100_item -> {
                updateBtn.setOnClickListener {
                    CoroutineScope(IO).launch {
                        setData(100)
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private suspend fun setData(itemsNumber: Int) {
        retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build()
        call = when (itemsNumber) {
            10 -> retrofit.create(FeedAPIInterface::class.java).top10feed!!
            100 -> retrofit.create(FeedAPIInterface::class.java).top100feed!!
            else -> retrofit.create(FeedAPIInterface::class.java).top10feed!!
        }
        try {
            feed = call.execute().body()!!
            setUI()
        } catch (e: Exception) {
            withContext(Main) {
                Toast.makeText(this@MainActivity, "Something went wrong, please try again", Toast.LENGTH_SHORT).show()
            }
            e.printStackTrace()
        }
    }

    private suspend fun setUI() {
        withContext(Main) {
            feedTitleTextView.text = feed.title
            appsRecyclerView.adapter = Adapter(feed.entries!!)
        }
    }
}