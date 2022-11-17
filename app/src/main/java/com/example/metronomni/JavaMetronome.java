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
    private double[] noSoundArray;
    private double[] silenceSoundArray;
    private Message msg;
    private double currentBeat = 1.0;
    ///////////////////////////////////////////////////

    // Sets subdivisions values in beginning of code //
    static boolean quarterSubs = true;
    static boolean eighthSubs = false;
    static boolean sixteenthSubs = false;
    static boolean eighthNoteTripletSubs = false;
    ///////////////////////////////////////////////////

    // Sets boolean values for if notes are accented, rests, or regular //
    static boolean first16thIsOn = true;
    static boolean second16thIsOn = true;
    static boolean third16thIsOn = true;
    static boolean fourth16thIsOn = true;
    static boolean onlyQuarterIsOn = true;
    static boolean first8thIsOn = true;
    static boolean second8thIsOn = true;
    //////////////////////////////////////////////////////////////////////

    public JavaMetronome(Handler handler) {
        audioGenerator.createPlayer();
        this.mHandler = handler;
    }

    // Calculates how much silenece to write to the AudioTrack //
    public void calcSilence() {
        silence = (int) (((60/usableBpm)*8000) - tick);
        soundTickArray = new double[this.tick];
        soundTockArray = new double[this.tick];
        noSoundArray = new double[this.tick];
        silenceSoundArray = new double[this.silence];
        msg = new Message();
        msg.obj = ""+currentBeat;
        double[] tick = audioGenerator.getSineWave(this.tick, 8000, beatSound);
        double[] tock = audioGenerator.getSineWave(this.tick, 8000, sound);
        double[] none = audioGenerator.getSineWave(this.tick, 8000, 0);
        for(int i=0;i<this.tick;i++) {
            soundTickArray[i] = tick[i];
            soundTockArray[i] = tock[i];
            noSoundArray[i] = none[i];
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
        } else if (eighthNoteTripletSubs) {
            usableBeat = beat * 3;
            usableBpm = bpm * 3;
            EighthNoteTripletPlay();
        }
    }

    // Plays metronome at quarter note subdivisions //
    public void QuarterNotePlay() {
        calcSilence();
        do {
            msg = new Message();
            msg.obj = ""+currentBeat;
            if (onlyQuarterIsOn) {
                audioGenerator.writeSound(soundTickArray);
            } else {
                audioGenerator.writeSound(noSoundArray);
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
                if (first8thIsOn) {
                    audioGenerator.writeSound(soundTickArray);
                } else {
                    audioGenerator.writeSound(noSoundArray);
                }
            } else {
                if (second8thIsOn) {
                    audioGenerator.writeSound(soundTickArray);
                } else {
                    audioGenerator.writeSound(noSoundArray);
                }
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
                if (first16thIsOn) {
                    audioGenerator.writeSound(soundTickArray);
                } else {
                    audioGenerator.writeSound(noSoundArray);
                }
            } else if (currentBeat % 4 == 2){
                if (second16thIsOn) {
                    audioGenerator.writeSound(soundTickArray);
                } else {
                    audioGenerator.writeSound(noSoundArray);
                }
            } else if (currentBeat % 4 == 3){
                if (third16thIsOn) {
                    audioGenerator.writeSound(soundTickArray);
                } else {
                    audioGenerator.writeSound(noSoundArray);
                }
            } else if (currentBeat % 4 == 0){
                if (fourth16thIsOn) {
                    audioGenerator.writeSound(soundTickArray);
                } else {
                    audioGenerator.writeSound(noSoundArray);
                }
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

    public void EighthNoteTripletPlay() {
        calcSilence();
        do {
            msg = new Message();
            msg.obj = ""+currentBeat;
            if(currentBeat % 3 == 1) {
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

    public boolean get8thTripSubs() { return eighthNoteTripletSubs; }

    public static void set8thTripSubs (boolean eighthNoteTrip2) { eighthNoteTripletSubs = eighthNoteTrip2; }

    // Getter and setter methods for boolean values which determine if notes are active or not //

    public static boolean getFirst16thIsOn() { return first16thIsOn; }
    public static void setFirst16thIsOn(boolean firstIsOn2) { first16thIsOn = firstIsOn2; }

    public static boolean getSecond16thIsOn() { return second16thIsOn; }
    public static void setSecond16thIsOn(boolean secondIsOn2) { second16thIsOn = secondIsOn2; }

    public static boolean getThird16thIsOn() { return third16thIsOn; }
    public static void setThird16thIsOn(boolean thirdIsOn2) { third16thIsOn = thirdIsOn2; }

    public static boolean getFourth16thIsOn() { return fourth16thIsOn; }
    public static void setFourth16thIsOn(boolean fourthIsOn2) { fourth16thIsOn = fourthIsOn2; }

    public static boolean getOnlyQuarterIsOn() { return onlyQuarterIsOn; }
    public static void setOnlyQuarterIsOn(boolean onlyQuarterIsOn2) { onlyQuarterIsOn = onlyQuarterIsOn2; }

    public static boolean getFirst8thIsOn() { return first8thIsOn; }
    public static void setFirst8thIsOn(boolean first8thIsOn2) { first8thIsOn = first8thIsOn2; }

    public static boolean getSecond8thIsOn() { return second8thIsOn; }
    public static void setSecond8thIsOn(boolean second8thIsOn2) { second8thIsOn = second8thIsOn2; }

    /////////////////////////////////////////////////////////////////////////////////////////////






}