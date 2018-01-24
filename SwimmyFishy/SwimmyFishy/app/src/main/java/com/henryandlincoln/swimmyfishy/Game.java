package com.henryandlincoln.swimmyfishy;


import android.content.SharedPreferences;

public class Game {

    private String character;
    private int bgmVolume;
    private int sfxVolume;

    public Game(){

    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public void setVolume(int bgmVolume,int sfxVolume){
        this.bgmVolume = bgmVolume;
        this.sfxVolume = sfxVolume;
    }

    public String getCharacter(){
        return this.character;
    }
}
