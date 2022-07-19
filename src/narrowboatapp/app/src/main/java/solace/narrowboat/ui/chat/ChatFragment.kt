package solace.narrowboat.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import solace.narrowboat.data.Chat
import solace.narrowboat.data.User
import solace.narrowboat.databinding.FragmentChatBinding
import solace.narrowboat.restapi.RetrofitClient
import solace.narrowboat.restapi.RetrofitInterface
import solace.narrowboat.ui.decoration.SideSpacingItemDecoration
import solace.narrowboat.ui.decoration.TopSpacingItemDecoration

class ChatFragment : Fragment() {

    private lateinit var chatFragment: FragmentChatBinding
    private lateinit var chatAdapter: ChatRecycleViewAdapter
    private lateinit var retrofitInterface: RetrofitInterface
    private var compositeDisposable = CompositeDisposable()

    companion object{
        var chats = mutableListOf<Chat>()
    }


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        chatFragment = FragmentChatBinding.inflate(inflater, container, false)
        return chatFragment.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecycleView()
        connectServer()
    }

    private fun initRecycleView(){
        val recycleView = chatFragment.rvChats
        recycleView.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(TopSpacingItemDecoration(20))
            addItemDecoration(SideSpacingItemDecoration(20))
            chatAdapter = ChatRecycleViewAdapter()
            adapter = chatAdapter
        }
        chats.clear()
        var user1: User = User(id = 1, name = "Bob")
        var chat1: Chat = Chat(user1)
        var user2: User = User(id = 2, name = "Tom")
        var chat2: Chat = Chat(user2)
        var user3: User = User(id = 3, name = "Scott")
        var chat3: Chat = Chat(user3)
        var user4: User = User(id = 4, name = "Rob")
        var chat4: Chat = Chat(user4)

        chats.add(chat1)
        chats.add(chat2)
        chats.add(chat3)
        chats.add(chat4)

        chatAdapter.submitList(chats)
    }

    private fun connectServer(){
        val retrofit = RetrofitClient.getInstance()
        retrofitInterface = retrofit.create(RetrofitInterface::class.java)
        compositeDisposable.add(
                retrofitInterface.registerUser(
                        "4",
                        "Pocho8",
                        "Asshole",
                        "test@gmail.com",
                        "2022-05-17 23:44:04.745302"
                ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe( { result ->
                            if (result.toString().contains("yes")){
                                println("WORKINGWORKINGWORKINGWORKINGWORKINGWORKINGWORKINGWORKINGWORKINGWORKINGWORKING")
                            } else{
                                println("FUCKEDFUCKEDFUCKEDFUCKEDFUCKEDFUCKEDFUCKEDFUCKEDFUCKEDFUCKEDFUCKEDFUCKEDFUCKED")
                            }
                        }, {
                            println("Nodejs server is offline!")
                        }))
    }
}