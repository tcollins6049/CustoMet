package com.example.metronomni;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
import android.widget.ImageButton;

public class MainActivity extends Activity {

    private final short minBpm = 40;
    private final short maxBpm = 208;

    private int bpm = 100;
    private short noteValue = 4;
    private short beats = 4;
    private short volume;
    private short initialVolume;
    private double beatSound = 2440;
    private double sound = 1440;
    private AudioManager audio;
    private MetronomeAsyncTask metroTask;

    private Button plusButton;
    private Button minusButton;
    private TextView currentBeat;

    private Handler mHandler;

    // have in mind that: http://stackoverflow.com/questions/11407943/this-handler-class-should-be-static-or-leaks-might-occur-incominghandler
    // in this case we should be fine as no delayed messages are queued
    private Handler getHandler() {
        return new Handler() {
            @Override
            public void handleMessage(Message msg) {
                String message = (String)msg.obj;
                if(message.equals("1"))
                    currentBeat.setTextColor(Color.GREEN);
                else
                    currentBeat.setTextColor(getResources().getColor(R.color.yellow));
                currentBeat.setText(message);
            }
        };
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String currentTempo = intent.getStringExtra("currentTempo");
        Button tempoButton = (Button) findViewById(R.id.tempoButton);
        if (currentTempo == null) {
            tempoButton.setText("120");
        } else {
            tempoButton.setText(currentTempo);
        }

        if (currentTempo != null) {
            bpm = Integer.valueOf(currentTempo);
        }
        ImageButton firstNoteButton = (ImageButton) findViewById(R.id.FirstNote);
        firstNoteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (JavaMetronome.getFirstIsOn()) {
                    JavaMetronome.setFirstIsOn(false);
                    firstNoteButton.setBackgroundResource(R.drawable.quarter_note_rest);
                } else {
                    JavaMetronome.setFirstIsOn(true);
                    firstNoteButton.setBackgroundResource(R.drawable.quarter_note);
                }
            }
        });
        ImageButton secondNoteButton = (ImageButton) findViewById(R.id.SecondNote);
        secondNoteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (JavaMetronome.getSecondIsOn()) {
                    JavaMetronome.setSecondIsOn(false);
                } else {
                    JavaMetronome.setSecondIsOn(true);
                }
            }
        });
        ImageButton thirdNoteButton = (ImageButton) findViewById(R.id.ThirdNote);
        thirdNoteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (JavaMetronome.getThirdIsOn()) {
                    JavaMetronome.setThirdIsOn(false);
                } else {
                    JavaMetronome.setThirdIsOn(true);
                }
            }
        });
        ImageButton fourthNoteButton = (ImageButton) findViewById(R.id.FourthNote);
        fourthNoteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (JavaMetronome.getFourthIsOn()) {
                    JavaMetronome.setFourthIsOn(false);
                } else {
                    JavaMetronome.setFourthIsOn(true);
                }
            }
        });

        metroTask = new MetronomeAsyncTask();

        currentBeat = (TextView) findViewById(R.id.currentBeat);


        audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);


    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public synchronized void onStartStopClick(View view) {
        Button button = (Button) view;
        String buttonText = button.getText().toString();
        if(buttonText.equalsIgnoreCase("start")) {
            button.setText(R.string.stop);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                metroTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Void[])null);
            else
                metroTask.execute();
        } else {
            button.setText(R.string.start);
            metroTask.stop();
            metroTask = new MetronomeAsyncTask();
            Runtime.getRuntime().gc();
        }
    }

    public void toTempoView(View view) {
        Button tempoButton = (Button) findViewById(R.id.tempoButton);
        String currTempo = tempoButton.getText().toString();
        Intent intent = new Intent(MainActivity.this, setTempoActivity.class);
        intent.putExtra("currentTempo", currTempo);
        startActivity(intent);
    }

    public void toSubdivisionsView(View view) {
        Button subdivButton = (Button) findViewById(R.id.subdivisionsButton);
        Button tempoButton = (Button) findViewById(R.id.tempoButton);
        String currTempo = tempoButton.getText().toString();
        Intent intent = new Intent(MainActivity.this, setSubdivisionsActivity.class);
        intent.putExtra("currentTempo", currTempo);
        startActivity(intent);
    }

    private class MetronomeAsyncTask extends AsyncTask<Void,Void,String> {
        JavaMetronome metronome;

        MetronomeAsyncTask() {
            mHandler = getHandler();
            metronome = new JavaMetronome(mHandler);
        }

        protected String doInBackground(Void... params) {
            metronome.setBeat(beats);
            metronome.setNoteValue(noteValue);
            metronome.setBpm(bpm);
            metronome.setBeatSound(beatSound);
            metronome.setSound(sound);

            metronome.play();

            return null;
        }

        public void stop() {
            metronome.stop();
            metronome = null;
        }


    }

}
