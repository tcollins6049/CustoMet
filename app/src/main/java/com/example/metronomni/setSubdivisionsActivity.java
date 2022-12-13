package com.example.metronomni;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.graphics.Color;


public class setSubdivisionsActivity extends Activity {
    private Handler mHandler;
    Boolean quarterClickable = true;
    Boolean eighthClickable = true;
    Boolean sixteenthClickable = true;
    Boolean eighthTripClickable = true;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subdivisions);

        Intent intent = getIntent();
        String currentTempo = intent.getStringExtra("currentTempo");
        int currBpm = Integer.valueOf(currentTempo);

        ImageButton subsQuarterButton = (ImageButton) findViewById(R.id.setQuarterNoteButton);
        ImageButton subsEighthsButton = (ImageButton) findViewById(R.id.setEighthNoteButton);
        ImageButton subsSixteenthsButton = (ImageButton) findViewById(R.id.setSixteenthNoteButton);
        ImageButton subsEighthTripButton = (ImageButton) findViewById(R.id.setEighthNoteNoteTripletButton);
        TextView quarterText = (TextView) findViewById(R.id.quarterNoteText);
        TextView eighthText = (TextView) findViewById(R.id.eighthNoteText);
        TextView sixteenthText = (TextView) findViewById(R.id.sixteenthNoteText);
        TextView eighthTripText = (TextView) findViewById(R.id.eighthNoteTripText);


        MainActivity mainactivity = new MainActivity();
        ConstraintLayout quarterArea = (ConstraintLayout) findViewById(R.id.quarterArea);
        ConstraintLayout eighthArea = (ConstraintLayout) findViewById(R.id.eighthArea);
        ConstraintLayout sixteenthArea = (ConstraintLayout) findViewById(R.id.sixteenthArea);
        ConstraintLayout eighthTripArea = (ConstraintLayout) findViewById(R.id.EighthTripArea);

        if (mainactivity.getQuarterSubStatus()) {
            quarterArea.setBackgroundColor(Color.GREEN);
        } else if (mainactivity.getEighthSubStatus()) {
            eighthArea.setBackgroundColor(Color.GREEN);
        } else if (mainactivity.getSixteenthSubStatus()) {
            sixteenthArea.setBackgroundColor(Color.GREEN);
        } else if (mainactivity.getEighthTripSubStatus()) {
            eighthTripArea.setBackgroundColor(Color.GREEN);
        }

        if (currBpm > 470) {
            subsQuarterButton.setClickable(false);
            subsQuarterButton.setBackgroundResource(R.color.black);
            quarterArea.setBackgroundResource(R.color.black);
            quarterText.setTextColor(getResources().getColor(R.color.black));
            quarterClickable = false;
        }
        if (currBpm > 230) {
            subsEighthsButton.setClickable(false);
            subsEighthsButton.setBackgroundResource(R.color.black);
            eighthArea.setBackgroundResource(R.color.black);
            eighthText.setTextColor(getResources().getColor(R.color.black));
            eighthClickable = false;
        }
        if (currBpm > 110) {
            subsSixteenthsButton.setClickable(false);
            subsSixteenthsButton.setBackgroundResource(R.color.black);
            sixteenthArea.setBackgroundResource(R.color.black);
            sixteenthText.setTextColor(getResources().getColor(R.color.black));
            sixteenthClickable = false;
        }
        if (currBpm > 155) {
            subsEighthTripButton.setClickable(false);
            subsEighthTripButton.setBackgroundResource(R.color.black);
            eighthTripArea.setBackgroundResource(R.color.black);
            eighthTripText.setTextColor(getResources().getColor(R.color.black));
            eighthTripClickable = false;
        }

        subsQuarterButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (quarterClickable) {
                    JavaMetronome.setQuarterSubs(true);
                    JavaMetronome.setEighthSubs(false);
                    JavaMetronome.set16thSubs(false);
                    JavaMetronome.set8thTripSubs(false);

                    MainActivity.setQuarterSubStatus(true);
                    MainActivity.setEighthSubStatus(false);
                    MainActivity.setSixteenthSubStatus(false);
                    MainActivity.setEighthTripSubStatus(false);

                    MainActivity.setSubText("Quarter Notes");

                    if (quarterClickable) quarterArea.setBackgroundColor(Color.GREEN);
                    if (eighthClickable) eighthArea.setBackgroundResource(R.color.dark_gray);
                    if (sixteenthClickable) sixteenthArea.setBackgroundResource(R.color.dark_gray);
                }
            }
        });
        subsEighthsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (eighthClickable) {
                    JavaMetronome.setQuarterSubs(false);
                    JavaMetronome.setEighthSubs(true);
                    JavaMetronome.set16thSubs(false);
                    JavaMetronome.set8thTripSubs(false);

                    MainActivity.setQuarterSubStatus(false);
                    MainActivity.setEighthSubStatus(true);
                    MainActivity.setSixteenthSubStatus(false);
                    MainActivity.setEighthTripSubStatus(false);

                    MainActivity.setSubText("8th Notes");

                    if (quarterClickable) quarterArea.setBackgroundResource(R.color.dark_gray);
                    if (eighthClickable) eighthArea.setBackgroundColor(Color.GREEN);
                    if (sixteenthClickable) sixteenthArea.setBackgroundResource(R.color.dark_gray);
                }
            }
        });
        subsSixteenthsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (sixteenthClickable) {
                    JavaMetronome.setQuarterSubs(false);
                    JavaMetronome.setEighthSubs(false);
                    JavaMetronome.set16thSubs(true);
                    JavaMetronome.set8thTripSubs(false);

                    MainActivity.setQuarterSubStatus(false);
                    MainActivity.setEighthSubStatus(false);
                    MainActivity.setSixteenthSubStatus(true);
                    MainActivity.setEighthTripSubStatus(false);

                    MainActivity.setSubText("16th Notes");

                    if (quarterClickable) quarterArea.setBackgroundResource(R.color.dark_gray);
                    if (eighthClickable) eighthArea.setBackgroundResource(R.color.dark_gray);
                    if (sixteenthClickable) sixteenthArea.setBackgroundColor(Color.GREEN);
                }
            }
        });

        ImageButton subsEighthNoteTripletsButton = (ImageButton) findViewById(R.id.setEighthNoteNoteTripletButton);
        subsEighthNoteTripletsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                JavaMetronome.setQuarterSubs(false);
                JavaMetronome.setEighthSubs(false);
                JavaMetronome.set16thSubs(false);
                JavaMetronome.set8thTripSubs(true);

                MainActivity.setQuarterSubStatus(false);
                MainActivity.setEighthSubStatus(false);
                MainActivity.setSixteenthSubStatus(false);
                MainActivity.setEighthTripSubStatus(true);

                MainActivity.setSubText("8th Note Triplets");

                if (quarterClickable) quarterArea.setBackgroundResource(R.color.dark_gray);
                if (eighthClickable) eighthArea.setBackgroundResource(R.color.dark_gray);
                if (sixteenthClickable) sixteenthArea.setBackgroundResource(R.color.dark_gray);
                if(eighthTripClickable) eighthTripArea.setBackgroundColor(Color.GREEN);
            }
        });

    }

    public void toMainViewFromSubs(View view) {
        Intent intent = new Intent(setSubdivisionsActivity.this, MainActivity.class);
        startActivity(intent);
    }
}

