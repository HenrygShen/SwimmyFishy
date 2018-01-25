package com.henryandlincoln.swimmyfishy;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class GameActivity extends Activity {

    private GameView gameView;
    public static final String PREFS_NAME = "Settings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        Game game = new Game();
        SharedPreferences settings = this.getApplicationContext().getSharedPreferences(PREFS_NAME,MODE_PRIVATE);

        int bgmVolume = settings.getInt("bgmVolume",0);
        int sfxVolume = settings.getInt("sfxVolume",0);
        int character = settings.getInt("character",R.drawable.catfish);
        game.setCharacter(character);
        game.setVolume(bgmVolume,sfxVolume);

        gameView = new GameView(this,game);
        setContentView(gameView);
    }

    @Override
    protected void onPause(){
        super.onPause();
        gameView.stopThread();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        gameView.stopThread();
    }

    @Override
    public void onBackPressed(){
        finish();
    }
}
