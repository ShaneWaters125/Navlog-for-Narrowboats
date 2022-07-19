package solace.narrowboat.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.data.kml.KmlLayer
import solace.narrowboat.R
import solace.narrowboat.databinding.FragmentChatBinding
import solace.narrowboat.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private lateinit var mapFragment: FragmentDashboardBinding
    val position = LatLng(52.415470222546176, -4.082938329946786)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mapFragment = FragmentDashboardBinding.inflate(inflater, container, false)
        return mapFragment.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val map = mapFragment.mapView
        map.onCreate(null)
        map.getMapAsync {
            MapsInitializer.initialize(requireContext())
            setMapLocation(it)
            val layer = KmlLayer(it, R.raw.opencanalmap, context)
            layer.addLayerToMap()
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