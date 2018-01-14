package com.henryandlincoln.swimmyfishy;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;

public class Options extends Activity {

    private AudioManager audioMananger;
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

        SeekBar bgmSeekbar  = (SeekBar) findViewById(R.id.BGMSeekbar);
        bgmSeekbar.getProgress();

    }

    private void configureSoundFXSeekbar(){

        SeekBar soundFXSeekbar = (SeekBar) findViewById(R.id.soundFXSeekbar);

        try {
            audioMananger  = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            soundFXSeekbar.setMax(audioMananger.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
            soundFXSeekbar.setProgress(audioMananger.getStreamVolume(AudioManager.STREAM_MUSIC));

            soundFXSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){


                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    audioMananger.setStreamVolume(AudioManager.STREAM_MUSIC,i,0);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
             });
        }
        catch (Exception ex){

        }
    }

    private void configureMenuButton(){

        Button backToMenu = (Button) findViewById(R.id.backToMenu);
        final MediaPlayer mp = MediaPlayer.create(this,R.raw.bubble_pop_1);
        backToMenu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                mp.start();
                finish();
            }
        });
    }
}
