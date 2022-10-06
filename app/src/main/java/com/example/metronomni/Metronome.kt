package com.example.metronomni
import kotlin.concurrent.timerTask
import java.util.*
import android.util.Log



object Metronome {
    private var metronome: Timer
    init {
        metronome = Timer("metronome", true)
    }

    fun calculateSleepPeriod():Long {
        return (1000 * (60 / 120)).toLong()
    }

    fun startMet(): Boolean {
        this.metronome.schedule(
            timerTask {
                Log.i("met", "Tick")
            },
            0L,
            calculateSleepPeriod()
        )
        return true
    }
}