package com.example.testingtfg.minigames.shooterMinigame;

import android.graphics.drawable.BitmapDrawable;

import com.example.testingtfg.minigames.MinigamesEngine;
import com.example.testingtfg.minigames.VisualGameObject;
import com.example.testingtfg.minigames.SpriteGameObject;

/*Clase abstracta que implementa parámetros y comportamientos comunes de las balas*/
public abstract class Bullet extends SpriteGameObject {

    //region Parámetros
    public enum Orientation {UP, DOWN, LEFT, RIGHT};
    protected double speedFactor;
    public Orientation orientation = Orientation.UP;
    //endregion

    //Constructor
    public Bullet(MinigamesEngine gameEngine, int drawable){
        super(gameEngine, drawable);
        speedFactor = gameEngine.pixelFactor * -300d / 1000d;
        bitmapImg = ((BitmapDrawable)gameEngine.getContext().
                getResources().getDrawable(drawable, null)).getBitmap();
    }

    @Override
    public void start(){}

    @Override
    public abstract void update(long elapsedMilis, MinigamesEngine gameEngine);

    //Inicialización de la posición
    protected void init(double initX, double initY){
        posX = initX - width/2;
        posY = initY - height/2;
    }

    protected abstract void remove(MinigamesEngine gameEngine);

    @Override
    public abstract void onCollision(MinigamesEngine gameEngine, VisualGameObject other);
}
