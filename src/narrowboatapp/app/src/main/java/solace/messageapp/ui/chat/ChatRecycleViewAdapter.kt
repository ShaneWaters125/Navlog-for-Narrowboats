package solace.messageapp.ui.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import solace.messageapp.R
import solace.messageapp.data.Chat

class ChatRecycleViewAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var chats:List<Chat> = ArrayList()
    private lateinit var parentView: ViewGroup

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ChatViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.viewholder_chat, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ChatRecycleViewAdapter.ChatViewHolder ->{
                holder.bind(chats[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return chats.size
    }

    fun submitList(chatList: List<Chat>){
        chats = chatList
        notifyDataSetChanged()
    }

    class ChatViewHolder constructor(
        chatView: View
    ): RecyclerView.ViewHolder(chatView){
        private val chatName: TextView = chatView.findViewById(R.id.tvChatName)

        fun bind(chat: Chat){
            chatName.text = chat.user.name

        }
    }

}