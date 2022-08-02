package solace.narrowboat.ui.logbook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import solace.narrowboat.data.Journey
import solace.narrowboat.databinding.FragmentLogbookBinding
import solace.narrowboat.restapi.RetrofitClient
import solace.narrowboat.restapi.RetrofitInterface
import solace.narrowboat.ui.decoration.SideSpacingItemDecoration
import solace.narrowboat.ui.decoration.TopSpacingItemDecoration

class LogbookFragment : Fragment() {

    private lateinit var logbookFragment: FragmentLogbookBinding
    private lateinit var journeyAdapter: JourneyRecycleViewAdapter
    private lateinit var retrofitInterface: RetrofitInterface
    private var compositeDisposable = CompositeDisposable()

    companion object{
        var journeys = mutableListOf<Journey>()
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        logbookFragment = FragmentLogbookBinding.inflate(inflater, container, false)
        return logbookFragment.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecycleView()
        connectServer()
    }

    private fun initRecycleView(){
        val recycleView = logbookFragment.rvJourneys
        recycleView.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(TopSpacingItemDecoration(20))
            addItemDecoration(SideSpacingItemDecoration(20))
            journeyAdapter = JourneyRecycleViewAdapter()
            adapter = journeyAdapter
        }
        journeys.clear()
        var journey1 = Journey("Journey 1")
        var journey2 = Journey("Journey 2")

        journeys.add(journey1)
        journeys.add(journey2)

        journeyAdapter.submitList(journeys)
    }

    private fun connectServer(){
        val retrofit = RetrofitClient.getInstance()
        retrofitInterface = retrofit.create(RetrofitInterface::class.java)
        compositeDisposable.add(
                retrofitInterface.registerUser(
                        "4",
                        "Pocho8",
                        "Testing12390128094",
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