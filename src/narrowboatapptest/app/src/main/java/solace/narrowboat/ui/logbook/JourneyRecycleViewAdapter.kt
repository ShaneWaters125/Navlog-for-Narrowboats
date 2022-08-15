package solace.narrowboat.ui.logbook

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import solace.narrowboat.JourneyActivity
import solace.narrowboat.MainActivity
import solace.narrowboat.R
import solace.narrowboat.data.DatabaseHandler
import solace.narrowboat.data.Journey
import solace.narrowboat.ui.voyage.VoyageFragment

class JourneyRecycleViewAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var journeys = ArrayList<Journey>()
    private lateinit var parentView: ViewGroup

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return JourneyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.viewholder_journey, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is JourneyRecycleViewAdapter.JourneyViewHolder ->{
                holder.bind(journeys[position])

                holder.itemView.setOnClickListener{
                    //Open Journey
                    val intent = Intent(holder.itemView.context, JourneyActivity::class.java)
                    intent.putExtra("id", journeys[position].id)
                    intent.putExtra("name", journeys[position].name)
                    holder.itemView.context.startActivity(intent)
                }

                holder.itemView.findViewById<ImageButton>(R.id.ibEditJourney).setOnClickListener {
                    editJourneyDialog(holder.itemView.context, position)
                }

                holder.itemView.findViewById<ImageButton>(R.id.ibDeleteJourney).setOnClickListener {
                    deleteJourneyDialog(holder.itemView.context, position)
                }

                holder.itemView.findViewById<ImageButton>(R.id.ibFakeOpenJourney).setOnClickListener {
                    //Open Journey
                    val intent = Intent(holder.itemView.context, JourneyActivity::class.java)
                    intent.putExtra("id", journeys[position].id)
                    intent.putExtra("name", journeys[position].name)
                    holder.itemView.context.startActivity(intent)
                }

            }
        }
    }

    private fun editJourneyDialog(context: Context, position: Int){
        val editJourneyDialog = Dialog(context, R.style.DialogTheme)
        editJourneyDialog.setContentView(R.layout.dialog_editjourney)

        //Initialises the close button.
        val btnClose: ImageButton = editJourneyDialog.findViewById(R.id.ibEditJourneyClose)
        btnClose.setOnClickListener(View.OnClickListener {
            editJourneyDialog.dismiss()
        })

        editJourneyDialog.show()
    }

    private fun deleteJourneyDialog(context: Context, position: Int){
        val deleteJourneyDialog = Dialog(context, R.style.DialogTheme)
        deleteJourneyDialog.setContentView(R.layout.dialog_deletejourney)


        val btnYes: Button = deleteJourneyDialog.findViewById(R.id.btnDeleteJourneyYes)
        btnYes.setOnClickListener(View.OnClickListener {
            val databaseHandler = DatabaseHandler(context)
            databaseHandler.deleteJourney(journeys[position].id)
            journeys.removeAt(position)
            notifyItemRemoved(position)
            notifyDataSetChanged()
            deleteJourneyDialog.dismiss()
        })

        val btnNo: Button = deleteJourneyDialog.findViewById(R.id.btnDeleteJourneyNo)
        btnNo.setOnClickListener(View.OnClickListener {
            //No
            deleteJourneyDialog.dismiss()
        })

        deleteJourneyDialog.show()
    }

    override fun getItemCount(): Int {
        return journeys.size
    }

    fun submitList(journeyList: List<Journey>){
        journeys.clear()
        journeys.addAll(journeyList)
        notifyDataSetChanged()
    }

    class JourneyViewHolder constructor(
            journeyView: View
    ): RecyclerView.ViewHolder(journeyView){
        private val journeyName: TextView = journeyView.findViewById(R.id.tvJourneyName)

        fun bind(journey: Journey){
            journeyName.text = journey.name

        }
    }

}