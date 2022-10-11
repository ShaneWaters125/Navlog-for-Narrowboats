package solace.narrowboat.ui.settings

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import solace.narrowboat.R
import solace.narrowboat.data.Boat

class BoatRecyleViewAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var boats = ArrayList<Boat>()
    private lateinit var parentView: ViewGroup

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BoatViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.viewholder_boat, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is BoatRecyleViewAdapter.BoatViewHolder -> {
                holder.bind(boats[position])

                holder.itemView.setOnClickListener{
                    viewBoatDialog(holder.itemView.context, position)
                }

                holder.itemView.findViewById<ImageButton>(R.id.ibFakeOpenBoat).setOnClickListener{
                    viewBoatDialog(holder.itemView.context, position)
                }
            }
        }
    }

    fun viewBoatDialog(context: Context, position: Int){
        val viewBoatDialog = Dialog(context, R.style.DialogTheme)
        viewBoatDialog.setContentView(R.layout.dialog_boatinfo)

        val btnClose: ImageButton = viewBoatDialog.findViewById(R.id.ibCloseViewBoat)
        btnClose.setOnClickListener {
            viewBoatDialog.dismiss()
        }

        viewBoatDialog.findViewById<TextView>(R.id.tvBoatNameInfo).text = boats[position].name
        viewBoatDialog.findViewById<TextView>(R.id.tvBoatOwnerInfo).text = boats[position].owner
        viewBoatDialog.findViewById<TextView>(R.id.tvRegisterNumInfo).text = boats[position].registeredNumber
        viewBoatDialog.findViewById<TextView>(R.id.tvCINInfo).text = boats[position].cin
        viewBoatDialog.findViewById<TextView>(R.id.tvBoatTypeInfo).text = boats[position].boatType
        viewBoatDialog.findViewById<TextView>(R.id.tvBoatModelInfo).text = boats[position].boatModel
        viewBoatDialog.findViewById<TextView>(R.id.tvBoatLengthInfo).text = boats[position].length
        viewBoatDialog.findViewById<TextView>(R.id.tvBoatBeamInfo).text = boats[position].beam
        viewBoatDialog.findViewById<TextView>(R.id.tvBoatDraftInfo).text = boats[position].draft

        viewBoatDialog.show()
    }


    override fun getItemCount(): Int {
        return boats.size
    }

    fun submitList(boatlist: List<Boat>){
        boats.clear()
        boats.addAll(boatlist)
        notifyDataSetChanged()
    }


    class BoatViewHolder constructor(
        boatView: View
    ): RecyclerView.ViewHolder(boatView){
        private val boatName: TextView = boatView.findViewById(R.id.tvBoatNameList)

        fun bind(boat: Boat){
            boatName.text = boat.name
        }
    }


}