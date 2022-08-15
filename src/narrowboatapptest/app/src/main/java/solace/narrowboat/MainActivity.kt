package solace.narrowboat

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import solace.narrowboat.data.Journey
import solace.narrowboat.services.LocationService

class MainActivity : AppCompatActivity() {

    companion object{
        var isVoyageStarted = false
        var isPaused = false
        lateinit var journeyIntent: Intent
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_journey, R.id.navigation_explorer, R.id.navigation_voyage, R.id.navigation_settings))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        //API 30+ does not give the open for asking for location all the time.
        checkPermission(Manifest.permission.ACCESS_BACKGROUND_LOCATION, 200)
        checkPermission(Manifest.permission.FOREGROUND_SERVICE, 201)
        checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION, 202)
        checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, 203)
        checkPermission(Manifest.permission.INTERNET, 204)
        checkPermission(Manifest.permission.ACCESS_NETWORK_STATE, 205)
    }

    // Function to check and request permission.
    private fun checkPermission(permission: String, requestCode: Int): Boolean {
        return if (ContextCompat.checkSelfPermission(this@MainActivity, permission) == PackageManager.PERMISSION_DENIED) {
            // Requesting the permission
            ActivityCompat.requestPermissions(this@MainActivity, arrayOf(permission), requestCode)
            false
        }else{
            true
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}