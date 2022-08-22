package solace.narrowboat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import solace.narrowboat.data.DatabaseHandler

class LogbookActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logbook)
        supportActionBar?.hide()

        initButtons()
        initText()

    }

    private fun initButtons(){
        val btnBack = findViewById<ImageButton>(R.id.ibCloseLogbook)
        btnBack.setOnClickListener {
            finish()
        }

        val btnCloseLogbook = findViewById<Button>(R.id.btnCloseLogbook)
        btnCloseLogbook.setOnClickListener {
            finish()
        }

        val btnUpdateLogbook = findViewById<Button>(R.id.btnUpdateLogbook)
        btnUpdateLogbook.setOnClickListener {
            var databaseHandler = DatabaseHandler(this)
            databaseHandler.updateLogbook(intent.getIntExtra("id", 0), findViewById<EditText>(R.id.etCrew).text.toString(), findViewById<EditText>(R.id.etGuests).text.toString(),
                    "", "", findViewById<EditText>(R.id.etWeather).text.toString(), findViewById<EditText>(R.id.etEngineHours).text.toString(),
                    findViewById<EditText>(R.id.etEngineFuel).text.toString(), findViewById<EditText>(R.id.etEngineWater).text.toString(), findViewById<EditText>(R.id.etNotes).text.toString(),
                    findViewById<EditText>(R.id.etNumLocks).text.toString()
            )
            finish()
        }
    }

    private fun initText(){
        var databaseHandler = DatabaseHandler(this)
        var logbook = databaseHandler.viewLogbook(intent.getIntExtra("id", 0))

        findViewById<EditText>(R.id.etCrew).setText(logbook.crew)
        findViewById<EditText>(R.id.etGuests).setText(logbook.guests)
        findViewById<EditText>(R.id.etWeather).setText(logbook.weather)
        findViewById<EditText>(R.id.etEngineHours).setText(logbook.engineHours)
        findViewById<EditText>(R.id.etEngineFuel).setText(logbook.fuel)
        findViewById<EditText>(R.id.etEngineWater).setText(logbook.water)
        findViewById<EditText>(R.id.etNotes).setText(logbook.notes)
        findViewById<EditText>(R.id.etNumLocks).setText(logbook.numLocks)

        databaseHandler.close()
    }
}