package edu.gwu.androidtweets

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TweetsAdapter(val tweets: List<Tweet>) : RecyclerView.Adapter<TweetsAdapter.ViewHolder>() {

    // A ViewHolder represents the Views that comprise a single row in our list (e.g.
    // our row to display a Tweet contains three TextViews and one ImageView).
    //
    // The "itemView" passed into the constructor comes from onCreateViewHolder because our LayoutInflater
    // ultimately returns a reference to the root View in the row's inflated layout. From there, we can
    // call findViewById to search from that root View downwards to find the Views we card about.
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val username: TextView = itemView.findViewById(R.id.username)
        val handle: TextView = itemView.findViewById(R.id.handle)
        val content: TextView = itemView.findViewById(R.id.tweet_content)
        val icon: ImageView = itemView.findViewById(R.id.icon)
    }

    // The RecyclerView needs a new row - we need to tell it what XML file to use
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Layout inflation (read & parse XML file and return a reference to the root layout)
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val itemView: View = layoutInflater.inflate(R.layout.row_tweet, parent, false)
        return ViewHolder(itemView)
    }

    // How many rows in total do you want to render?
    override fun getItemCount(): Int {
        return tweets.size
    }

    // The RecyclerView is ready to display a row - fill it with content
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentTweet = tweets[position]
        holder.username.text = currentTweet.username
        holder.handle.text = currentTweet.handle
        holder.content.text = currentTweet.content

        // TODO: Show profile picture (future lecture)
    }
}