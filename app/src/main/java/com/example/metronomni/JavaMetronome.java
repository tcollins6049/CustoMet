package com.example.metronomni;

import android.os.Handler;
import android.os.Message;

public class JavaMetronome {
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

    private AudioGenerator audioGenerator = new AudioGenerator(8000);
    private Handler mHandler;
    private double[] soundTickArray;
    private double[] soundTockArray;
    private double[] silenceSoundArray;
    private Message msg;
    private double currentBeat = 1.0;

    private boolean quarterSubs = false;
    private boolean eighthSubs = false;
    private boolean sixteenthSubs = true;

    public JavaMetronome(Handler handler) {
        audioGenerator.createPlayer();
        this.mHandler = handler;
    }

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




    public void stop() {
        play = false;
        audioGenerator.destroyAudioTrack();
    }

    /*public double getBpm() {
        return bpm;
    }*/

    public void setBpm(int bpm) {
        this.bpm = bpm;
    }

    /*public int getNoteValue() {
        return noteValue;
    }*/

    public void setNoteValue(int bpmetre) {
        this.noteValue = bpmetre;
    }

    /*public int getBeat() {
        return beat;
    }*/

    public void setBeat(int beat) {
        this.beat = beat;
    }

    /*public double getBeatSound() {
        return beatSound;
    }*/

    public void setBeatSound(double sound1) {
        this.beatSound = sound1;
    }

    /*public double getSound() {
        return sound;
    }*/

    public void setSound(double sound2) {
        this.sound = sound2;
    }
}