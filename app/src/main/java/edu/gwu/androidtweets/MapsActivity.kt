package edu.gwu.androidtweets

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.jetbrains.anko.doAsync

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    private lateinit var confirm: Button

    private var currentAddress: Address? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        confirm = findViewById(R.id.confirm)
        confirm.setOnClickListener {
            if (currentAddress != null) {
                val intent = Intent(this@MapsActivity, TweetsActivity::class.java)
                intent.putExtra("address", currentAddress)
                startActivity(intent)
            }
        }
        confirm.isEnabled = false

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     *
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.setOnMapLongClickListener { coords: LatLng ->
            mMap.clear()

            // Geocoding needs to be performed on a background thread, otherwise we run the risk
            // of freezing the app (the UI Thread) if it takes too long.
            doAsync {
                val geocoder = Geocoder(this@MapsActivity)
                val results: List<Address> = try {
                    geocoder.getFromLocation(
                        coords.latitude,
                        coords.longitude,
                        10
                    )
                } catch (exception: Exception) {
                    Log.e("MapsActivity", "Geocoding failed", exception)
                    listOf()
                }

                // Now that we have results, let's hop back onto the UI Thread since we can only perform
                // UI events (like dropping map markers) if we're on the UI Thread.
                runOnUiThread {
                    if (results.isNotEmpty()) {
                        // Just grab the first result - we're not super concerned with geocoding accuracy here
                        // and the first result may be the one with the "highest confidence"
                        val firstResult: Address = results.first()
                        val streetAddress: String = firstResult.getAddressLine(0)

                        mMap.addMarker(
                            MarkerOptions()
                                .position(coords)
                                .title(streetAddress)
                        )

                        mMap.animateCamera(
                            CameraUpdateFactory.newLatLng(coords)
                        )

                        updateConfirmButton(firstResult)
                    } else {
                        Log.e("MapsActivity", "Geocoding failed or returned no results")
                        Toast.makeText(
                            this@MapsActivity,
                            "No results for location!",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }

    /**
     * Flips the button color from red --> green and updates the icon
     */
    private fun updateConfirmButton(address: Address) {
        val greenColor = getColor(R.color.buttonGreen)
        val checkIcon = getDrawable(R.drawable.ic_baseline_check_24)

        confirm.setBackgroundColor(greenColor)
        confirm.setCompoundDrawablesWithIntrinsicBounds(checkIcon, null, null, null)
        confirm.text = address.getAddressLine(0)
        confirm.isEnabled = true

        currentAddress = address
    }
}