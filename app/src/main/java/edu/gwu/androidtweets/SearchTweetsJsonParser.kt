package edu.gwu.androidtweets

import org.json.JSONObject

class SearchTweetsJsonParser {
    fun parseJson(json: JSONObject): List<Tweet> {
        val tweets = mutableListOf<Tweet>()

        // The list of Tweets will be within the statuses array
        val statuses = json.getJSONArray("statuses")

        // Loop thru the statuses array and parse each individual list, adding it to our `tweets`
        // list which we will return at the end.
        for (i in 0 until statuses.length()) {
            val curr = statuses.getJSONObject(i)
            val text = curr.getString("text")

            val user = curr.getJSONObject("user")
            val name = user.getString("name")
            val handle = user.getString("screen_name")
            val profilePictureUrl = user.getString("profile_image_url_https")

            val tweet = Tweet(
                username = name,
                handle = handle,
                iconUrl = profilePictureUrl,
                content = text
            )

            tweets.add(tweet)
        }

        return tweets
    }
}