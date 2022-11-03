package com.example.metronomni;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

public class setSubdivisionsActivity extends Activity {
    private Handler mHandler;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subdivisions);


        Button subsQuarterButton = (Button) findViewById(R.id.setQuarterNoteButton);
        subsQuarterButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                JavaMetronome.setQuarterSubs(true);
                JavaMetronome.setEighthSubs(false);
                JavaMetronome.set16thSubs(false);
            }
        });

        Button subsEightsButton = (Button) findViewById(R.id.setEighthNoteButton);
        subsQuarterButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                JavaMetronome.setQuarterSubs(false);
                JavaMetronome.setEighthSubs(true);
                JavaMetronome.set16thSubs(false);
            }
        });

        Button subsSixteenthsButton = (Button) findViewById(R.id.setSixteenthNoteButton);
        subsQuarterButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                JavaMetronome.setQuarterSubs(false);
                JavaMetronome.setEighthSubs(false);
                JavaMetronome.set16thSubs(true);
            }
        });
    }
}
