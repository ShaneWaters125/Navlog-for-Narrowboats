package solace.narrowboat

import android.app.WallpaperColors.fromBitmap
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import solace.narrowboat.data.DatabaseHandler

class MapActivity : AppCompatActivity() {

    private lateinit var map: MapView
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationManager: LocationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        supportActionBar?.hide()

        Places.initialize(this, "AIzaSyAdBc0uiFkHzXWuyGqCG3x27-kjMBuXBsU")
        locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        map = findViewById(R.id.mvLoadedSession)
        map.onCreate(null)
        map.getMapAsync {
            MapsInitializer.initialize(this)
        }

        initButtons()
        addMapMarkers()
    }

    private fun initButtons(){
        val btnBack = findViewById<ImageButton>(R.id.ibBackLoadedSession)
        btnBack.setOnClickListener {
            finish()
        }
    }

    private fun addMapMarkers(){
        val id = intent.getIntExtra("id", 0)
        var databaseHandler = DatabaseHandler(this)
        var markers = databaseHandler.viewMarkers(id)
        var firstLocation: LatLng = LatLng(0.0,0.0)
        for(marker in markers){
            var mapMarker = LatLng(marker.latitude, marker.longitude)
            firstLocation = mapMarker
            map.getMapAsync {
                with(it){
                    addMarker(MarkerOptions().position(mapMarker).title(marker.time).icon(BitmapDescriptorFactory.fromResource(R.raw.circlemarker)))
                }
            }
        }
        map.getMapAsync {
            with(it){
                moveCamera(
                    CameraUpdateFactory.newLatLngZoom(
                    firstLocation, 13f
                ))
            }
        }
    }

    override fun onResume() {
        super.onResume()
        findViewById<MapView>(R.id.mvLoadedSession).onResume()
    }

    override fun onPause() {
        super.onPause()
        findViewById<MapView>(R.id.mvLoadedSession).onPause()
    }

    override fun onDestroy() {

        super.onDestroy()
        findViewById<MapView>(R.id.mvLoadedSession).onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        findViewById<MapView>(R.id.mvLoadedSession).onLowMemory()
    }

}