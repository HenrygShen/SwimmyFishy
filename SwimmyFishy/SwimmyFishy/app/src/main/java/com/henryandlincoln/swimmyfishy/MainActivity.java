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


public class MainActivity extends Activity {

    public static final String PREFS_NAME = "Settings";
    private static final int MAX_VOLUME = 100;
    private String highScore = "-1";
    private int sfxVolume;
    private int bgmVolume;
    private MediaPlayer mediaPlayer;

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

        mediaPlayer = MediaPlayer.create(this,R.raw.bgm);
        mediaPlayer.setVolume(0.5f,0.5f);
        mediaPlayer.start();
    }

    @Override
    protected void onResume(){
        super.onResume();

        /* Update these values according to the SharedPreferences when this activity resumes */
        sfxVolume = getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE).getInt("sfxVolume",30);
        bgmVolume = getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE).getInt("bgmVolume",30);
        highScore = getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE).getString("highScore","0");

        setVolume(mediaPlayer,bgmVolume);

        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    @Override
    protected void onStop(){

        super.onStop();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    private void loadSettings(){

        SharedPreferences settings = this.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        sfxVolume = settings.getInt("sfxVolume",0);
        bgmVolume = settings.getInt("bgmVolume",0);
        highScore = settings.getString("highScore","N/A");
        TextView tv = (TextView) findViewById(R.id.high_score);
        tv.setText("High Score : "  + highScore);
    }

    private void configurePlayButton(){

        Button playBtn = (Button) findViewById(R.id.play);
        final MediaPlayer soundEffectButton =  MediaPlayer.create(this,R.raw.bubble_pop_3);

        playBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                setVolume(soundEffectButton,sfxVolume);
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

                setVolume(soundEffectButton,sfxVolume);
                soundEffectButton.start();

                /* Start the Options activity */
                Intent i =  new Intent(MainActivity.this,Options.class);
                startActivity(i);
            }
        });
    }

    private void setVolume(MediaPlayer mp,int currentVolume){
        float volume = (1 - (float)(Math.log(MAX_VOLUME - currentVolume + 1)/Math.log(MAX_VOLUME)));
        mp.setVolume(volume,volume);
    }

}
