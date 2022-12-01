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

    //private final short minBpm = 40;
    //private final short maxBpm = 208;

    private int bpm = 100;
    private short noteValue = 4;
    private short beats = 4;
    //private short volume;
    //private short initialVolume;
    private double beatSound = 2440;
    private double sound = 1440;
    private AudioManager audio;
    private MetronomeAsyncTask metroTask;
    Boolean isMetOn = false;

    //private Button plusButton;
    //private Button minusButton;
    private TextView currentBeat;

    static Boolean quarterSubStatus = true;
    static Boolean eighthSubStatus = false;
    static Boolean sixteenthSubStatus = false;
    static Boolean eighthTripSubStatus = false;

    private Handler mHandler;

    // have in mind that: http://stackoverflow.com/questions/11407943/this-handler-class-should-be-static-or-leaks-might-occur-incominghandler
    // in this case we should be fine as no delayed messages are queued
    /*private Handler getHandler() {
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
    }*/

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmet);

        Intent intent = getIntent();
        String currentTempo = intent.getStringExtra("currentTempo");
        Button tempoButton = (Button) findViewById(R.id.tempoButton);
        if (currentTempo == null) {
            currentTempo = "100";
            tempoButton.setText(currentTempo);
        } else {
            tempoButton.setText(currentTempo);
        }

        if (currentTempo != null) {
            bpm = Integer.valueOf(currentTempo);
        }

        setNoteImages();

        ImageButton startStopButton = (ImageButton) findViewById(R.id.startstop);
        ImageButton first16thNoteButton = (ImageButton) findViewById(R.id.First16thNote);
        ImageButton second16thNoteButton = (ImageButton) findViewById(R.id.Second16thNote);
        ImageButton fourth16thNoteButton = (ImageButton) findViewById(R.id.Fourth16thNote);
        ImageButton third16thNoteButton = (ImageButton) findViewById(R.id.Third16thNote);
        ImageButton onlyQuarterNoteButton = (ImageButton) findViewById(R.id.OnlyQuarterNote);
        ImageButton first8thNoteButton = (ImageButton) findViewById(R.id.secondEighthNote);
        ImageButton second8thNoteButton = (ImageButton) findViewById(R.id.firstEighthNote);
        first16thNoteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (isMetOn) {
                    isMetOn = false;
                    startStopButton.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                    metroTask.stop();
                    metroTask = new MetronomeAsyncTask();
                    Runtime.getRuntime().gc();
                }
                if (JavaMetronome.getFirst16thIsOn()) {
                    JavaMetronome.setFirst16thIsOn(false);
                    first16thNoteButton.setImageResource(R.drawable.quarterrest1);
                } else {
                    JavaMetronome.setFirst16thIsOn(true);
                    first16thNoteButton.setImageResource(R.drawable.testing4);
                }
            }
        });
        second16thNoteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (isMetOn) {
                    isMetOn = false;
                    startStopButton.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                    metroTask.stop();
                    metroTask = new MetronomeAsyncTask();
                    Runtime.getRuntime().gc();
                }
                if (JavaMetronome.getSecond16thIsOn()) {
                    JavaMetronome.setSecond16thIsOn(false);
                    second16thNoteButton.setImageResource(R.drawable.quarterrest1);
                } else {
                    JavaMetronome.setSecond16thIsOn(true);
                    second16thNoteButton.setImageResource(R.drawable.testing4);
                }
            }
        });
        third16thNoteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (isMetOn) {
                    isMetOn = false;
                    startStopButton.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                    metroTask.stop();
                    metroTask = new MetronomeAsyncTask();
                    Runtime.getRuntime().gc();
                }
                if (JavaMetronome.getThird16thIsOn()) {
                    JavaMetronome.setThird16thIsOn(false);
                    third16thNoteButton.setImageResource(R.drawable.quarterrest1);
                } else {
                    JavaMetronome.setThird16thIsOn(true);
                    third16thNoteButton.setImageResource(R.drawable.testing4);
                }
            }
        });
        fourth16thNoteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (isMetOn) {
                    isMetOn = false;
                    startStopButton.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                    metroTask.stop();
                    metroTask = new MetronomeAsyncTask();
                    Runtime.getRuntime().gc();
                }
                if (JavaMetronome.getFourth16thIsOn()) {
                    JavaMetronome.setFourth16thIsOn(false);
                    fourth16thNoteButton.setImageResource(R.drawable.quarterrest1);
                } else {
                    JavaMetronome.setFourth16thIsOn(true);
                    fourth16thNoteButton.setImageResource(R.drawable.testing4);
                }
            }
        });
        onlyQuarterNoteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (isMetOn) {
                    isMetOn = false;
                    startStopButton.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                    metroTask.stop();
                    metroTask = new MetronomeAsyncTask();
                    Runtime.getRuntime().gc();
                }
                if (JavaMetronome.getOnlyQuarterIsOn()) {
                    JavaMetronome.setOnlyQuarterIsOn(false);
                    onlyQuarterNoteButton.setImageResource(R.drawable.quarterrest1);
                } else {
                    JavaMetronome.setOnlyQuarterIsOn(true);
                    onlyQuarterNoteButton.setImageResource(R.drawable.testing4);
                }
            }
        });
        first8thNoteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (isMetOn) {
                    isMetOn = false;
                    startStopButton.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                    metroTask.stop();
                    metroTask = new MetronomeAsyncTask();
                    Runtime.getRuntime().gc();
                }
                if (JavaMetronome.getFirst8thIsOn()) {
                    JavaMetronome.setFirst8thIsOn(false);
                    first8thNoteButton.setImageResource(R.drawable.quarterrest1);
                } else {
                    JavaMetronome.setFirst8thIsOn(true);
                    first8thNoteButton.setImageResource(R.drawable.testing4);
                }
            }
        });
        second8thNoteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (isMetOn) {
                    isMetOn = false;
                    startStopButton.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                    metroTask.stop();
                    metroTask = new MetronomeAsyncTask();
                    Runtime.getRuntime().gc();
                }
                if (JavaMetronome.getSecond8thIsOn()) {
                    JavaMetronome.setSecond8thIsOn(false);
                    second8thNoteButton.setImageResource(R.drawable.quarterrest1);
                } else {
                    JavaMetronome.setSecond8thIsOn(true);
                    second8thNoteButton.setImageResource(R.drawable.testing4);
                }
            }
        });


        metroTask = new MetronomeAsyncTask();

        currentBeat = (TextView) findViewById(R.id.currentBeat);


        audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);


    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public synchronized void onStartStopClick(View view) {
        ImageButton button = (ImageButton) view;
        //String buttonText = button.getText().toString();
        //if(buttonText.equalsIgnoreCase("start")) {
        if (!isMetOn) {
            isMetOn = true;
            button.setImageResource(R.drawable.ic_baseline_pause_24);
            //button.setText(R.string.stop);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                metroTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Void[])null);
            else
                metroTask.execute();
        } else {
            //button.setText(R.string.start);
            isMetOn = false;
            button.setImageResource(R.drawable.ic_baseline_play_arrow_24);
            metroTask.stop();
            metroTask = new MetronomeAsyncTask();
            Runtime.getRuntime().gc();
        }
    }

    public void toTempoView(View view) {
        ImageButton startStopButton = (ImageButton) findViewById(R.id.startstop);
        if (isMetOn) {
            isMetOn = false;
            startStopButton.setImageResource(R.drawable.ic_baseline_play_arrow_24);
            metroTask.stop();
            metroTask = new MetronomeAsyncTask();
            Runtime.getRuntime().gc();
        }
        Button tempoButton = (Button) findViewById(R.id.tempoButton);
        String currTempo = tempoButton.getText().toString();
        Intent intent = new Intent(MainActivity.this, setTempoActivity.class);
        intent.putExtra("currentTempo", currTempo);
        startActivity(intent);
    }

    public void toSubdivisionsView(View view) {
        ImageButton startStopButton = (ImageButton) findViewById(R.id.startstop);
        if (isMetOn) {
            isMetOn = false;
            startStopButton.setImageResource(R.drawable.ic_baseline_play_arrow_24);
            metroTask.stop();
            metroTask = new MetronomeAsyncTask();
            Runtime.getRuntime().gc();
        }
        Button subdivButton = (Button) findViewById(R.id.subdivisionsButton);
        Button tempoButton = (Button) findViewById(R.id.tempoButton);
        String currTempo = tempoButton.getText().toString();
        Intent intent = new Intent(MainActivity.this, setSubdivisionsActivity.class);
        intent.putExtra("currentTempo", currTempo);
        startActivity(intent);
    }

    public void toCustomView(View view) {
        ImageButton startStopButton = (ImageButton) findViewById(R.id.startstop);
        if (isMetOn) {
            isMetOn = false;
            startStopButton.setImageResource(R.drawable.ic_baseline_play_arrow_24);
            metroTask.stop();
            metroTask = new MetronomeAsyncTask();
            Runtime.getRuntime().gc();
        }
        Intent intent = new Intent(MainActivity.this, setCustomActivity.class);
        startActivity(intent);
    }

    private class MetronomeAsyncTask extends AsyncTask<Void,Void,String> {
        JavaMetronome metronome;

        MetronomeAsyncTask() {
            //mHandler = getHandler();
            metronome = new JavaMetronome(); //mHandler);
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

    public void setNoteImages() {
        ImageButton first16thNoteButton = (ImageButton) findViewById(R.id.First16thNote);
        ImageButton second16thNoteButton = (ImageButton) findViewById(R.id.Second16thNote);
        ImageButton fourth16thNoteButton = (ImageButton) findViewById(R.id.Fourth16thNote);
        ImageButton third16thNoteButton = (ImageButton) findViewById(R.id.Third16thNote);
        ImageButton onlyQuarterNoteButton = (ImageButton) findViewById(R.id.OnlyQuarterNote);
        ImageButton first8thNoteButton = (ImageButton) findViewById(R.id.firstEighthNote);
        ImageButton second8thNoteButton = (ImageButton) findViewById(R.id.secondEighthNote);
        if (quarterSubStatus) {
            first16thNoteButton.setVisibility(View.GONE);
            second16thNoteButton.setVisibility(View.GONE);
            third16thNoteButton.setVisibility(View.GONE);
            fourth16thNoteButton.setVisibility(View.GONE);
            onlyQuarterNoteButton.setVisibility(View.VISIBLE);
            first8thNoteButton.setVisibility(View.GONE);
            second8thNoteButton.setVisibility(View.GONE);
        } else if (eighthSubStatus) {
            first16thNoteButton.setVisibility(View.GONE);
            second16thNoteButton.setVisibility(View.GONE);
            third16thNoteButton.setVisibility(View.GONE);
            fourth16thNoteButton.setVisibility(View.GONE);
            onlyQuarterNoteButton.setVisibility(View.GONE);
            first8thNoteButton.setVisibility(View.VISIBLE);
            second8thNoteButton.setVisibility(View.VISIBLE);
        } else if (sixteenthSubStatus) {
            first16thNoteButton.setVisibility(View.VISIBLE);
            second16thNoteButton.setVisibility(View.VISIBLE);
            third16thNoteButton.setVisibility(View.VISIBLE);
            fourth16thNoteButton.setVisibility(View.VISIBLE);
            onlyQuarterNoteButton.setVisibility(View.GONE);
            first8thNoteButton.setVisibility(View.GONE);
            second8thNoteButton.setVisibility(View.GONE);
        }

    }

    public static boolean getQuarterSubStatus() { return quarterSubStatus; }
    public static void setQuarterSubStatus(Boolean qss2) { quarterSubStatus = qss2; }

    public static boolean getEighthSubStatus() { return eighthSubStatus; }
    public static void setEighthSubStatus(Boolean ess2) { eighthSubStatus = ess2; }

    public static boolean getSixteenthSubStatus() { return sixteenthSubStatus; }
    public static void setSixteenthSubStatus(Boolean sss2) { sixteenthSubStatus = sss2; }

    public static boolean getEighthTripSubStatus() { return eighthTripSubStatus; }
    public static void setEighthTripSubStatus(Boolean etss2) { eighthTripSubStatus = etss2; }



}
