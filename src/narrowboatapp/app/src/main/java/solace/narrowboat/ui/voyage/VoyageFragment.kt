package solace.narrowboat.ui.voyage

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import solace.narrowboat.MainActivity
import solace.narrowboat.R
import solace.narrowboat.data.DatabaseHandler
import solace.narrowboat.data.Journey
import solace.narrowboat.databinding.FragmentVoyageBinding
import solace.narrowboat.services.LocationService


class VoyageFragment : Fragment() {

    private var isFocused = false
    private lateinit var voyageFragment: FragmentVoyageBinding
    private var MIN_DISTANCE: Float = 1F
    private var MIN_TIME: Long = 1000

    //Google API for location services.
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest

    private lateinit var locationManager: LocationManager

    private var started = false
    private var handler = Handler(Looper.getMainLooper())

    private lateinit var runnable: Runnable

    private var locationBound: Boolean = false

    private lateinit var map: MapView
    //Made these global variables so when the fragment is killed the app still keeps them initialised.
    //    private lateinit var locationService: LocationService
//    private lateinit var selectedJourney: Journey
//    private lateinit var intent: Intent

    private val connection = object : ServiceConnection{
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as LocationService.MyBinder
            MainActivity.locationService = binder.getService()
            locationBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            locationBound = false
        }
    }


    @SuppressLint("MissingPermission")
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        Places.initialize(requireActivity().application, "AIzaSyAdBc0uiFkHzXWuyGqCG3x27-kjMBuXBsU")
        voyageFragment = FragmentVoyageBinding.inflate(inflater, container, false)

        if(checkPermission(Manifest.permission.ACCESS_BACKGROUND_LOCATION, 200)){
            warningDialog()
        } else{
            locationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE, LocationUpdateListener())
        }
        return voyageFragment.root
    }

    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        if(checkPermission(Manifest.permission.ACCESS_BACKGROUND_LOCATION, 200)){
            warningDialog()
        } else{
            isFocused = true
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())

            initButtons()
        }

        map = voyageFragment.mapView2
        map.onCreate(null)
        map.getMapAsync {
            with(it){
                isMyLocationEnabled = true
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun initButtons(){
        val startBtn = voyageFragment.btnStartVoyage
        val stopBtn = voyageFragment.btnStopVoyage
        val pauseBtn = voyageFragment.btnPauseVoyage
        val logbookBtn = voyageFragment.btnLogbookVoyage
        val resumeBtn = voyageFragment.btnResumeVoyage

        if(MainActivity.isVoyageStarted){
            startBtn.visibility = View.INVISIBLE
            stopBtn.visibility = View.VISIBLE
            pauseBtn.visibility = View.VISIBLE
            logbookBtn.visibility = View.VISIBLE

            if(MainActivity.isPaused){
                resumeBtn.visibility = View.VISIBLE
            } else{
                resumeBtn.visibility = View.INVISIBLE
            }

        }

        startBtn.setOnClickListener {
            journeyDialog()
        }

        stopBtn.setOnClickListener {

            MainActivity.locationService.setJourneyId(MainActivity.selectedJourney.id)
            MainActivity.journeyIntent.action = "StopService"
            requireActivity().startService(MainActivity.journeyIntent)
            MainActivity.isVoyageStarted = false

            startBtn.visibility = View.VISIBLE
            stopBtn.visibility = View.INVISIBLE
            pauseBtn.visibility = View.INVISIBLE
            logbookBtn.visibility = View.INVISIBLE
            resumeBtn.visibility = View.INVISIBLE

        }

        pauseBtn.setOnClickListener {
            MainActivity.isPaused = true
            MainActivity.locationService.pauseTracking()
            pauseBtn.visibility = View.INVISIBLE
            resumeBtn.visibility = View.VISIBLE
        }

        resumeBtn.setOnClickListener {
            MainActivity.isPaused = false
            MainActivity.locationService.resumeTracking()
            pauseBtn.visibility = View.VISIBLE
            resumeBtn.visibility = View.INVISIBLE
        }


    }

    // Function to check and request permission.
    private fun checkPermission(permission: String, requestCode: Int): Boolean {
        if (ContextCompat.checkSelfPermission(requireActivity(), permission) == PackageManager.PERMISSION_DENIED) {
            // Requesting the permission
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(permission), requestCode)
            return true
        }else{
            return false
        }
    }

    private fun warningDialog(){
        val warningDialog = Dialog(requireContext(), R.style.DialogTheme)
        warningDialog.setContentView(R.layout.dialog_locationwarning)
        warningDialog.setCanceledOnTouchOutside(false)
        warningDialog.setCancelable(false)
        warningDialog.show()
    }

    private fun journeyDialog(){
        val startBtn = voyageFragment.btnStartVoyage
        val stopBtn = voyageFragment.btnStopVoyage
        val pauseBtn = voyageFragment.btnPauseVoyage
        val logbookBtn = voyageFragment.btnLogbookVoyage
        val resumeBtn = voyageFragment.btnResumeVoyage

        val journeyDialog = Dialog(requireContext(), R.style.DialogTheme)
        journeyDialog.setContentView(R.layout.dialog_selectjourney)
        val databaseHandler = DatabaseHandler(requireContext())

        val journeys = databaseHandler.viewJourneys()
        val journeyStringArray = ArrayList<String>()
        for(journey in journeys){
            journeyStringArray.add(journey.name)
        }

        val spinnerJourney: Spinner = journeyDialog.findViewById(R.id.spnJourneys)
        val spinnerAdapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_dropdown_item, journeyStringArray)
        spinnerJourney.adapter = spinnerAdapter

        val btnSelectJourney: Button = journeyDialog.findViewById(R.id.btnSelectJourney)
        btnSelectJourney.setOnClickListener {
            MainActivity.journeyIntent = Intent(requireActivity(), LocationService::class.java).also{ intent ->
                requireActivity().bindService(intent, connection, Context.BIND_AUTO_CREATE)
            }
            requireActivity().startForegroundService(MainActivity.journeyIntent)

            MainActivity.selectedJourney = journeys[spinnerJourney.selectedItemPosition]

            resumeBtn.visibility = View.INVISIBLE
            startBtn.visibility = View.INVISIBLE
            stopBtn.visibility = View.VISIBLE
            pauseBtn.visibility = View.VISIBLE
            logbookBtn.visibility = View.VISIBLE

            databaseHandler.close()
            MainActivity.isVoyageStarted = true
            journeyDialog.dismiss()
        }

        journeyDialog.setCanceledOnTouchOutside(false)
        journeyDialog.setCancelable(false)
        journeyDialog.show()
    }



