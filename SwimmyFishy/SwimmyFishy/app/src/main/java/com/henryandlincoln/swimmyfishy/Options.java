package com.henryandlincoln.swimmyfishy;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;



public class Options extends Activity {

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

        DisplayMetrics dm  = new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout( width,(int) (height*0.7));


        Button button = (Button) findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
            }
        });


    }
}
