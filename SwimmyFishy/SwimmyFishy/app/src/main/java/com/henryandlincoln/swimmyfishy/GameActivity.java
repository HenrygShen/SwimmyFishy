package com.henryandlincoln.swimmyfishy;

import android.app.Activity;
import android.os.Bundle;

public class GameActivity extends Activity {

    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        gameView = new GameView(this.getApplicationContext());
        setContentView(gameView);
    }
}
