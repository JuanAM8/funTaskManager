package com.example.testingtfg.minigames.mazeMinigame;

import com.example.testingtfg.minigames.MinigamesEngine;
import com.example.testingtfg.minigames.VisualGameObject;
import com.example.testingtfg.minigames.SpriteGameObject;

/*Clase que gestiona los muros del minijuego 3*/
public class MazeTile extends SpriteGameObject {

    public boolean walkable;

    public MazeTile(MinigamesEngine gameEngine, boolean walkable, int drawable){
        super(gameEngine, drawable);
        this.walkable = walkable;
    }

    @Override
    public void start() {
    }

    public void init(MinigamesEngine gameEngine, int posX, int posY){
        this.posX = posX;
        this.posY = posY;
        rotation = 0;
    }

    @Override
    public void update(long elapsedMillis, MinigamesEngine gameEngine) {
    }

    @Override
    public void onCollision(MinigamesEngine gameEngine, VisualGameObject other){
    }

}
