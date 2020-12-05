package edu.gwu.androidtweets

import android.util.Base64
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import java.net.URLEncoder
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

class TwitterManager {

    val okHttpClient: OkHttpClient

    // An init block allows us to perform extra logic during instance creation (similar to having
    // extra logic in a constructor)
    init {
        val builder = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(loggingInterceptor)

        okHttpClient = builder.build()
    }

    /**
     * Twitter requires us to encodedour API Key and API Secret in a special way for the request.
     *
     * Step 1 for application-only OAuth from:
     * https://developer.twitter.com/en/docs/basics/authentication/oauth-2-0/application-only
     */
    private fun encodeSecrets(
        apiKey: String,
        apiSecret: String
    ) : String {
        // Encoding for a URL -- converts things like spaces into %20
        val encodedKey = URLEncoder.encode(apiKey, "UTF-8")
        val encodedSecret = URLEncoder.encode(apiSecret, "UTF-8")

        // Concatenate the two together, with a colon in-between
        val combinedEncoded = "$encodedKey:$encodedSecret"

        // Base-64 encode the combined string - server expects to have the credentials
        // in the agreed-upon format (generally used for transmitting binary data)
        // https://en.wikipedia.org/wiki/Base64
        return Base64.encodeToString(combinedEncoded.toByteArray(), Base64.NO_WRAP)
    }

    /**
     * All of Twitter's APIs are also protected by OAuth.
     */
    fun retrieveOAuthToken(
        apiKey: String,
        apiSecret: String
    ): String {
        // Twitter requires us to encode our API Key and API Secret in a special way for the request.
        val base64Combined = encodeSecrets(apiKey, apiSecret)

        // Step 2 for application-only OAuth from:
        // https://developer.twitter.com/en/docs/authentication/oauth-2-0/application-only
        //
        // OAuth is defined to be a POST call, which has a specific body / payload to let the server
        // know we are doing "application-only" OAuth (e.g. we will only access public information)
        val requestBody = "grant_type=client_credentials".toRequestBody(
            contentType = "application/x-www-form-urlencoded".toMediaType()
        )

        // The encoded secrets become a header on the request
        val request = Request.Builder()
            .url("https://api.twitter.com/oauth2/token")
            .header("Authorization", "Basic $base64Combined")
            .post(requestBody)
            .build()

        // "Execute" the request (.execute will block the current thread until the server replies with a response)
        val response = okHttpClient.newCall(request).execute()

        // Get the JSON body from the response (if it exists)
        val responseString = response.body?.string()

        // If the response was successful (e.g. status code was a 200) AND the server sent us back
        // some JSON (which will contain the OAuth token), then we can go ahead and parse the JSON body.
        return if (response.isSuccessful && !responseString.isNullOrBlank()) {
            // Set up for parsing the JSON response from the root element
            val json = JSONObject(responseString)

            // Pull out the OAuth token
            json.getString("access_token")
        } else {
            ""
        }
    }

    /**
     * Retrieves Tweets containing the word "Android" in a roughly 30 mile radius around the
     * GPS coordinates that are passed.
     *
     * The Search Tweets API is also protected by OAuth, so a token is required.
     */
    fun retrieveTweets(
        oAuthToken: String,
        latitude: Double,
        longitude: Double
    ): List<Tweet> {
        val searchTerm = "Android"
        val radius = "30mi"

        // Build the request
        // The OAuth token becomes a header on the request
        val request: Request = Request.Builder()
            .url("https://api.twitter.com/1.1/search/tweets.json?q=$searchTerm&geocode=$latitude,$longitude,$radius")
            .header("Authorization", "Bearer $oAuthToken")
            .get()
            .build()

        // "Execute" the request (.execute will block the current thread until the server replies with a response)
        val response: Response = okHttpClient.newCall(request).execute()

        // Create an empty, mutable list to hold up the Tweets we will parse from the JSON
        val tweets = mutableListOf<Tweet>()

        // Get the JSON body from the response (if it exists)
        val responseString = response.body?.string()

        // If the response was successful (e.g. status code was a 200) AND the server sent us back
        // some JSON (which will contain the Tweets), then we can go ahead and parse the JSON body.
        if (response.isSuccessful && !responseString.isNullOrBlank()) {
            // Set up for parsing the JSON response from the root element
            val json = JSONObject(responseString)
            val parsedTweets = SearchTweetsJsonParser().parseJson(json)
            tweets.addAll(parsedTweets)
        } else {
            // Response failed (maybe the server is down)
            // We could throw an Exception here for the Activity, or update the function to return an error-type
        }

        return tweets
    }

}