package edu.gwu.androidtweets

import android.location.Address
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class TweetsActivity : BaseTweetsActivity() {

    private lateinit var firebaseDatabase: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseDatabase = FirebaseDatabase.getInstance()
    }

    override fun retrieveTweets(address: Address) {
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
}