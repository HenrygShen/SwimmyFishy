package com.henryandlincoln.swimmyfishy;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class GameActivity extends Activity {

    private static final String PREFS_NAME = "Settings";
    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        /* Remove status,title bar and lock screen to portrait */
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        /* Load the character choice into the Game object, then pass into the game view to use */
        SharedPreferences settings = this.getApplicationContext().getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        int characterType = settings.getInt("character",R.drawable.catfish);

        /* Create game object to pass into the game view, which will load the settings */
        Game game = new Game(this);
        game.setCharacterType(characterType);

        gameView = new GameView(this,game);
        setContentView(gameView);

    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.stopThread();
    }

    @Override
    protected void onResume(){
        super.onResume();

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        gameView.stopThread();
    }


    public void gameOver(){

        Intent i = new Intent(GameActivity.this,GameOverActivity.class);
        startActivity(i);
    }

}
