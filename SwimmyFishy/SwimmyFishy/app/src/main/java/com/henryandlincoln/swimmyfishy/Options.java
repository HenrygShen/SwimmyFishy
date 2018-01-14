package com.henryandlincoln.swimmyfishy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Options extends Activity {

    public static final String PREFS_NAME = "Settings";
    public int sfxVolume;
    private int bgmVolume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        /* Set up the layout :

        Hide the status bar */
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        /* Hide the title bar */
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        /* Set the layout */
        setContentView(R.layout.options_window);

        /* Grab the volumes passed through main activity */
        SharedPreferences settings = getApplicationContext().getSharedPreferences(PREFS_NAME,0);
        sfxVolume = settings.getInt("sfxVolume",0);
        bgmVolume = getIntent().getIntExtra("bgmVolume",0);
        setUpScreen();

    }
    private void setUpScreen() {

        DisplayMetrics dm  = new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout( (int)(width*0.88),(int) (height*0.7));
        configureInteractions();
    }

    private void configureInteractions(){
        configureMenuButton();
        configureBGMSeekbar();
        configureSoundFXSeekbar();
    }

    private void configureBGMSeekbar(){

        final SeekBar bgmSeekBar = (SeekBar) findViewById(R.id.BGMSeekbar);
        bgmSeekBar.setMax(100);
        bgmSeekBar.setProgress(bgmVolume);

        bgmSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                bgmVolume = bgmSeekBar.getProgress();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    private void configureSoundFXSeekbar(){

        final SeekBar soundFXSeekBar = (SeekBar) findViewById(R.id.soundFXSeekbar);
        soundFXSeekBar.setMax(100);
        soundFXSeekBar.setProgress(sfxVolume);

        soundFXSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                sfxVolume = soundFXSeekBar.getProgress();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void configureMenuButton(){

        Button backToMenu = (Button) findViewById(R.id.backToMenu);
        final MediaPlayer mp = MediaPlayer.create(this,R.raw.bubble_pop_1);

        backToMenu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                SharedPreferences settings = getApplicationContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                settings.edit().putInt("sfxVolume",sfxVolume).apply();
                settings.edit().putInt("bgmVolume",bgmVolume).apply();
                float vol=(float)(Math.log(100-sfxVolume)/Math.log(100));
                mp.setVolume(1-vol,1-vol);
                mp.start();
                finish();
            }
        });
    }
}
