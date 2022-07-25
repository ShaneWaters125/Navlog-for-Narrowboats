package solace.narrowboat.ui.dashboard

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.maps.android.data.kml.KmlLayer
import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.pbkdf2.Pack
import solace.narrowboat.MainActivity
import solace.narrowboat.R
import solace.narrowboat.databinding.FragmentChatBinding
import solace.narrowboat.databinding.FragmentDashboardBinding
import java.util.jar.Manifest

class DashboardFragment : Fragment() {

    private lateinit var mapFragment: FragmentDashboardBinding
    lateinit var curLocation: LatLng
    val position = LatLng(52.415470222546176, -4.082938329946786)

    //Google API for location services.
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mapFragment = FragmentDashboardBinding.inflate(inflater, container, false)
        return mapFragment.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Places.initialize(requireContext(), "AIzaSyAdBc0uiFkHzXWuyGqCG3x27-kjMBuXBsU")
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())
        updateGPS()

        val map = mapFragment.mapView
        map.onCreate(null)
        map.getMapAsync {
            MapsInitializer.initialize(requireContext())
            setMapLocation(it)
//            val layer = KmlLayer(it, R.raw.opencanalmap, context)
//            layer.addLayerToMap()
        }
    }

    @SuppressLint("MissingPermission")
    private fun updateGPS(){
        if(checkPermissions()){
                fusedLocationProviderClient.lastLocation.addOnCompleteListener{ task ->
                    getNewLocation()
                }
        } else{
            requestPermissions()
        }

    }

    @SuppressLint("MissingPermission")
    private fun getNewLocation(){
        locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 30000
        locationRequest.fastestInterval = 5000
        fusedLocationProviderClient!!.requestLocationUpdates(
                locationRequest,locationCallback, Looper.myLooper()
        )
    }

    private val locationCallback = object : LocationCallback(){
        override fun onLocationResult(p0: LocationResult) {
            val map = mapFragment.mapView
            var lastLocation: Location = p0.lastLocation
            println(lastLocation.longitude)
            println(lastLocation.latitude)
            curLocation = LatLng(lastLocation.latitude, lastLocation.longitude)
            map.getMapAsync {
                with(it){
                    moveCamera(CameraUpdateFactory.newLatLngZoom(
                            LatLng(lastLocation.latitude, lastLocation.longitude), 16f
                    ))
                    addMarker(MarkerOptions().position(curLocation))
                }
            }
        }
    }


    private fun checkPermissions():Boolean{
        if(ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            return true
        }
        return false
    }

    private fun requestPermissions(){
        ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION), 52)
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
            moveCamera(CameraUpdateFactory.newLatLngZoom(position, 13f))
            addMarker(MarkerOptions().position(position))
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