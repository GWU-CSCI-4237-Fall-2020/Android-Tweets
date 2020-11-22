package edu.gwu.androidtweets

import android.location.Address
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.jetbrains.anko.doAsync
import java.lang.Exception

class TweetsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    private lateinit var addTweet: FloatingActionButton

    private lateinit var tweetContent: EditText

    private lateinit var firebaseDatabase: FirebaseDatabase

    private val currentTweets: MutableList<Tweet> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tweets)

        firebaseDatabase = FirebaseDatabase.getInstance()

        val intent = getIntent()
        val address: Address = intent.getParcelableExtra<Address>("address")!!

        recyclerView = findViewById(R.id.recyclerView)
        addTweet = findViewById(R.id.add_tweet)
        tweetContent = findViewById(R.id.tweet_content)

        if (savedInstanceState != null) {
            // Restoring from a screen rotation / configuration change
            val savedTweets = savedInstanceState.getSerializable("TWEETS") as List<Tweet>
            currentTweets.addAll(savedTweets)

            val adapter = TweetsAdapter(currentTweets)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this@TweetsActivity)
        } else {
            // This is the first time the activity is being launched
            // getTweetsFromFirebase(address)
            getTweetsFromTwitter(address)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable("TWEETS", ArrayList(currentTweets))
    }

    private fun getTweetsFromFirebase(address: Address) {
        val state = address.adminArea ?: "Unknown"
        val localizedString = getString(R.string.tweets_title, state)
        setTitle(localizedString)

        val reference = firebaseDatabase.getReference("tweets/$state")

        addTweet.setOnClickListener {
            val content: String = tweetContent.text.toString()
            val email: String = FirebaseAuth.getInstance().currentUser!!.email!!
            val tweet = Tweet(
                username = email,
                handle = email,
                iconUrl = "https://i.imgur.com/DvpvklR.png",
                content = content
            )
            reference.push().setValue(tweet)
        }

        reference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    this@TweetsActivity,
                    "Failed to retrieve data from Firebase: ${error.message}",
                    Toast.LENGTH_LONG
                ).show()
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val tweets = mutableListOf<Tweet>()
                snapshot.children.forEach { data ->
                    val tweet = data.getValue(Tweet::class.java)
                    if (tweet != null) {
                        tweets.add(tweet)
                    }
                }

                val adapter = TweetsAdapter(tweets)
                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(this@TweetsActivity)
            }
        })
    }

    private fun getTweetsFromTwitter(address: Address) {
        addTweet.hide()
        tweetContent.visibility = View.GONE

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

    fun getFakeTweets(): List<Tweet> {
        return listOf(
            Tweet(
                handle = "@nickcapurso",
                username = "Nick Capurso",
                content = "We're learning lists!",
                iconUrl = "https://...."
            ),
            Tweet(
                username = "Android Central",
                handle = "@androidcentral",
                content = "NVIDIA Shield TV vs. Shield TV Pro: Which should I buy?",
                iconUrl = "https://...."
            ),
            Tweet(
                username = "DC Android",
                handle = "@DCAndroid",
                content = "FYI - another great integration for the @Firebase platform",
                iconUrl = "https://...."
            ),
            Tweet(
                username = "KotlinConf",
                handle = "@kotlinconf",
                content = "Can't make it to KotlinConf this year? We have a surprise for you. We'll be live streaming the keynotes, closing panel and an entire track over the 2 main conference days. Sign-up to get notified once we go live!",
                iconUrl = "https://...."
            ),
            Tweet(
                username = "Android Summit",
                handle = "@androidsummit",
                content = "What a #Keynote! @SlatteryClaire is the Director of Performance at Speechless, and that's exactly how she left us after her amazing (and interactive!) #keynote at #androidsummit. #DCTech #AndroidDev #Android",
                iconUrl = "https://...."
            ),
            Tweet(
                username = "Fragmented Podcast",
                handle = "@FragmentedCast",
                content = ".... annnnnnnnnd we're back!\n\nThis week @donnfelker talks about how it's Ok to not know everything and how to set yourself up mentally for JIT (Just In Time [learning]). Listen in here: \nhttp://fragmentedpodcast.com/episodes/135/ ",
                iconUrl = "https://...."
            ),
            Tweet(
                username = "Jake Wharton",
                handle = "@JakeWharton",
                content = "Free idea: location-aware physical password list inside a password manager. Mostly for garage door codes and the like. I want to open my password app, switch to the non-URL password section, and see a list of things sorted by physical distance to me.",
                iconUrl = "https://...."
            ),
            Tweet(
                username = "Droidcon Boston",
                handle = "@droidconbos",
                content = "#DroidconBos will be back in Boston next year on April 8-9!",
                iconUrl = "https://...."
            ),
            Tweet(
                username = "AndroidWeekly",
                handle = "@androidweekly",
                content = "Latest Android Weekly Issue 327 is out!\nhttp://androidweekly.net/ #latest-issue  #AndroidDev",
                iconUrl = "https://...."
            ),
            Tweet(
                username = ".droidconSF",
                handle = "@droidconSF",
                content = "Drum roll please.. Announcing droidcon SF 2018! November 19-20 @ Mission Bay Conference Center. Content and programming by @tsmith & @joenrv.",
                iconUrl = "https://...."
            )
        )
    }
}