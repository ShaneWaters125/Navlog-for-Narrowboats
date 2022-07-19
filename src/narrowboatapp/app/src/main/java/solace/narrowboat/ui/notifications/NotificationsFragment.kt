package solace.narrowboat.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import solace.narrowboat.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment() {

    private lateinit var notificationsFragment: FragmentNotificationsBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        notificationsFragment = FragmentNotificationsBinding.inflate(inflater, container, false)
        return notificationsFragment.root
    }
}