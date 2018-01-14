package com.henryandlincoln.swimmyfishy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity {

    public static final String PREFS_NAME = "Settings";
    private String highScore = "-1";
    private int sfxVolume;
    private int bgmVolume;
    private MediaPlayer mp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /* Hides the status bar */
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        /* Read and load settings using SharedPreferences */
        loadSettings();
        configurePlayButton();
        configureOptionsButton();

        mp = MediaPlayer.create(this,R.raw.bgm);
        float vol=(float)(Math.log(100-bgmVolume)/Math.log(100));
        mp.setVolume(1-vol,1-vol);
        mp.start();
    }

    @Override
    protected void onResume(){
        super.onResume();
        sfxVolume = getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE).getInt("sfxVolume",0);
        bgmVolume = getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE).getInt("bgmVolume",0);
        highScore = getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE).getString("highScore","0");
        float vol=(float)(Math.log(100-bgmVolume)/Math.log(100));
        if (bgmVolume == 100 ){
            vol = 0;
        }
        mp.setVolume(1-vol,1-vol);

    }

    protected void loadSettings(){

        SharedPreferences settings = this.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        sfxVolume = settings.getInt("sfxVolume",0);
        bgmVolume = settings.getInt("bgmVolume",0);
        highScore = settings.getString("highScore","N/A");
        TextView tv = (TextView) findViewById(R.id.high_score);
        tv.setText("High Score : "  + highScore);
    }

    protected void configurePlayButton(){

        Button playBtn = (Button) findViewById(R.id.play);
        final MediaPlayer mp =  MediaPlayer.create(this,R.raw.bubble_pop_3);

        playBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                float vol=(float)(Math.log(100-sfxVolume)/Math.log(100));
                mp.setVolume(1-vol,1-vol);
                mp.start();

                /* Start the game */
                Intent i  = new Intent(MainActivity.this,GameActivity.class);
                startActivity(i);
            }
        });
    }

    protected void configureOptionsButton(){

        Button setBtn = (Button) findViewById(R.id.options);
        final MediaPlayer mp = MediaPlayer.create(this,R.raw.bubble_pop_2);

        setBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                float vol=(float)(Math.log(100-sfxVolume)/Math.log(100));
                mp.setVolume(1-vol,1-vol);
                mp.start();

                /* Start the Options activity */
                Intent i =  new Intent(MainActivity.this,Options.class);
                startActivity(i);
            }
        });
    }

}
