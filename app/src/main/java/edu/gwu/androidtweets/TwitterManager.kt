package edu.gwu.androidtweets

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject

class TwitterManager {

    val okHttpClient: OkHttpClient

    init {
        val builder = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(loggingInterceptor)

        okHttpClient = builder.build()
    }

    fun retrieveTweets(latitude: Double, longitude: Double): List<Tweet> {
        val searchTerm = "Android"
        val radius = "30mi"

        val request: Request = Request.Builder()
            .url("https://api.twitter.com/1.1/search/tweets.json?q=$searchTerm&geocode=$latitude,$longitude,$radius")
            // This header is required for Twitter's API (we'll learn about it in Lecture 7)
            .header("Authorization", "Bearer AAAAAAAAAAAAAAAAAAAAAJ6N8QAAAAAABppHnTpssd0Hrsdpsi6vYN%2BTfks%3DFY1iVemJdKF5HWRZhQnHRbGpwXJevg3sYyvYC3R53sHCfOJvFk")
            .get()
            .build()

        val response: Response = okHttpClient.newCall(request).execute()

        val tweets = mutableListOf<Tweet>()
        val responseString = response.body?.string()

        if (response.isSuccessful && !responseString.isNullOrBlank()) {
            val json = JSONObject(responseString)
            val statuses = json.getJSONArray("statuses")

            for (i in 0 until statuses.length()) {
                val curr = statuses.getJSONObject(i)
                val text = curr.getString("text")

                val user = curr.getJSONObject("user")
                val name = user.getString("name")
                val handle = user.getString("screen_name")
                val profilePictureUrl = user.getString("profile_image_url")

                val tweet = Tweet(
                    username = name,
                    handle = handle,
                    iconUrl = profilePictureUrl,
                    content = text
                )

                tweets.add(tweet)
            }
        } else {
            // Response failed (maybe the server is down)
            // We could throw an Exception here for the Activity, or update the function to return an error-type
        }

        return tweets
    }

}