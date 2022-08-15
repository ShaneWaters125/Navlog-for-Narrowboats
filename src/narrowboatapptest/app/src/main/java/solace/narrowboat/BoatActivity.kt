package solace.narrowboat
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import solace.narrowboat.data.Boat
import solace.narrowboat.data.DatabaseHandler
import solace.narrowboat.ui.decoration.SideSpacingItemDecoration
import solace.narrowboat.ui.decoration.TopSpacingItemDecoration
import solace.narrowboat.ui.settings.BoatRecyleViewAdapter

class BoatActivity : AppCompatActivity() {

    private lateinit var boatAdapter: BoatRecyleViewAdapter

    companion object{
        var boats = mutableListOf<Boat>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_boat)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.hide()

        initButtons()
        initRecycleViewer()
    }

    private fun initRecycleViewer(){
        val recycleView = findViewById<RecyclerView>(R.id.rv_boats)
        recycleView.apply{
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(TopSpacingItemDecoration(20))
            addItemDecoration(SideSpacingItemDecoration(20))
            boatAdapter = BoatRecyleViewAdapter()
            adapter = boatAdapter
        }
        boats.clear()

        val databaseHandler = DatabaseHandler(this)
        boatAdapter.submitList(databaseHandler.viewBoats())
    }

    private fun initButtons(){
        val btnBack = findViewById<ImageButton>(R.id.ibCloseBoatList)
        btnBack.setOnClickListener{
            finish()
        }
        val btnCreateNewBoat = findViewById<Button>(R.id.btnCreateBoat)
        btnCreateNewBoat.setOnClickListener {
            val intent = Intent(this, BoatInfoActivity::class.java)
            this.startActivity(intent)
        }
    }



}