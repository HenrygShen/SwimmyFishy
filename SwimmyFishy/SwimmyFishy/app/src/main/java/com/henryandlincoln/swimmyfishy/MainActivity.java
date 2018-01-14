package com.henryandlincoln.swimmyfishy;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class MainActivity extends Activity {

    private String highScore = "-1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /* Hides the status bar */
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        /* Read setting.txt from assets folder and load these settings */
        //TODO
        configureScreen();
        configurePlayButton();
        configureOptionsButton();
    }


    protected void configureScreen(){
        loadHighScore();
    }

    private void loadHighScore(){
        try {
            InputStream is = getAssets().open("settings.txt");
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine())!=null){
                if (line.contains("HS")){
                    int i = line.indexOf("=");
                    this.highScore = line.substring(i + 1,line.length()).trim();
                }
            }

        }
        catch (IOException ex){
        }
        TextView tv = (TextView) findViewById(R.id.high_score);
        tv.setText("High score : " + this.highScore);

    }

    protected void configurePlayButton(){

        Button playBtn = (Button) findViewById(R.id.play);
        final MediaPlayer mp =  MediaPlayer.create(this,R.raw.bubble_pop_3);

        playBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i  = new Intent(MainActivity.this,GameActivity.class);
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
                mp.start();
                startActivity(i);
            }
        });
    }

}
