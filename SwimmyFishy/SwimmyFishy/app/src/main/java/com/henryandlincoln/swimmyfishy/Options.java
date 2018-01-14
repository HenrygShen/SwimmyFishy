package com.henryandlincoln.swimmyfishy;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;

public class Options extends Activity {

    private static final String PREFS_NAME = "Settings";
    private static final int MAX_VOLUME = 100;
    private int sfxVolume;
    private int bgmVolume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        /* Hide the status bar and title bar*/
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        /* Set up the layout : */
        setContentView(R.layout.options_window);

        /* Set up each volume level */
        SharedPreferences settings = getApplicationContext().getSharedPreferences(PREFS_NAME,0);
        sfxVolume = settings.getInt("sfxVolume",0);
        bgmVolume = settings.getInt("bgmVolume",0);

        setUpScreen();
        setUpControls();
    }

    /* The view for this activity will not take up the full screen, this function view smaller than 100% */
    private void setUpScreen() {

        DisplayMetrics dm  = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        getWindow().setLayout((int) (dm.widthPixels*0.88) ,(int) (dm.heightPixels*0.7));
    }

    private void setUpControls(){
        configureBGMSeekBar();
        configureSoundFXSeekBar();
        configureMenuButton();
    }

    private void configureBGMSeekBar(){

        final SeekBar bgmSeekBar = (SeekBar) findViewById(R.id.BGMSeekbar);
        bgmSeekBar.setMax(MAX_VOLUME);
        bgmSeekBar.setProgress(bgmVolume);

        bgmSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                /* Update the background music volume every time this SeekBar is updated */
                bgmVolume = bgmSeekBar.getProgress();
                /* Save the volume here in case the user escapes this activity using the phone's default back button */
                getApplicationContext().getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE).edit().putInt("bgmVolume",bgmVolume).apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    private void configureSoundFXSeekBar(){

        final SeekBar soundFXSeekBar = (SeekBar) findViewById(R.id.soundFXSeekbar);
        soundFXSeekBar.setMax(MAX_VOLUME);
        soundFXSeekBar.setProgress(sfxVolume);

        soundFXSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                /* Update the sound effects volume every time this SeekBar is updated */
                sfxVolume = soundFXSeekBar.getProgress();
                /* Save the volume here in case the user escapes this activity using the phone's default back button */
                getApplicationContext().getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE).edit().putInt("sfxVolume",sfxVolume).apply();
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
        final MediaPlayer sfx = MediaPlayer.create(this,R.raw.bubble_pop_1);

        backToMenu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                float vol= 1 - (float)(Math.log(MAX_VOLUME - sfxVolume + 1)/Math.log(MAX_VOLUME));
                sfx.setVolume(vol,vol);
                sfx.start();
                finish();
            }
        });
    }
}
