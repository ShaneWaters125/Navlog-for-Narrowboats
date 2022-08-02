package solace.narrowboat

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import solace.narrowboat.data.Journey
import solace.narrowboat.data.Session
import solace.narrowboat.ui.decoration.SideSpacingItemDecoration
import solace.narrowboat.ui.decoration.TopSpacingItemDecoration
import solace.narrowboat.ui.logbook.JourneyRecycleViewAdapter
import solace.narrowboat.ui.logbook.LogbookFragment
import solace.narrowboat.ui.logbook.SessionRecycleViewAdapter

class JourneyActivity : AppCompatActivity() {

    private lateinit var sessionAdapter: SessionRecycleViewAdapter

    companion object{
        var sessions = mutableListOf<Session>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_journey)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportActionBar?.hide()

        initButtons()
        initRecycleViewer()
    }

    /**
     * Initialises the back button on the tool and returns the user to the previous activity.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return true
    }

    private fun initRecycleViewer(){
        val recycleView = findViewById<RecyclerView>(R.id.rvEntries)
        recycleView.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(TopSpacingItemDecoration(20))
            addItemDecoration(SideSpacingItemDecoration(20))
            sessionAdapter = SessionRecycleViewAdapter()
            adapter = sessionAdapter
        }

        sessions.clear()
        val session1 = Session(1, "13:24 - 17:52", "01/08/2022", "1.2km", "A coal boat name")
        sessions.add(session1)
        val session2 = Session(1, "08:12 - 13:11", "02/08/2022", "1.5km", "A coal boat name")
        sessions.add(session2)
        sessionAdapter.submitList(sessions)

    }

    private fun initButtons(){
        val btnBack = findViewById<ImageButton>(R.id.ibJourneyBack)
        btnBack.setOnClickListener {
            finish()
        }
    }
}