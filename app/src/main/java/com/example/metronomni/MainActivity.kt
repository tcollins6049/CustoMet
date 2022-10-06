package com.example.metronomni

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.view.View
import android.content.Intent
import android.widget.ToggleButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent = intent
        val currentTempo = intent.getStringExtra("TEMPO_TEXT")
        val tempoButton = findViewById<Button>(R.id.toTempoView)
        if (!currentTempo.equals(null)) {
            tempoButton.text = currentTempo
        }

        val play: ToggleButton = findViewById(R.id.playButton)
        play.setOnClickListener {
            Metronome.startMet()
        }
    }


    fun goToTempoView(view: View) {
        val toTempoView = findViewById<Button>(R.id.toTempoView)
        val currentTempo = toTempoView.getText().toString()
        val intent = Intent(this, tempoActivity::class.java)
        intent.putExtra("TEMPO_TEXT", currentTempo)
        startActivity(intent)
    }
}