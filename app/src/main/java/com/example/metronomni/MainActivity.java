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
import android.widget.Switch;
import android.widget.CompoundButton;

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

    static String subText = "Quarter Notes";

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
        ImageButton first8thTripNote = (ImageButton) findViewById(R.id.first8thTripNote);
        ImageButton second8thTripNote = (ImageButton) findViewById(R.id.second8thTripNote);
        ImageButton third8thTripNote = (ImageButton) findViewById(R.id.third8thTripNote);


        Switch accent1Switch = (Switch) findViewById(R.id.pulseToggle);

        TextView subTextView = (TextView) findViewById(R.id.subdivisionName);
        subTextView.setText(subText);

        accent1Switch.setChecked(false);
        JavaMetronome.setBeat1Accent(false);
        accent1Switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isMetOn) {
                    isMetOn = false;
                    startStopButton.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                    metroTask.stop();
                    metroTask = new MetronomeAsyncTask();
                    Runtime.getRuntime().gc();
                }
                if (isChecked) {
                    JavaMetronome.setBeat1Accent(true);
                } else {
                    JavaMetronome.setBeat1Accent(false);
                }
            }
        });

        first16thNoteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (isMetOn) {
                    isMetOn = false;
                    startStopButton.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                    metroTask.stop();
                    metroTask = new MetronomeAsyncTask();
                    Runtime.getRuntime().gc();
                }
                if (JavaMetronome.getFirst16thIsOn() == 1) {
                    JavaMetronome.setFirst16thIsOn(2);
                    //first16thNoteButton.setImageResource(R.drawable.quarterrest1);
                } else if (JavaMetronome.getFirst16thIsOn() == 2) {
                    JavaMetronome.setFirst16thIsOn(0);
                    first16thNoteButton.setImageResource(R.drawable.quarterrest1);
                } else {
                    JavaMetronome.setFirst16thIsOn(1);
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
                if (JavaMetronome.getSecond16thIsOn() == 1) {
                    JavaMetronome.setSecond16thIsOn(2);
                    //first16thNoteButton.setImageResource(R.drawable.quarterrest1);
                } else if (JavaMetronome.getSecond16thIsOn() == 2) {
                    JavaMetronome.setSecond16thIsOn(0);
                    second16thNoteButton.setImageResource(R.drawable.quarterrest1);
                } else {
                    JavaMetronome.setSecond16thIsOn(1);
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
                if (JavaMetronome.getThird16thIsOn() == 1) {
                    JavaMetronome.setThird16thIsOn(2);
                    //first16thNoteButton.setImageResource(R.drawable.quarterrest1);
                } else if (JavaMetronome.getThird16thIsOn() == 2) {
                    JavaMetronome.setThird16thIsOn(0);
                    third16thNoteButton.setImageResource(R.drawable.quarterrest1);
                } else {
                    JavaMetronome.setThird16thIsOn(1);
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
                if (JavaMetronome.getFourth16thIsOn() == 1) {
                    JavaMetronome.setFourth16thIsOn(2);
                    //first16thNoteButton.setImageResource(R.drawable.quarterrest1);
                } else if (JavaMetronome.getFourth16thIsOn() == 2) {
                    JavaMetronome.setFourth16thIsOn(0);
                    fourth16thNoteButton.setImageResource(R.drawable.quarterrest1);
                } else {
                    JavaMetronome.setFourth16thIsOn(1);
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
                if (JavaMetronome.getOnlyQuarterIsOn() == 1) {
                    JavaMetronome.setOnlyQuarterIsOn(2);
                } else if (JavaMetronome.getOnlyQuarterIsOn() == 2){
                    JavaMetronome.setOnlyQuarterIsOn(0);
                    onlyQuarterNoteButton.setImageResource(R.drawable.quarterrest1);
                } else {
                    JavaMetronome.setOnlyQuarterIsOn(1);
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
                if (JavaMetronome.getFirst8thIsOn() == 1) {
                    JavaMetronome.setFirst8thIsOn(2);
                } else if (JavaMetronome.getFirst8thIsOn() == 2){
                    JavaMetronome.setFirst8thIsOn(0);
                    first8thNoteButton.setImageResource(R.drawable.quarterrest1);
                } else {
                    JavaMetronome.setFirst8thIsOn(1);
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
                if (JavaMetronome.getSecond8thIsOn() == 1) {
                    JavaMetronome.setSecond8thIsOn(2);
                } else if (JavaMetronome.getSecond8thIsOn() == 2){
                    JavaMetronome.setSecond8thIsOn(0);
                    second8thNoteButton.setImageResource(R.drawable.quarterrest1);
                } else {
                    JavaMetronome.setSecond8thIsOn(1);
                    second8thNoteButton.setImageResource(R.drawable.testing4);
                }
            }
        });
        first8thTripNote.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (isMetOn) {
                    isMetOn = false;
                    startStopButton.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                    metroTask.stop();
                    metroTask = new MetronomeAsyncTask();
                    Runtime.getRuntime().gc();
                }
                if (JavaMetronome.getFirst8thTripIsOn() == 1) {
                    JavaMetronome.setFirst8thTripIsOn(2);
                } else if (JavaMetronome.getFirst8thTripIsOn() == 2){
                    JavaMetronome.setFirst8thTripIsOn(0);
                    first8thTripNote.setImageResource(R.drawable.quarterrest1);
                } else {
                    JavaMetronome.setFirst8thTripIsOn(1);
                    first8thTripNote.setImageResource(R.drawable.testing4);
                }
            }
        });
        second8thTripNote.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (isMetOn) {
                    isMetOn = false;
                    startStopButton.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                    metroTask.stop();
                    metroTask = new MetronomeAsyncTask();
                    Runtime.getRuntime().gc();
                }
                if (JavaMetronome.getSecond8thTripIsOn() == 1) {
                    JavaMetronome.setSecond8thTripIsOn(2);
                } else if (JavaMetronome.getSecond8thTripIsOn() == 2){
                    JavaMetronome.setSecond8thTripIsOn(0);
                    second8thTripNote.setImageResource(R.drawable.quarterrest1);
                } else {
                    JavaMetronome.setSecond8thTripIsOn(1);
                    second8thTripNote.setImageResource(R.drawable.testing4);
                }
            }
        });
        third8thTripNote.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (isMetOn) {
                    isMetOn = false;
                    startStopButton.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                    metroTask.stop();
                    metroTask = new MetronomeAsyncTask();
                    Runtime.getRuntime().gc();
                }
                if (JavaMetronome.getThird8thTripIsOn() == 1) {
                    JavaMetronome.setThird8thTripIsOn(2);
                } else if (JavaMetronome.getThird8thTripIsOn() == 2){
                    JavaMetronome.setThird8thTripIsOn(0);
                    third8thTripNote.setImageResource(R.drawable.quarterrest1);
                } else {
                    JavaMetronome.setThird8thTripIsOn(1);
                    third8thTripNote.setImageResource(R.drawable.testing4);
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
        ImageButton first8thTripNote = (ImageButton) findViewById(R.id.first8thTripNote);
        ImageButton second8thTripNote = (ImageButton) findViewById(R.id.second8thTripNote);
        ImageButton third8thTripNote = (ImageButton) findViewById(R.id.third8thTripNote);

        if (quarterSubStatus) {
            first16thNoteButton.setVisibility(View.GONE);
            second16thNoteButton.setVisibility(View.GONE);
            third16thNoteButton.setVisibility(View.GONE);
            fourth16thNoteButton.setVisibility(View.GONE);
            onlyQuarterNoteButton.setVisibility(View.VISIBLE);
            first8thNoteButton.setVisibility(View.GONE);
            second8thNoteButton.setVisibility(View.GONE);
            first8thTripNote.setVisibility(View.GONE);
            second8thTripNote.setVisibility(View.GONE);
            third8thTripNote.setVisibility(View.GONE);
        } else if (eighthSubStatus) {
            first16thNoteButton.setVisibility(View.GONE);
            second16thNoteButton.setVisibility(View.GONE);
            third16thNoteButton.setVisibility(View.GONE);
            fourth16thNoteButton.setVisibility(View.GONE);
            onlyQuarterNoteButton.setVisibility(View.GONE);
            first8thNoteButton.setVisibility(View.VISIBLE);
            second8thNoteButton.setVisibility(View.VISIBLE);
            first8thTripNote.setVisibility(View.GONE);
            second8thTripNote.setVisibility(View.GONE);
            third8thTripNote.setVisibility(View.GONE);
        } else if (sixteenthSubStatus) {
            first16thNoteButton.setVisibility(View.VISIBLE);
            second16thNoteButton.setVisibility(View.VISIBLE);
            third16thNoteButton.setVisibility(View.VISIBLE);
            fourth16thNoteButton.setVisibility(View.VISIBLE);
            onlyQuarterNoteButton.setVisibility(View.GONE);
            first8thNoteButton.setVisibility(View.GONE);
            second8thNoteButton.setVisibility(View.GONE);
            first8thTripNote.setVisibility(View.GONE);
            second8thTripNote.setVisibility(View.GONE);
            third8thTripNote.setVisibility(View.GONE);
        } else if (eighthTripSubStatus) {
            first16thNoteButton.setVisibility(View.GONE);
            second16thNoteButton.setVisibility(View.GONE);
            third16thNoteButton.setVisibility(View.GONE);
            fourth16thNoteButton.setVisibility(View.GONE);
            onlyQuarterNoteButton.setVisibility(View.GONE);
            first8thNoteButton.setVisibility(View.GONE);
            second8thNoteButton.setVisibility(View.GONE);
            first8thTripNote.setVisibility(View.VISIBLE);
            second8thTripNote.setVisibility(View.VISIBLE);
            third8thTripNote.setVisibility(View.VISIBLE);
        }

    }

    public static void setSubText(String text) { subText = text;}

    public static boolean getQuarterSubStatus() { return quarterSubStatus; }
    public static void setQuarterSubStatus(Boolean qss2) { quarterSubStatus = qss2; }

    public static boolean getEighthSubStatus() { return eighthSubStatus; }
    public static void setEighthSubStatus(Boolean ess2) { eighthSubStatus = ess2; }

    public static boolean getSixteenthSubStatus() { return sixteenthSubStatus; }
    public static void setSixteenthSubStatus(Boolean sss2) { sixteenthSubStatus = sss2; }

    public static Boolean getEighthTripSubStatus() { return eighthTripSubStatus; }
    public static void setEighthTripSubStatus(Boolean etss2) { eighthTripSubStatus = etss2; }



}
