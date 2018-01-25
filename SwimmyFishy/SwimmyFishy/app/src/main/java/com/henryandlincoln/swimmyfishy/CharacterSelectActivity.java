package com.henryandlincoln.swimmyfishy;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class CharacterSelectActivity extends Activity {

    private int windowHeight;
    public static final String PREFS_NAME = "Settings";
    private int windowWidth;
    private SharedPreferences settings;
    private boolean dogUnlocked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        /* Remove status,title bar and lock screen to portrait */
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_character_select);

        /* Create settings to load character choice into */
        settings = this.getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        dogUnlocked = settings.getBoolean("dogUnlocked",false);

        /* The images for the buttons are dependent on the window size, use these variables to scale the images */
        windowHeight = this.getWindow().getDecorView().getHeight();
        windowWidth = this.getWindow().getDecorView().getWidth();

        configureCatButton();
        configureDogButton();
    }

    public void configureCatButton(){

        Button catBtn = (Button) findViewById(R.id.catButton);
        catBtn.setHeight((int)(windowHeight*0.3));
        catBtn.setWidth((int)(windowWidth*0.6));

        catBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                settings.edit().putInt("character",R.drawable.catfish).apply();
            }
        });
    }

    public void configureDogButton(){

        Button dogBtn = (Button) findViewById(R.id.dogButton);
        dogBtn.setHeight((int)(windowHeight*0.3));
        dogBtn.setWidth((int)(windowWidth*0.6));
        final int dog;
        Drawable buttonBG;
        if (dogUnlocked){
            dog = R.drawable.dogfish_unlocked;
            buttonBG = getResources().getDrawable(dog);
        }
        else {
            dog = R.drawable.dogfish_locked;
            buttonBG = getResources().getDrawable(dog);
        }
        dogBtn.setBackground(buttonBG);
        dogBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (dogUnlocked) {
                    settings.edit().putInt("character", R.drawable.dogfish).apply();
                }
                else {
                    settings.edit().putInt("character",R.drawable.catfish).apply();
                }
            }
        });
    }
}
