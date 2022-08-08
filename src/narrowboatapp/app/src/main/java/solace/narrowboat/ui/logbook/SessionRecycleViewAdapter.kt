package solace.narrowboat.ui.logbook

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import solace.narrowboat.JourneyActivity
import solace.narrowboat.LogbookActivity
import solace.narrowboat.MapActivity
import solace.narrowboat.R
import solace.narrowboat.data.Session

class SessionRecycleViewAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var sessions = ArrayList<Session>()
    private lateinit var parentView: ViewGroup

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SessionViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.viewholder_session, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is SessionRecycleViewAdapter.SessionViewHolder ->{
                holder.bind(sessions[position])

                holder.itemView.findViewById<Button>(R.id.btnOpenLogbook).setOnClickListener {
                    val intent = Intent(holder.itemView.context, LogbookActivity::class.java)
                    holder.itemView.context.startActivity(intent)
                }

                holder.itemView.findViewById<Button>(R.id.btnOpenMap).setOnClickListener {
                    val intent = Intent(holder.itemView.context, MapActivity::class.java)
                    intent.putExtra("id", sessions[position].sid)
                    holder.itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return sessions.size
    }

    fun submitList(sessionList: List<Session>){
        sessions.clear()
        sessions.addAll(sessionList)
        notifyDataSetChanged()
    }

    class SessionViewHolder constructor(
        sessionView: View
    ): RecyclerView.ViewHolder(sessionView){
        private val sessionTime: TextView = sessionView.findViewById(R.id.tvTime)
        private val sessionDate: TextView = sessionView.findViewById(R.id.tvDate)
        private val sessionDistance: TextView = sessionView.findViewById(R.id.tvDistance)
        private val sessionBoat: TextView = sessionView.findViewById(R.id.tvBoatName)

        @SuppressLint("SetTextI18n")
        fun bind(session: Session){
            sessionTime.text = session.startTime + " - " + session.endTime
            sessionDate.text = session.date
            sessionDistance.text = session.distance
            sessionBoat.text = session.boatname
        }
    }

}