package com.example.metronomni;

import android.os.Handler;
import android.os.Message;

public class JavaMetronome {
    // Creates values needed by metronome //
    private double bpm;
    private int beat;
    private int noteValue;
    private int silence;
    private double usableBpm = bpm;
    private int usableBeat = beat;

    private double beatSound;
    private double sound;
    private final int tick = 1000; // samples of tick

    private boolean play = true;
    /////////////////////////////////////////

    // Creates values used to write to AudioTrack //
    private AudioGenerator audioGenerator = new AudioGenerator(8000);
    private Handler mHandler;
    private double[] soundTickArray;
    private double[] soundTockArray;
    private double[] silenceSoundArray;
    private Message msg;
    private double currentBeat = 1.0;
    ///////////////////////////////////////////////////

    // Sets subdivisions values in beginning of code //
    private static boolean quarterSubs = false;
    private static boolean eighthSubs = true;
    private static boolean sixteenthSubs = false;
    ///////////////////////////////////////////////////

    public JavaMetronome(Handler handler) {
        audioGenerator.createPlayer();
        this.mHandler = handler;
    }

    // Calculates how much silenece to write to the AudioTrack //
    public void calcSilence() {
        silence = (int) (((60/usableBpm)*8000) - tick);
        soundTickArray = new double[this.tick];
        soundTockArray = new double[this.tick];
        silenceSoundArray = new double[this.silence];
        msg = new Message();
        msg.obj = ""+currentBeat;
        double[] tick = audioGenerator.getSineWave(this.tick, 8000, beatSound);
        double[] tock = audioGenerator.getSineWave(this.tick, 8000, sound);
        for(int i=0;i<this.tick;i++) {
            soundTickArray[i] = tick[i];
            soundTockArray[i] = tock[i];
        }
        for(int i=0;i<silence;i++)
            silenceSoundArray[i] = 0;
    }


    ////////////// This section has play methods based on which subdivision is selected ///////////////

    // Decides which play method to call based on subdivisions //
    public void play() {
        if (quarterSubs) {
            usableBeat = beat;
            usableBpm = bpm;
            QuarterNotePlay();
        } else if (eighthSubs) {
            usableBeat = beat * 2;
            usableBpm = bpm * 2;
            EighthNotePlay();
        } else if (sixteenthSubs) {
            usableBeat = beat * 4;
            usableBpm = bpm * 4;
            sixteenthNotePlay();
        }
    }

    // Plays metronome at quarter note subdivisions //
    public void QuarterNotePlay() {
        calcSilence();
        do {
            msg = new Message();
            msg.obj = ""+currentBeat;
            if(currentBeat == 1) {
                audioGenerator.writeSound(soundTickArray);
            } else {
                audioGenerator.writeSound(soundTockArray);
            }
            if(bpm <= 120)
                mHandler.sendMessage(msg);

            audioGenerator.writeSound(silenceSoundArray);

            if(bpm > 120)
                mHandler.sendMessage(msg);
            currentBeat++;
            if(currentBeat > usableBeat)
                currentBeat = 1;
        } while(play);
    }

    // Plays metronome at eighth note subdivisions //
    public void EighthNotePlay() {
        calcSilence();
        do {
            msg = new Message();
            msg.obj = ""+currentBeat;
            if(currentBeat % 2 == 1) {
                audioGenerator.writeSound(soundTickArray);
            } else {
                audioGenerator.writeSound(soundTockArray);
            }
            if(bpm <= 120)
                mHandler.sendMessage(msg);

            audioGenerator.writeSound(silenceSoundArray);

            if(bpm > 120)
                mHandler.sendMessage(msg);
            currentBeat++;
            if(currentBeat > usableBeat)
                currentBeat = 1;
        } while(play);
    }

    // Plays metronome at 16th note subdivisions //
    public void sixteenthNotePlay() {
        calcSilence();
        do {
            msg = new Message();
            msg.obj = ""+currentBeat;
            if(currentBeat % 4 == 1) {
                audioGenerator.writeSound(soundTickArray);
            } else {
                audioGenerator.writeSound(soundTockArray);
            }
            if(bpm <= 120)
                mHandler.sendMessage(msg);

            audioGenerator.writeSound(silenceSoundArray);

            if(bpm > 120)
                mHandler.sendMessage(msg);
            currentBeat++;
            if(currentBeat > beat)
                currentBeat = 1;
        } while(play);
    }

    // Stops the metronome from playing //
    public void stop() {
        play = false;
        audioGenerator.destroyAudioTrack();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    /////////////////// Variable getter and setter methods ///////////////////////////////////////

    // Metronome variable getter and setter methods //
    public double getBpm() { return bpm; }

    public void setBpm(int bpm) {
        this.bpm = bpm;
    }

    public int getNoteValue() { return noteValue; }

    public void setNoteValue(int bpmetre) {
        this.noteValue = bpmetre;
    }

    public int getBeat() { return beat; }

    public void setBeat(int beat) {
        this.beat = beat;
    }

    public double getBeatSound() { return beatSound; }

    public void setBeatSound(double sound1) {
        this.beatSound = sound1;
    }

    public double getSound() { return sound; }

    public void setSound(double sound2) { this.sound = sound2; }


    // Subdivision getter and setter methods
    public boolean getQuarterSubs() { return quarterSubs; }

    public static void setQuarterSubs(boolean quarterSubs2) { quarterSubs = quarterSubs2; }

    public boolean getEighthSubs() { return eighthSubs; }

    public static void setEighthSubs(boolean eighthSubs2) { eighthSubs = eighthSubs2; }

    public boolean get16thSubs() { return sixteenthSubs; }

    public static void set16thSubs(boolean sixteenthSubs2) { sixteenthSubs = sixteenthSubs2; }

    /////////////////////////////////////////////////////////////////////////////////////////////





}