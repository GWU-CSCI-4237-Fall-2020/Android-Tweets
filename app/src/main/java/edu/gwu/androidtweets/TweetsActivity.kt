package edu.gwu.androidtweets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.setPadding

class TweetsActivity : AppCompatActivity() {

    private lateinit var rootLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tweets)
        rootLayout = findViewById(R.id.rootLayout)

        // ... do some operation to retrieve data ...
        val fakeData = listOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5")

        fakeData.forEach{ data ->
            val newRow = createListRowFromFile(data)
            rootLayout.addView(newRow)
        }
    }

    /**
     * Programmatically creates rows
     */
    fun createListRow(itemLabel: String): View {
        // Calculates 8dp in pixels, based on screen density, using a helper function
        val dpToPx_8 = dpToPx(8.0f).toInt()

        // Create horizontal LinearLayout
        val linearLayout = LinearLayout(this)
        linearLayout.orientation = LinearLayout.HORIZONTAL
        linearLayout.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, // Width
            LinearLayout.LayoutParams.WRAP_CONTENT  // Height
        )
        linearLayout.setPadding(dpToPx_8)

        // Create CheckBox
        val checkBox = CheckBox(this)
        checkBox.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT, // Width
            LinearLayout.LayoutParams.WRAP_CONTENT  // Height
        )

        // Create TextView
        val textView = TextView(this)
        val textViewLayoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT, // Width
            LinearLayout.LayoutParams.WRAP_CONTENT  // Height
        )
        textViewLayoutParams.marginStart = dpToPx_8
        textView.layoutParams = textViewLayoutParams
        textView.text = itemLabel

        linearLayout.addView(checkBox)
        linearLayout.addView(textView)

        return linearLayout
    }

    /**
     * Hybrid of using XML + code to generate rows
     */
    fun createListRowFromFile(itemLabel: String): View {
        // A LayoutInflater is an object that "knows" how to read & parse XML layouts ("inflation")
        val layoutInflater = LayoutInflater.from(this)

        // Read the row from XML
        val inflatedRow = layoutInflater.inflate(R.layout.row_selection, rootLayout, false)

        // Set the correct text for the row item
        val textView = inflatedRow.findViewById<TextView>(R.id.item)
        textView.text = itemLabel

        return inflatedRow
    }


    fun dpToPx(dp: Float): Float {
        return dp * resources.displayMetrics.density
    }
}