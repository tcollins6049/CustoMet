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

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subdivisions);

        MainActivity mainactivity = new MainActivity();
        ConstraintLayout quarterArea = (ConstraintLayout) findViewById(R.id.quarterArea);
        ConstraintLayout eighthArea = (ConstraintLayout) findViewById(R.id.eighthArea);
        if (mainactivity.getQuarterSubStatus()) {
            quarterArea.setBackgroundColor(Color.GREEN);
        }

        ImageButton subsQuarterButton = (ImageButton) findViewById(R.id.setQuarterNoteButton);
        subsQuarterButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                JavaMetronome.setQuarterSubs(true);
                JavaMetronome.setEighthSubs(false);
                JavaMetronome.set16thSubs(false);
                JavaMetronome.set8thTripSubs(false);

                MainActivity.setQuarterSubStatus(true);
                MainActivity.setEighthSubStatus(false);
                MainActivity.setSixteenthSubStatus(false);
                MainActivity.setEighthTripSubStatus(false);
            }
        });

        ImageButton subsEighthsButton = (ImageButton) findViewById(R.id.setEighthNoteButton);
        subsEighthsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                JavaMetronome.setQuarterSubs(false);
                JavaMetronome.setEighthSubs(true);
                JavaMetronome.set16thSubs(false);
                JavaMetronome.set8thTripSubs(false);

                MainActivity.setQuarterSubStatus(false);
                MainActivity.setEighthSubStatus(true);
                MainActivity.setSixteenthSubStatus(false);
                MainActivity.setEighthTripSubStatus(false);

                quarterArea.setBackgroundResource(R.color.dark_gray);
                eighthArea.setBackgroundColor(Color.GREEN);
            }
        });

        /*ImageButton subsSixteenthsButton = (ImageButton) findViewById(R.id.setSixteenthNoteButton);
        subsSixteenthsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                JavaMetronome.setQuarterSubs(false);
                JavaMetronome.setEighthSubs(false);
                JavaMetronome.set16thSubs(true);
                JavaMetronome.set8thTripSubs(false);

                MainActivity.setQuarterSubStatus(false);
                MainActivity.setEighthSubStatus(false);
                MainActivity.setSixteenthSubStatus(true);
                MainActivity.setEighthTripSubStatus(false);
            }
        });

        ImageButton subsEighthNoteTripletsButton = (ImageButton) findViewById(R.id.setEighthNoteTripletButton);
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
            }
        });*/

    }

    public void toMainViewFromSubs(View view) {
        Intent intent = new Intent(setSubdivisionsActivity.this, MainActivity.class);
        startActivity(intent);
    }
}

