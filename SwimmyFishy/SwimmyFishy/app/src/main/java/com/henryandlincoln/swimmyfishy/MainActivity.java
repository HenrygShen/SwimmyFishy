package com.henryandlincoln.swimmyfishy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;


public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /* Hides the status bar */
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        /* Read setting.txt from assets folder and load these settings */
        //TODO

        configurePlayButton();
        configureOptionsButton();
    }

    protected void configurePlayButton(){
        Button playBtn = (Button) findViewById(R.id.play);

        playBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i  = new Intent(MainActivity.this,GameActivity.class);
                startActivity(i);
            }
        });
    }

    protected void configureOptionsButton(){
        Button setBtn = (Button) findViewById(R.id.options);

        setBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i =  new Intent(MainActivity.this,Options.class);
                startActivity(i);
            }
        });
    }

}
