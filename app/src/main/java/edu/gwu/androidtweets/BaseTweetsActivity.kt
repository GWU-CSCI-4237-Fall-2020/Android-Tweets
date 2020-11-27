package edu.gwu.androidtweets

import android.location.Address
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

abstract class BaseTweetsActivity : AppCompatActivity() {

    protected lateinit var recyclerView: RecyclerView

    protected lateinit var addTweet: FloatingActionButton

    protected lateinit var tweetContent: EditText

    protected val currentTweets: MutableList<Tweet> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tweets)

        val intent = getIntent()
        val address: Address = intent.getParcelableExtra<Address>("address")!!

        if (savedInstanceState != null) {
            // Restoring from a screen rotation / configuration change
            val savedTweets = savedInstanceState.getSerializable("TWEETS") as List<Tweet>
            currentTweets.addAll(savedTweets)

            val adapter = TweetsAdapter(currentTweets)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this)
        } else {
            // This is the first time the activity is being launched
            retrieveTweets(address)
        }
    }

    abstract fun retrieveTweets(address: Address)

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable("TWEETS", ArrayList(currentTweets))
    }

}