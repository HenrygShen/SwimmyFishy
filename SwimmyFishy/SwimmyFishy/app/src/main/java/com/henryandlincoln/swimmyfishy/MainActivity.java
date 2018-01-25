package com.henryandlincoln.swimmyfishy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity {

    private static final String PREFS_NAME = "Settings";
    private long lastClickTime = 0;
    private String highScore = "-1";



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        /* Remove status,title bar and lock screen to portrait */
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        /* Read and load settings using SharedPreferences */
        loadSettings();
        configurePlayButton();
        configureOptionsButton();
        configureCharacterSelButton();

    }

    @Override
    protected void onResume(){
        super.onResume();

        /* Load high score when activity resumes */
        highScore = getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE).getString("highScore","0");
    }

    @Override
    protected void onStop(){
        super.onStop();

    }

    private void loadSettings(){

        SharedPreferences settings = this.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        highScore = settings.getString("highScore","N/A");
        TextView tv = (TextView) findViewById(R.id.high_score);
        tv.setText(highScore);
    }

    private void configurePlayButton(){

        Button playBtn = (Button) findViewById(R.id.play);
        final MediaPlayer soundEffectButton =  MediaPlayer.create(this,R.raw.bubble_pop_3);

        playBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                if (SystemClock.elapsedRealtime() - lastClickTime < 1000){
                    return;
                }

                lastClickTime = SystemClock.elapsedRealtime();

                soundEffectButton.start();

                /* Start the game */
                Intent i  = new Intent(MainActivity.this,GameActivity.class);
                startActivity(i);
            }
        });
    }

    private void configureOptionsButton(){

        Button setBtn = (Button) findViewById(R.id.options);
        final MediaPlayer soundEffectButton = MediaPlayer.create(this,R.raw.bubble_pop_2);

        setBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                if (SystemClock.elapsedRealtime() - lastClickTime < 1000){
                    return;
                }

                lastClickTime = SystemClock.elapsedRealtime();

                soundEffectButton.start();

                /* Start the Options activity */
                Intent i =  new Intent(MainActivity.this,Options.class);
                startActivity(i);
            }
        });
    }

    private void configureCharacterSelButton(){

        Button selBtn = (Button) findViewById(R.id.characterSelect);

        final MediaPlayer soundEffectButton = MediaPlayer.create(this,R.raw.bubble_pop_2);

        selBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                if (SystemClock.elapsedRealtime() - lastClickTime < 1000){
                    return;
                }

                lastClickTime = SystemClock.elapsedRealtime();

                soundEffectButton.start();

                /* Open the character select screen */
                Intent i = new Intent(MainActivity.this,CharacterSelectActivity.class);
                startActivity(i);

            }
        });

    }

}
