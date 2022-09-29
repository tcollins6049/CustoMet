package com.example.metronomni

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Button
import android.view.View
import android.content.Intent

class tempoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tempo)
        val intent = intent
        val currentTempo = intent.getStringExtra("TEMPO_TEXT")
        val tempoText = findViewById<TextView>(R.id.tempoText1)
        tempoText.text = currentTempo

        val zeroButton = findViewById<Button>(R.id.zeroButton)
        val oneButton = findViewById<Button>(R.id.oneButton)
        val twoButton = findViewById<Button>(R.id.twoButton)
        val threeButton = findViewById<Button>(R.id.threeButton)
        val fourButton = findViewById<Button>(R.id.fourButton)
        val fiveButton = findViewById<Button>(R.id.fiveButton)
        val sixButton = findViewById<Button>(R.id.sixButton)
        val sevenButton = findViewById<Button>(R.id.sevenButton)
        val eightButton = findViewById<Button>(R.id.eightButton)
        val nineButton = findViewById<Button>(R.id.nineButton)
        val clearButton = findViewById<Button>(R.id.clearButton)
        val setButton = findViewById<Button>(R.id.setButton)
        var tempoNumber = 0

        zeroButton.setOnClickListener {
            if (tempoNumber == 0) {
                tempoText.text = tempoNumber.toString()
            } else if (tempoNumber > 0 && tempoNumber < 10) {
                tempoNumber = (tempoNumber * 10)
                tempoText.text = tempoNumber.toString()
            } else if (tempoNumber >= 10 && tempoNumber < 100) {
                tempoNumber = (tempoNumber * 10)
                tempoText.text = tempoNumber.toString()
            }

        }
        oneButton.setOnClickListener {
            if (tempoNumber == 0) {
                tempoNumber += 1
                tempoText.text = tempoNumber.toString()
            } else if (tempoNumber >0 && tempoNumber < 100) {
                tempoNumber = (tempoNumber * 10) + 1
                tempoText.text = tempoNumber.toString()
            }
        }
        twoButton.setOnClickListener {
            if (tempoNumber == 0) {
                tempoNumber += 2
                tempoText.text = tempoNumber.toString()
            } else if (tempoNumber >0 && tempoNumber < 100) {
                tempoNumber = (tempoNumber * 10) + 2
                tempoText.text = tempoNumber.toString()
            }
        }
        threeButton.setOnClickListener {
            if (tempoNumber == 0) {
                tempoNumber += 3
                tempoText.text = tempoNumber.toString()
            } else if (tempoNumber >0 && tempoNumber < 100) {
                tempoNumber = (tempoNumber * 10) + 3
                tempoText.text = tempoNumber.toString()
            }
        }
        fourButton.setOnClickListener {
            if (tempoNumber == 0) {
                tempoNumber += 4
                tempoText.text = tempoNumber.toString()
            } else if (tempoNumber >0 && tempoNumber < 100) {
                tempoNumber = (tempoNumber * 10) + 4
                tempoText.text = tempoNumber.toString()
            }
        }
        fiveButton.setOnClickListener {
            if (tempoNumber == 0) {
                tempoNumber += 5
                tempoText.text = tempoNumber.toString()
            } else if (tempoNumber >0 && tempoNumber < 100) {
                tempoNumber = (tempoNumber * 10) + 5
                tempoText.text = tempoNumber.toString()
            }
        }
        sixButton.setOnClickListener {
            if (tempoNumber == 0) {
                tempoNumber += 6
                tempoText.text = tempoNumber.toString()
            } else if (tempoNumber >0 && tempoNumber < 100) {
                tempoNumber = (tempoNumber * 10) + 6
                tempoText.text = tempoNumber.toString()
            }
        }
        sevenButton.setOnClickListener {
            if (tempoNumber == 0) {
                tempoNumber += 7
                tempoText.text = tempoNumber.toString()
            } else if (tempoNumber >0 && tempoNumber < 100) {
                tempoNumber = (tempoNumber * 10) + 7
                tempoText.text = tempoNumber.toString()
            }
        }
        eightButton.setOnClickListener {
            if (tempoNumber == 0) {
                tempoNumber += 8
                tempoText.text = tempoNumber.toString()
            } else if (tempoNumber >0 && tempoNumber < 100) {
                tempoNumber = (tempoNumber * 10) + 8
                tempoText.text = tempoNumber.toString()
            }
        }
        nineButton.setOnClickListener {
            if (tempoNumber == 0) {
                tempoNumber += 9
                tempoText.text = tempoNumber.toString()
            } else if (tempoNumber >0 && tempoNumber < 100) {
                tempoNumber = (tempoNumber * 10) + 9
                tempoText.text = tempoNumber.toString()
            }
        }
        clearButton.setOnClickListener {
            tempoNumber = 0
            tempoText.text = tempoNumber.toString()
        }


    }

    fun toMainView(view: View) {
        val setButton = findViewById<Button>(R.id.setButton)
        val tempoText = findViewById<TextView>(R.id.tempoText1)
        val currentTempo = tempoText.getText().toString()
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("TEMPO_TEXT", currentTempo)
        startActivity(intent)
    }
}