//    private fun checkPermissions():Boolean{
//        if(ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
//            return true
//        }
//        return false
//    }

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

    override fun onResume() {
        super.onResume()
        if(MainActivity.isVoyageStarted){
            val startBtn = voyageFragment.btnStartVoyage
            val stopBtn = voyageFragment.btnStopVoyage
            val pauseBtn = voyageFragment.btnPauseVoyage
            val logbookBtn = voyageFragment.btnLogbookVoyage
            val resumeBtn = voyageFragment.btnResumeVoyage
            if(MainActivity.isPaused){
                resumeBtn.visibility = View.VISIBLE
            } else{
                resumeBtn.visibility = View.INVISIBLE
            }


            startBtn.visibility = View.INVISIBLE
            stopBtn.visibility = View.VISIBLE
            pauseBtn.visibility = View.VISIBLE
            logbookBtn.visibility = View.VISIBLE
        }
        isFocused = true
        voyageFragment.mapView2.onResume()
    }

    override fun onPause() {
        super.onPause()
        isFocused = false
        voyageFragment.mapView2.onPause()
    }

    override fun onDestroy() {

        super.onDestroy()
        voyageFragment.mapView2.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        voyageFragment.mapView2.onLowMemory()
    }

    inner class LocationUpdateListener : LocationListener{
        override fun onLocationChanged(location: Location) {
            if(isFocused){
                map.getMapAsync {
                    with(it){
                        moveCamera(CameraUpdateFactory.newLatLngZoom(
                                LatLng(location.latitude, location.longitude), 16f
                        ))
                        // INTENT DOES NOT WORK WHEN THE APP IS RELOADED AS IT IS RESET WHEN LOADED BUT THE SERVICE IS STILL RUNNING RESULTING IN MARKERS NOT BEING ADDED AFTER OPENING THE APP AGAIN.
//                        if(MainActivity.journeyIntent.isInitialized){
//                            var locations = MainActivity.locationService.getPositions()
//                            if(locations.isNotEmpty()){
//                                addMarker(MarkerOptions().position(LatLng(locations[locations.size-1].latitude, locations[locations.size-1].longitude)))
//                            }
//                        }
                    }
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
//        requireContext().unbindService(connection)
        locationBound = false
    }


}