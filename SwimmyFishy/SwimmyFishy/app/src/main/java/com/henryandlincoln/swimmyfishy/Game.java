package com.henryandlincoln.swimmyfishy;

public class Game {

    private int character;
    private int bgmVolume;
    private int sfxVolume;

    public Game(){

    }

    public void setCharacter(int character) {
        this.character = character;
    }

    public void setVolume(int bgmVolume,int sfxVolume){
        this.bgmVolume = bgmVolume;
        this.sfxVolume = sfxVolume;
    }

    public int getCharacter(){
        return this.character;
    }
}
