package solace.narrowboat

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import solace.narrowboat.data.DatabaseHandler

class BoatInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_boatinfo)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.hide()

        initButtons()
    }

    private fun initButtons(){
        val btnClose = findViewById<ImageButton>(R.id.ibCloseBoatInfo)
        btnClose.setOnClickListener {
            finish()
        }

        val btnCreateBoat = findViewById<Button>(R.id.btnCreateNewBoat)
        btnCreateBoat.setOnClickListener {
            val databaseHandler = DatabaseHandler(this)
            val boatName = findViewById<EditText>(R.id.etBoatName).text.toString()
            val boatOwner = findViewById<EditText>(R.id.etBoatOwner).text.toString()
            val boatRegisterNum = findViewById<EditText>(R.id.etRegisterNumber).text.toString()
            val boatCIN = findViewById<EditText>(R.id.etCIN).text.toString()
            val boatType = findViewById<EditText>(R.id.etType).text.toString()
            val boatModel = findViewById<EditText>(R.id.etModel).text.toString()
            val boatLength = findViewById<EditText>(R.id.etLength).text.toString()
            val boatBeam = findViewById<EditText>(R.id.etBeam).text.toString()
            val boatDraft = findViewById<EditText>(R.id.etDraft).text.toString()
            databaseHandler.addBoat(boatName, boatOwner, boatRegisterNum, boatCIN, boatType, boatModel, boatLength, boatBeam, boatDraft)
            databaseHandler.close()

            finish()
        }
    }

}