package solace.narrowboat.ui.settings

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import solace.narrowboat.BoatActivity
import solace.narrowboat.JourneyActivity
import solace.narrowboat.R
import solace.narrowboat.databinding.FragmentDashboardBinding
import solace.narrowboat.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    private lateinit var settingsFragment: FragmentSettingsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        settingsFragment = FragmentSettingsBinding.inflate(inflater, container, false)
        return settingsFragment.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initButtons()
    }

    private fun initButtons(){
        val btnViewBoats = settingsFragment.btnViewBoats
        btnViewBoats.setOnClickListener {
            val intent = Intent(requireContext(), BoatActivity::class.java)
            requireContext().startActivity(intent)
        }
    }

}