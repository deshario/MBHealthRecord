package com.deshario.mbhealthrecord;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Deshario on 12/25/2016.
 */

public class SplashScreenActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Intent welcome = new Intent(SplashScreenActivity.this,WelcomeActivity.class);
                Intent welcome = new Intent(SplashScreenActivity.this,UserFirstInfo.class);
                startActivity(welcome);
                finish();
            }
        },SPLASH_TIME_OUT);
    }
}
