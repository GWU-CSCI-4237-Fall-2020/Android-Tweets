package edu.gwu.androidtweets

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class TweetsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tweets)

        val intent = getIntent()
        val locationName = intent.getStringExtra("LOCATION")
        val localizedString = getString(R.string.tweets_title, locationName)
        setTitle(localizedString)
    }
}