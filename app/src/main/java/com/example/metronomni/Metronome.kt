package com.example.metronomni
import kotlin.concurrent.timerTask
import java.util.*
import android.util.Log
import android.media.ToneGenerator
import android.media.AudioManager
import android.os.Handler
import android.os.Looper


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
        //To calculate times between task
        /*var startTime = System.currentTimeMillis()
        var prevTime = startTime
        var prevTime2 = startTime
        var startTime2 = startTime
        this.metronome.schedule(
            timerTask {
                prevTime = startTime
                startTime = System.currentTimeMillis()
                var total = startTime - prevTime
                Log.i("met", "$total")
                prevTime2 = startTime
                startTime2 = System.currentTimeMillis()
                var total2 = startTime2 - prevTime2
                Log.i("met", "funcTotal: $total2")
            },
            0L,
            calculateSleepPeriod(bpm)
        )
        return true*/


        // To play sound
        //var tonegenerator: ToneGenerator
        this.metronome.schedule(
            timerTask {
                val toneGenerator = ToneGenerator(AudioManager.STREAM_MUSIC, 100)
                toneGenerator.startTone(METRONOME_TONE)
                toneGenerator.release()
                //tonegenerator = ToneGenerator(AudioManager.STREAM_MUSIC, 100)
                //tonegenerator.startTone(METRONOME_TONE)
                //val handler = Handler(Looper.getMainLooper())
                //handler.postDelayed(Runnable() {
                //    tonegenerator.release()
                //}, 100)

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