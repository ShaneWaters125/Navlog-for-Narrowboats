package solace.narrowboat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class LogbookActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logbook)
        supportActionBar?.hide()

        initButtons()
    }

    private fun initButtons(){
        val btnBack = findViewById<ImageButton>(R.id.ibCloseLogbook)
        btnBack.setOnClickListener {
            finish()
        }
    }
}