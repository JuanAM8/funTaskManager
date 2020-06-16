package com.example.testingtfg.minigames.mazeMinigame;

import com.example.testingtfg.minigames.MinigamesEngine;
import com.example.testingtfg.minigames.VisualGameObject;
import com.example.testingtfg.minigames.SpriteGameObject;

/*Clase que gestiona los objetos a recoger en el minijuego 3*/
public class MazeItem extends SpriteGameObject {

    public MazeItem(MinigamesEngine gameEngine, int drawable){
        super(gameEngine, drawable);
    }

    @Override
    public void start() {
    }

    @Override
    public void update(long elapsedMillis, MinigamesEngine gameEngine) {
    }


    public void init(int posX, int posY){
        this.posX = posX + width/4;
        this.posY = posY + height/4;
    }

    private void moveToNextTile(){

    }

    public void remove(MinigamesEngine gameEngine){
        gameEngine.removeGameObject(this);
    }

    @Override
    public void onCollision(MinigamesEngine gameEngine, VisualGameObject other){
    }

}
