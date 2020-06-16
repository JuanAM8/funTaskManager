package com.example.testingtfg.minigames.mazeMinigame;

import android.graphics.Canvas;

import com.example.testingtfg.minigames.MinigamesEngine;
import com.example.testingtfg.minigames.GameObject;

/*Clase que gestiona los niveles del minijuego 3*/
public class MazeLevel extends GameObject {

    public int[][] map;

    public MazeLevel(int[][] map){
        this.map = map;
    }

    public void start() {
    }

    @Override
    public void draw(Canvas canvas){}

    @Override
    public void update(long elapsedMillis, MinigamesEngine gameEngine) {
    }

}
