package com.example.metronomni

/*import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.view.View
import android.content.Intent
import android.media.AudioManager
import android.media.SoundPool
import android.widget.ToggleButton
//import android.os.Handler
import android.util.Log
import java.util.concurrent.locks.LockSupport

@Suppress("Deprecation")
class MainActivity : AppCompatActivity() {

    private var soundPool: SoundPool? = null
    private var soundId = 1
    var metOnorOff = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent = intent
        val currentTempo = intent.getStringExtra("TEMPO_TEXT")
        val tempoButton = findViewById<Button>(R.id.toTempoView)
        if (!currentTempo.equals(null)) {
            tempoButton.text = currentTempo
        }

        var isMetOn = false
        val play: ToggleButton = findViewById(R.id.playButton)
        play.setOnClickListener {
            // To play sound in Metronome kotlin file
            /*
            if (currentTempo != null && isMetOn == false) {
                Metronome.startMet(currentTempo.toInt())
                isMetOn = true
            } else if (currentTempo == null && isMetOn == false){
                Metronome.startMet(120)
                isMetOn = true
            } else if (isMetOn == true) {
                Metronome.stopMet()
                Metronome.createNewTimer()
                isMetOn = false
            }
             */

            if (currentTempo != null && isMetOn == false) {
                soundpoolPlay(currentTempo.toInt())
                metOnorOff = true
                isMetOn = true
            } else if (currentTempo == null && isMetOn == false) {
                soundpoolPlay(120)
                metOnorOff = true
                isMetOn = true
            } else {
                //soundpoolStop()
                metOnorOff = false
                isMetOn = false
            }


        }
    }

    fun soundpoolPlay(bpm: Int) {
        val sndPool = SoundPool(3, AudioManager.STREAM_MUSIC, 0)
        val loadSound = sndPool.load(this, R.raw.trimmed_drumstick_met, 1)
        volumeControlStream = AudioManager.STREAM_MUSIC
        val thread = Thread {
            while (metOnorOff) {
                soundId = sndPool.play(loadSound, 1f, 1f, 1, 0, 1f)
                LockSupport.parkNanos(240000 / bpm.toLong() / 4 * 1000000)
            }
        }
        thread.priority = Thread.MAX_PRIORITY
        thread.start()
    }

    fun goToTempoView(view: View) {
        val toTempoView = findViewById<Button>(R.id.toTempoView)
        val currentTempo = toTempoView.getText().toString()
        val intent = Intent(this, tempoActivity::class.java)
        intent.putExtra("TEMPO_TEXT", currentTempo)
        startActivity(intent)
    }
}*/
