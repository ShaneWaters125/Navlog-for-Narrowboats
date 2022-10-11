package solace.narrowboat.ui.explorer

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.maps.android.data.kml.KmlContainer
import com.google.maps.android.data.kml.KmlLayer
import com.google.maps.android.data.kml.KmlPlacemark
import com.google.maps.android.data.kml.KmlPoint
import solace.narrowboat.MainActivity
import solace.narrowboat.databinding.FragmentDashboardBinding
import solace.narrowboat.R

class ExplorerFragment : Fragment() {

    private lateinit var mapFragment: FragmentDashboardBinding
    lateinit var curLocation: LatLng

    //Google API for location services.
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)

        mapFragment = FragmentDashboardBinding.inflate(inflater, container, false)
        return mapFragment.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Places.initialize(requireContext(), "AIzaSyAdBc0uiFkHzXWuyGqCG3x27-kjMBuXBsU")
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())

        val map = mapFragment.mapView
        map.onCreate(null)
        map.getMapAsync {

            MapsInitializer.initialize(requireContext())
            setMapLocation(it)

            var layer: KmlLayer?

            val run = Runnable {
                layer = KmlLayer(it, R.raw.opencanalmap, context)
                if(Thread.interrupted()){
                    MainActivity.run {
                        activity?.runOnUiThread {
                            layer?.addLayerToMap()
                        }
                    }
                    return@Runnable
                }
            }

            val thread = Thread(run)
            thread.start()
            thread.interrupt()

        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(requestCode == 52){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Log.d("Debug:", "Valid Permissions")
            }
        }
    }

    private fun setMapLocation(map : GoogleMap){
        with(map){
//            moveCamera(CameraUpdateFactory.newLatLngZoom(position, 13f))
            mapType = GoogleMap.MAP_TYPE_NORMAL
            setOnMapClickListener {
                Toast.makeText(context, "Clicked on map", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mapFragment.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapFragment.mapView.onPause()
    }

    override fun onDestroy() {

        super.onDestroy()
        mapFragment.mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapFragment.mapView.onLowMemory()
    }
}