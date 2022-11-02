package com.example.metronomni;

import android.os.Handler;
import android.os.Message;

public class JavaMetronome {
    private double bpm;
    private int beat;
    private int noteValue;
    private int silence;
    private int silence2;

    private double beatSound;
    private double sound;
    private final int tick = 1000; // samples of tick

    private boolean play = true;

    private AudioGenerator audioGenerator = new AudioGenerator(8000);
    private Handler mHandler;
    private double[] soundTickArray;
    private double[] soundTockArray;
    private double[] silenceSoundArray;
    private double[] silence2SoundArray;
    private Message msg;
    private double currentBeat = 1.0;

    public JavaMetronome(Handler handler) {
        audioGenerator.createPlayer();
        this.mHandler = handler;
    }

    public void calcSilence() {
        silence = (int) (((60/bpm)*8000) - tick); // / 2) - 500;
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

    public void calc2ndSilence() {
        silence2 = (int) (((60 / (bpm)) * 8000) - tick);
        silence2SoundArray = new double[this.silence2];
        for(int i=0; i<silence2; i++) {
            silence2SoundArray[i] = 0;
        }
    }

    public void play() {
        calcSilence();
        calc2ndSilence();
        do {
            msg = new Message();
            msg.obj = ""+currentBeat;
            if(currentBeat == 1) { //|| currentBeat == 2) {
                audioGenerator.writeSound(soundTickArray);
                //audioGenerator.writeSound(silence2SoundArray);
            } /*else if (currentBeat == 2) {
                audioGenerator.writeSound(soundTockArray);
            } else if (currentBeat == 3) {
                audioGenerator.writeSound(soundTickArray);
            } */else {
                audioGenerator.writeSound(soundTickArray);
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