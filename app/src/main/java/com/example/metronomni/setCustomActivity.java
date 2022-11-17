package com.example.metronomni;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class setCustomActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);


    }

    public void toMainViewFromCustom(View view) {
        Intent intent = new Intent(setCustomActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
