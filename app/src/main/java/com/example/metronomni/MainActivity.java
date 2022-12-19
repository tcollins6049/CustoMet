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

    private int bpm = 75;
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
    static Boolean sixtupletSubStatus = false;

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
            currentTempo = "75";
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

        ImageButton first6Note = (ImageButton) findViewById(R.id.first6Note);
        ImageButton second6Note = (ImageButton) findViewById(R.id.second6Note);
        ImageButton third6Note = (ImageButton) findViewById(R.id.third6Note);
        ImageButton fourth6Note = (ImageButton) findViewById(R.id.fourth6Note);
        ImageButton fifth6Note = (ImageButton) findViewById(R.id.fifth6Note);
        ImageButton sixth6Note = (ImageButton) findViewById(R.id.sixth6Note);


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
                    first16thNoteButton.setImageResource(R.drawable.green_quarter);
                } else if (JavaMetronome.getFirst16thIsOn() == 2) {
                    JavaMetronome.setFirst16thIsOn(0);
                    first16thNoteButton.setImageResource(R.drawable.testing4);
                    first16thNoteButton.setAlpha(120);
                } else {
                    JavaMetronome.setFirst16thIsOn(1);
                    first16thNoteButton.setImageResource(R.drawable.testing4);
                    first16thNoteButton.setAlpha(255);
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
                    second16thNoteButton.setImageResource(R.drawable.green_quarter);
                } else if (JavaMetronome.getSecond16thIsOn() == 2) {
                    JavaMetronome.setSecond16thIsOn(0);
                    second16thNoteButton.setImageResource(R.drawable.testing4);
                    second16thNoteButton.setAlpha(120);
                } else {
                    JavaMetronome.setSecond16thIsOn(1);
                    second16thNoteButton.setImageResource(R.drawable.testing4);
                    second16thNoteButton.setAlpha(255);
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
                    third16thNoteButton.setImageResource(R.drawable.green_quarter);
                } else if (JavaMetronome.getThird16thIsOn() == 2) {
                    JavaMetronome.setThird16thIsOn(0);
                    third16thNoteButton.setImageResource(R.drawable.testing4);
                    third16thNoteButton.setAlpha(120);
                } else {
                    JavaMetronome.setThird16thIsOn(1);
                    third16thNoteButton.setImageResource(R.drawable.testing4);
                    third16thNoteButton.setAlpha(255);
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
                    fourth16thNoteButton.setImageResource(R.drawable.green_quarter);
                } else if (JavaMetronome.getFourth16thIsOn() == 2) {
                    JavaMetronome.setFourth16thIsOn(0);
                    fourth16thNoteButton.setImageResource(R.drawable.testing4);
                    fourth16thNoteButton.setAlpha(120);
                } else {
                    JavaMetronome.setFourth16thIsOn(1);
                    fourth16thNoteButton.setImageResource(R.drawable.testing4);
                    fourth16thNoteButton.setAlpha(255);
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
                    onlyQuarterNoteButton.setImageResource(R.drawable.green_quarter);
                } else if (JavaMetronome.getOnlyQuarterIsOn() == 2){
                    JavaMetronome.setOnlyQuarterIsOn(0);
                    onlyQuarterNoteButton.setImageResource(R.drawable.testing4);
                    onlyQuarterNoteButton.setAlpha(120);
                } else {
                    JavaMetronome.setOnlyQuarterIsOn(1);
                    onlyQuarterNoteButton.setImageResource(R.drawable.testing4);
                    onlyQuarterNoteButton.setAlpha(255);
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
                    first8thNoteButton.setImageResource(R.drawable.green_quarter);
                } else if (JavaMetronome.getFirst8thIsOn() == 2){
                    JavaMetronome.setFirst8thIsOn(0);
                    first8thNoteButton.setImageResource(R.drawable.testing4);
                    first8thNoteButton.setAlpha(120);
                } else {
                    JavaMetronome.setFirst8thIsOn(1);
                    first8thNoteButton.setImageResource(R.drawable.testing4);
                    first8thNoteButton.setAlpha(255);
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
                    second8thNoteButton.setImageResource(R.drawable.green_quarter);
                } else if (JavaMetronome.getSecond8thIsOn() == 2){
                    JavaMetronome.setSecond8thIsOn(0);
                    second8thNoteButton.setImageResource(R.drawable.testing4);
                    second8thNoteButton.setAlpha(120);
                } else {
                    JavaMetronome.setSecond8thIsOn(1);
                    second8thNoteButton.setImageResource(R.drawable.testing4);
                    second8thNoteButton.setAlpha(255);
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
                    first8thTripNote.setImageResource(R.drawable.green_quarter);
                } else if (JavaMetronome.getFirst8thTripIsOn() == 2){
                    JavaMetronome.setFirst8thTripIsOn(0);
                    first8thTripNote.setImageResource(R.drawable.testing4);
                    first8thTripNote.setAlpha(120);
                } else {
                    JavaMetronome.setFirst8thTripIsOn(1);
                    first8thTripNote.setImageResource(R.drawable.testing4);
                    first8thTripNote.setAlpha(255);
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
                    second8thTripNote.setImageResource(R.drawable.green_quarter);
                } else if (JavaMetronome.getSecond8thTripIsOn() == 2){
                    JavaMetronome.setSecond8thTripIsOn(0);
                    second8thTripNote.setImageResource(R.drawable.testing4);
                    second8thTripNote.setAlpha(120);
                } else {
                    JavaMetronome.setSecond8thTripIsOn(1);
                    second8thTripNote.setImageResource(R.drawable.testing4);
                    second8thTripNote.setAlpha(255);
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
                    third8thTripNote.setImageResource(R.drawable.green_quarter);
                } else if (JavaMetronome.getThird8thTripIsOn() == 2){
                    JavaMetronome.setThird8thTripIsOn(0);
                    third8thTripNote.setImageResource(R.drawable.testing4);
                    third8thTripNote.setAlpha(120);
                } else {
                    JavaMetronome.setThird8thTripIsOn(1);
                    third8thTripNote.setImageResource(R.drawable.testing4);
                    third8thTripNote.setAlpha(255);
                }
            }
        });
        first6Note.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (isMetOn) {
                    isMetOn = false;
                    startStopButton.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                    metroTask.stop();
                    metroTask = new MetronomeAsyncTask();
                    Runtime.getRuntime().gc();
                }
                if (JavaMetronome.getFirst6() == 1) {
                    JavaMetronome.setFirst6(2);
                    first6Note.setImageResource(R.drawable.green_quarter);
                } else if (JavaMetronome.getFirst6() == 2){
                    JavaMetronome.setFirst6(0);
                    first6Note.setImageResource(R.drawable.testing4);
                    first6Note.setAlpha(120);
                } else {
                    JavaMetronome.setFirst6(1);
                    first6Note.setImageResource(R.drawable.testing4);
                    first6Note.setAlpha(255);
                }
            }
        });
        second6Note.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (isMetOn) {
                    isMetOn = false;
                    startStopButton.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                    metroTask.stop();
                    metroTask = new MetronomeAsyncTask();
                    Runtime.getRuntime().gc();
                }
                if (JavaMetronome.getSecond6() == 1) {
                    JavaMetronome.setSecond6(2);
                    second6Note.setImageResource(R.drawable.green_quarter);
                } else if (JavaMetronome.getSecond6() == 2){
                    JavaMetronome.setSecond6(0);
                    second6Note.setImageResource(R.drawable.testing4);
                    second6Note.setAlpha(120);
                } else {
                    JavaMetronome.setSecond6(1);
                    second6Note.setImageResource(R.drawable.testing4);
                    second6Note.setAlpha(255);
                }
            }
        });
        third6Note.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (isMetOn) {
                    isMetOn = false;
                    startStopButton.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                    metroTask.stop();
                    metroTask = new MetronomeAsyncTask();
                    Runtime.getRuntime().gc();
                }
                if (JavaMetronome.getThird6() == 1) {
                    JavaMetronome.setThird6(2);
                    third6Note.setImageResource(R.drawable.green_quarter);
                } else if (JavaMetronome.getThird6() == 2){
                    JavaMetronome.setThird6(0);
                    third6Note.setImageResource(R.drawable.testing4);
                    third6Note.setAlpha(120);
                } else {
                    JavaMetronome.setThird6(1);
                    third6Note.setImageResource(R.drawable.testing4);
                    third6Note.setAlpha(255);
                }
            }
        });
        fourth6Note.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (isMetOn) {
                    isMetOn = false;
                    startStopButton.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                    metroTask.stop();
                    metroTask = new MetronomeAsyncTask();
                    Runtime.getRuntime().gc();
                }
                if (JavaMetronome.getFourth6() == 1) {
                    JavaMetronome.setFourth6(2);
                    fourth6Note.setImageResource(R.drawable.green_quarter);
                } else if (JavaMetronome.getFourth6() == 2){
                    JavaMetronome.setFourth6(0);
                    fourth6Note.setImageResource(R.drawable.testing4);
                    fourth6Note.setAlpha(120);
                } else {
                    JavaMetronome.setFourth6(1);
                    fourth6Note.setImageResource(R.drawable.testing4);
                    fourth6Note.setAlpha(255);
                }
            }
        });
        fifth6Note.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (isMetOn) {
                    isMetOn = false;
                    startStopButton.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                    metroTask.stop();
                    metroTask = new MetronomeAsyncTask();
                    Runtime.getRuntime().gc();
                }
                if (JavaMetronome.getFifth6() == 1) {
                    JavaMetronome.setFifth6(2);
                    fifth6Note.setImageResource(R.drawable.green_quarter);
                } else if (JavaMetronome.getFifth6() == 2){
                    JavaMetronome.setFifth6(0);
                    fifth6Note.setImageResource(R.drawable.testing4);
                    fifth6Note.setAlpha(120);
                } else {
                    JavaMetronome.setFifth6(1);
                    fifth6Note.setImageResource(R.drawable.testing4);
                    fifth6Note.setAlpha(255);
                }
            }
        });
        sixth6Note.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (isMetOn) {
                    isMetOn = false;
                    startStopButton.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                    metroTask.stop();
                    metroTask = new MetronomeAsyncTask();
                    Runtime.getRuntime().gc();
                }
                if (JavaMetronome.getSixth6() == 1) {
                    JavaMetronome.setSixth6(2);
                    sixth6Note.setImageResource(R.drawable.green_quarter);
                } else if (JavaMetronome.getSixth6() == 2){
                    JavaMetronome.setSixth6(0);
                    sixth6Note.setImageResource(R.drawable.testing4);
                    sixth6Note.setAlpha(120);
                } else {
                    JavaMetronome.setSixth6(1);
                    sixth6Note.setImageResource(R.drawable.testing4);
                    sixth6Note.setAlpha(255);
                }
            }
        });


        metroTask = new MetronomeAsyncTask();

        //currentBeat = (TextView) findViewById(R.id.currentBeat);


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
        ImageButton first6Note = (ImageButton) findViewById(R.id.first6Note);
        ImageButton second6Note = (ImageButton) findViewById(R.id.second6Note);
        ImageButton third6Note = (ImageButton) findViewById(R.id.third6Note);
        ImageButton fourth6Note = (ImageButton) findViewById(R.id.fourth6Note);
        ImageButton fifth6Note = (ImageButton) findViewById(R.id.fifth6Note);
        ImageButton sixth6Note = (ImageButton) findViewById(R.id.sixth6Note);

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

            first6Note.setVisibility(View.GONE);
            second6Note.setVisibility(View.GONE);
            third6Note.setVisibility(View.GONE);
            fourth6Note.setVisibility(View.GONE);
            fifth6Note.setVisibility(View.GONE);
            sixth6Note.setVisibility(View.GONE);
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
            first6Note.setVisibility(View.GONE);
            second6Note.setVisibility(View.GONE);
            third6Note.setVisibility(View.GONE);
            fourth6Note.setVisibility(View.GONE);
            fifth6Note.setVisibility(View.GONE);
            sixth6Note.setVisibility(View.GONE);
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
            first6Note.setVisibility(View.GONE);
            second6Note.setVisibility(View.GONE);
            third6Note.setVisibility(View.GONE);
            fourth6Note.setVisibility(View.GONE);
            fifth6Note.setVisibility(View.GONE);
            sixth6Note.setVisibility(View.GONE);
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
            first6Note.setVisibility(View.GONE);
            second6Note.setVisibility(View.GONE);
            third6Note.setVisibility(View.GONE);
            fourth6Note.setVisibility(View.GONE);
            fifth6Note.setVisibility(View.GONE);
            sixth6Note.setVisibility(View.GONE);
        } else if (sixtupletSubStatus) {
            first16thNoteButton.setVisibility(View.GONE);
            second16thNoteButton.setVisibility(View.GONE);
            third16thNoteButton.setVisibility(View.GONE);
            fourth16thNoteButton.setVisibility(View.GONE);
            onlyQuarterNoteButton.setVisibility(View.GONE);
            first8thNoteButton.setVisibility(View.GONE);
            second8thNoteButton.setVisibility(View.GONE);
            first8thTripNote.setVisibility(View.GONE);
            second8thTripNote.setVisibility(View.GONE);
            third8thTripNote.setVisibility(View.GONE);
            first6Note.setVisibility(View.VISIBLE);
            second6Note.setVisibility(View.VISIBLE);
            third6Note.setVisibility(View.VISIBLE);
            fourth6Note.setVisibility(View.VISIBLE);
            fifth6Note.setVisibility(View.VISIBLE);
            sixth6Note.setVisibility(View.VISIBLE);
        }

    }

    public static void setSubText(String text) { subText = text;}

    public static boolean getQuarterSubStatus() { return quarterSubStatus; }
    public static void setQuarterSubStatus(Boolean qss2) { quarterSubStatus = qss2; }

    public static boolean getEighthSubStatus() { return eighthSubStatus; }
    public static void setEighthSubStatus(Boolean ess2) { eighthSubStatus = ess2; }

    public static boolean getSixteenthSubStatus() { return sixteenthSubStatus; }
    public static void setSixteenthSubStatus(Boolean sss2) { sixteenthSubStatus = sss2; }

    public static boolean getEighthTripSubStatus() { return eighthTripSubStatus; }
    public static void setEighthTripSubStatus(boolean etss2) { eighthTripSubStatus = etss2; }

    public static boolean getSixtupletSubStatus() { return sixtupletSubStatus; }
    public static void setSixtupletSubStatus(boolean status) { sixtupletSubStatus = status; }



}
