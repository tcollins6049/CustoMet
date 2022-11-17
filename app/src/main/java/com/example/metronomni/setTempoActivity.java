package com.example.metronomni;
import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;

public class setTempoActivity extends Activity {

    int tempo = 0;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tempo);
        //tempo = 0;

        // Gets current tempo from main activity and places that in the text view //
        Intent intent = getIntent();
        String currTempo = intent.getStringExtra("currentTempo");
        TextView tempoText = (TextView) findViewById(R.id.tempoText1);
        tempoText.setText(currTempo);


        // Button Declerations //
        Button zeroButton = (Button) findViewById(R.id.zeroButton);
        Button oneButton = (Button) findViewById(R.id.oneButton);
        Button twoButton = (Button) findViewById(R.id.twoButton);
        Button threeButton = (Button) findViewById(R.id.threeButton);
        Button fourButton = (Button) findViewById(R.id.fourButton);
        Button fiveButton = (Button) findViewById(R.id.fiveButton);
        Button sixButton = (Button) findViewById(R.id.sixButton);
        Button sevenButton = (Button) findViewById(R.id.sevenButton);
        Button eightButton = (Button) findViewById(R.id.eightButton);
        Button nineButton = (Button) findViewById(R.id.nineButton);
        Button clearButton = (Button) findViewById(R.id.clearButton);
        Button setButton = (Button) findViewById(R.id.setButton);


        zeroButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (tempo == 0) {
                    tempo = tempo + 0;
                    tempoText.setText(Integer.toString(tempo));
                }
                else if (tempo > 0 && tempo < 100) {
                    tempo = (tempo * 10) + 0;
                    tempoText.setText(Integer.toString(tempo));
                }
                checkBpmBounds(tempo);
            }
        });
        oneButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (tempo == 0) {
                    tempo = tempo + 1;
                    tempoText.setText(Integer.toString(tempo));
                }
                else if (tempo > 0 && tempo < 100) {
                    tempo = (tempo * 10) + 1;
                    tempoText.setText(Integer.toString(tempo));
                }
                checkBpmBounds(tempo);
            }
        });
        twoButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (tempo == 0) {
                    tempo = tempo + 2;
                    tempoText.setText(Integer.toString(tempo));
                }
                else if (tempo > 0 && tempo < 100) {
                    tempo = (tempo * 10) + 2;
                    tempoText.setText(Integer.toString(tempo));
                }
                checkBpmBounds(tempo);
            }
        });
        threeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (tempo == 0) {
                    tempo = tempo + 3;
                    tempoText.setText(Integer.toString(tempo));
                }
                else if (tempo > 0 && tempo < 100) {
                    tempo = (tempo * 10) + 3;
                    tempoText.setText(Integer.toString(tempo));
                }
                checkBpmBounds(tempo);
            }
        });
        fourButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (tempo == 0) {
                    tempo = tempo + 4;
                    tempoText.setText(Integer.toString(tempo));
                }
                else if (tempo > 0 && tempo < 100) {
                    tempo = (tempo * 10) + 4;
                    tempoText.setText(Integer.toString(tempo));
                }
                checkBpmBounds(tempo);
            }
        });
        fiveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (tempo == 0) {
                    tempo = tempo + 5;
                    tempoText.setText(Integer.toString(tempo));
                }
                else if (tempo > 0 && tempo < 100) {
                    tempo = (tempo * 10) + 5;
                    tempoText.setText(Integer.toString(tempo));
                }
                checkBpmBounds(tempo);
            }
        });
        sixButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (tempo == 0) {
                    tempo = tempo + 6;
                    tempoText.setText(Integer.toString(tempo));
                }
                else if (tempo > 0 && tempo < 100) {
                    tempo = (tempo * 10) + 6;
                    tempoText.setText(Integer.toString(tempo));
                }
                checkBpmBounds(tempo);
            }
        });
        sevenButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (tempo == 0) {
                    tempo = tempo + 7;
                    tempoText.setText(Integer.toString(tempo));
                }
                else if (tempo > 0 && tempo < 100) {
                    tempo = (tempo * 10) + 7;
                    tempoText.setText(Integer.toString(tempo));
                }
                checkBpmBounds(tempo);
            }
        });
        eightButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (tempo == 0) {
                    tempo = tempo + 8;
                    tempoText.setText(Integer.toString(tempo));
                }
                else if (tempo > 0 && tempo < 100) {
                    tempo = (tempo * 10) + 8;
                    tempoText.setText(Integer.toString(tempo));
                }
                checkBpmBounds(tempo);
            }
        });
        nineButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (tempo == 0) {
                    tempo = tempo + 9;
                    tempoText.setText(Integer.toString(tempo));
                }
                else if (tempo > 0 && tempo < 100) {
                    tempo = (tempo * 10) + 9;
                    tempoText.setText(Integer.toString(tempo));
                }
                checkBpmBounds(tempo);
            }
        });
        clearButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tempo = 0;
                tempoText.setText(Integer.toString(tempo));
            }
        });
    }

    public void checkBpmBounds(int tempo) {
        TextView tempoText = (TextView) findViewById(R.id.tempoText1);
        MainActivity mainactivity = new MainActivity();
        if (mainactivity.getQuarterSubStatus()) {
            if (tempo > 480) {
                tempo = 480;
                tempoText.setText(Integer.toString(tempo));
            }
        } else if (mainactivity.getEighthSubStatus()) {
            if (tempo > 240) {
                tempo = 240;
                tempoText.setText(Integer.toString(tempo));
            }
        } else if (mainactivity.getSixteenthSubStatus()) {
            if (tempo > 120) {
                tempo = 120;
                tempoText.setText(Integer.toString(tempo));
            }
        }
    }

    public void toMainView(View view) {
        Button setButton = (Button) findViewById(R.id.setButton);
        TextView tempoText = (TextView) findViewById(R.id.tempoText1);
        String currTempo = tempoText.getText().toString();
        Intent intent = new Intent(setTempoActivity.this, MainActivity.class);
        intent.putExtra("currentTempo", currTempo);
        startActivity(intent);
    }
}