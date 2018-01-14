package com.henryandlincoln.swimmyfishy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class MainActivity extends Activity {

    public static final String PREFS_NAME = "Settings";
    private String highScore = "-1";
    private static int VOLUME_SET_REQUEST_CODE = 0;
    private int sfxVolume;
    private int bgmVolume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /* Hides the status bar */
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        /* Read setting.txt from assets folder and load these settings */
        //TODO

        loadSettings();
        configurePlayButton();
        configureOptionsButton();
    }


    protected void loadSettings(){

        SharedPreferences settings = this.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        sfxVolume = settings.getInt("sfxVolume",0);
        bgmVolume = settings.getInt("bgmVolume",0);
        highScore = settings.getString("highScore","0");
        TextView tv = (TextView) findViewById(R.id.high_score);
        tv.setText("High Score : "  + highScore);
        highScore = "39";
        settings.edit().putString("highScore",highScore).apply();


    }



    protected void configurePlayButton(){

        Button playBtn = (Button) findViewById(R.id.play);
        final MediaPlayer mp =  MediaPlayer.create(this,R.raw.bubble_pop_3);

        playBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i  = new Intent(MainActivity.this,GameActivity.class);
                mp.setVolume(sfxVolume,sfxVolume);
                mp.start();
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
                Intent i =  new Intent(MainActivity.this,Options.class);
                sfxVolume = getApplicationContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).getInt("sfxVolume",0);
                bgmVolume = getApplicationContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).getInt("bgmVolume",0);
                float vol=(float)(Math.log(100-sfxVolume)/Math.log(100));
                mp.setVolume(1-vol,1-vol);
                mp.start();
                startActivity(i);
            }
        });
    }




}
