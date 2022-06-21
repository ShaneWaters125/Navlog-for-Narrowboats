package solace.messageapp.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import solace.messageapp.R
import solace.messageapp.databinding.FragmentChatBinding
import solace.messageapp.databinding.FragmentNotificationsBinding

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