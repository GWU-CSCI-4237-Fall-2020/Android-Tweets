package edu.gwu.androidtweets

import android.location.Address
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import org.jetbrains.anko.doAsync
import java.lang.Exception

class TweetsActivity : BaseTweetsActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addTweet.hide()
        tweetContent.visibility = View.GONE
    }

    override fun retrieveTweets(address: Address) {
        val city = address.locality ?: "Unknown"
        val localizedString = getString(R.string.tweets_title, city)
        setTitle(localizedString)

        doAsync {
            val twitterManager = TwitterManager()
            try {
                val apiKey = getString(R.string.twitter_api_key)
                val apiSecret = getString(R.string.twitter_api_secret)

                val oAuthToken = twitterManager.retrieveOAuthToken(
                    apiKey = apiKey,
                    apiSecret = apiSecret
                )

                val tweets = twitterManager.retrieveTweets(
                    oAuthToken = oAuthToken,
                    latitude = address.latitude,
                    longitude = address.longitude
                )
                val adapter = TweetsAdapter(tweets)

                currentTweets.clear()
                currentTweets.addAll(tweets)

                runOnUiThread {
                    recyclerView.adapter = adapter
                    recyclerView.layoutManager = LinearLayoutManager(this@TweetsActivity)
                }
            } catch (exception: Exception) {
                // OkHttp's execute() function will throw an Exception upon connectivity issues (or errors making the request)
                Log.e("TweetsActivity", "Retrieving Tweets failed", exception)
                runOnUiThread {
                    Toast.makeText(this@TweetsActivity, "Failed to retrieve Tweets!", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}