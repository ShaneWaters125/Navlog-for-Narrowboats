package solace.narrowboat.services

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Binder
import android.os.IBinder
import androidx.core.app.NotificationCompat
import solace.narrowboat.MainActivity
import solace.narrowboat.R
import solace.narrowboat.data.DatabaseHandler
import solace.narrowboat.data.MarkerPosition
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates

class LocationService : Service(), LocationListener {

    private var locations = arrayListOf<MarkerPosition>()

    private var MIN_DISTANCE: Float = 1F
    private var MIN_TIME: Long = 10000
    private lateinit var locationManager: LocationManager
    private var longitude = 0.0
    private var latitude = 0.0
    private lateinit var startTime: String

    private var journeyid: Int = 0

    var binder = MyBinder()
    lateinit var services: LocationService

    @SuppressLint("MissingPermission")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if(intent != null){
            if(intent.action == "StopService"){
                val databaseHandler = DatabaseHandler(this)
                val currentTime: String = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
                val currentDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
                databaseHandler.addSession(journeyid, startTime, currentTime, currentDate, "5", "A cool boat")

                for(location in locations){
                    databaseHandler.addMarker(location.latitude.toString(), location.longitude.toString(), databaseHandler.getMostRecentSession(), location.time)
                }

                databaseHandler.close()
                this.onDestroy()
            } else{
                locations.clear()
                startTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
                var notificationChannel = NotificationChannel("1", "Location Service", NotificationManager.IMPORTANCE_DEFAULT)
                var notificationManager = getSystemService(NotificationManager::class.java)
                notificationManager.createNotificationChannel(notificationChannel)

                var intent1 = Intent(this, MainActivity::class.java)
                var pendingIntent = PendingIntent.getActivity(this, 0, intent1, 0)
                var notification = NotificationCompat.Builder(this, "1").setContentTitle("Location Service").setContentText("Tracking your location").setSmallIcon(R.mipmap.ic_launcher).setContentIntent(pendingIntent).build()
                startForeground(1, notification)

                locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE, this)
            }
        }

      return START_STICKY
//        return START_NOT_STICKY
    }

    override fun onDestroy() {
        stopForeground(true)
        stopSelf()
        locationManager.removeUpdates(this)
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }

    override fun onLocationChanged(location: Location) {
        println(location.longitude)
        println(location.latitude)
        longitude = location.longitude
        latitude = location.latitude
        val currentTime: String = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
        locations.add(MarkerPosition(location.latitude, location.longitude, currentTime))
    }

    override fun onProviderEnabled(provider: String) {
        super.onProviderEnabled(provider)
    }

    override fun onProviderDisabled(provider: String) {
        super.onProviderDisabled(provider)
    }

    inner class MyBinder: Binder(){
        fun getService(): LocationService = this@LocationService
    }

    fun getPositions(): List<MarkerPosition>{
        return locations
    }

    fun setJourneyId(id: Int){
        journeyid = id
    }

    fun pauseTracking(){
        locationManager.removeUpdates(this)
    }

    @SuppressLint("MissingPermission")
    fun resumeTracking(){
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE, this)
    }
}