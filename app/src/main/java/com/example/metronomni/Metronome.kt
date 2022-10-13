package com.example.metronomni
import kotlin.concurrent.timerTask
import java.util.*
import android.util.Log
import android.media.ToneGenerator
import android.media.AudioManager



object Metronome {
    private var metronome: Timer
    private const val METRONOME_TONE = ToneGenerator.TONE_PROP_BEEP

    init {
        metronome = Timer("metronome", true)
    }

    fun calculateSleepPeriod(bpm: Int):Long {
         return 1000 * 60/bpm.toLong()
    }

    fun startMet(bpm: Int): Boolean {
        //To calculate times between tasks
        /*
        var startTime = System.currentTimeMillis()
        var prevTime = startTime
        this.metronome.schedule(
            timerTask {
                prevTime = startTime
                startTime = System.currentTimeMillis()
                var total = startTime - prevTime
                Log.i("met", "$total")
            },
            0L,
            calculateSleepPeriod(bpm)
        )
        return true
        */

        // To play sound
        this.metronome.schedule(
            timerTask {
                val toneGenerator = ToneGenerator(AudioManager.STREAM_MUSIC, 100)
                toneGenerator.startTone(METRONOME_TONE)
                //toneGenerator.release()
            },
            0L,
            calculateSleepPeriod(bpm)
        )
        return true
    }

    fun stopMet(): Boolean {
        this.metronome.cancel()
        return false
    }

    fun createNewTimer() {
        this.metronome = Timer("metronome", true)
    }
}