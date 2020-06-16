package com.example.testingtfg.minigames.shooterMinigame;

import com.example.testingtfg.minigames.MinigamesEngine;
import com.example.testingtfg.minigames.VisualGameObject;
import com.example.testingtfg.minigames.SpriteGameObject;

/*Clase abstracta para los enemigos que implementa parámetros y métodos comunes*/
public abstract class Enemy extends SpriteGameObject {

    //region Parámetros
    protected final ShooterGameManager gameManager;
    protected double speed;
    protected double speedX;
    protected double speedY;
    protected double rotSpeed;
    //endregion

    //Constructor
    public Enemy(ShooterGameManager gameManager, MinigamesEngine gameEngine, int drawable){
        super(gameEngine, drawable);
        this.gameManager = gameManager;
    }

    public void init(MinigamesEngine gameEngine){}

    @Override
    public void start(){}

    public void remove(MinigamesEngine gameEngine){
        gameEngine.removeGameObject(this);
        gameManager.returnToPool(this);
    }

    @Override
    public abstract void update(long elapsedMillis, MinigamesEngine gameEngine);

    @Override
    public void onCollision(MinigamesEngine gameEngine, VisualGameObject other){}
}
