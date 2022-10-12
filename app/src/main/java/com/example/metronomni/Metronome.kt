package com.example.metronomni
import kotlin.concurrent.timerTask
import java.util.*
import android.util.Log



object Metronome {
    private var metronome: Timer

    init {
        metronome = Timer("metronome", true)
    }

    fun calculateSleepPeriod(bpm: Int):Long {
         return 1000 * 60/bpm.toLong()
    }

    fun startMet(bpm: Int): Boolean {
        this.metronome.schedule(
            timerTask {
                Log.i("met", "Tick")
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