package com.example.testingtfg.minigames.shooterMinigame;

import com.example.testingtfg.minigames.MinigamesEngine;
import com.example.testingtfg.minigames.VisualGameObject;

/*Clase que gestiona las balas que disparan los enemigos*/
public class EnemyBullet extends Bullet {

    private EnemyShip parent;

    public EnemyBullet(MinigamesEngine gameEngine, int drawable){
        super(gameEngine, drawable);
    }

    @Override
    public void start(){}

    @Override
    public void update(long elapsedMillis, MinigamesEngine gameEngine){
        posY -= speedFactor * elapsedMillis * 3;
        if (posY < height){
            gameEngine.removeGameObject(this);
            parent.releaseBullet(this);
        }
    }

    public void initEnemyBullet(MinigamesEngine gameEngine, EnemyShip parentEnemy, double initPosX, double initPosY) {
        init(initPosX, initPosY);
        parent = parentEnemy;
        orientation = Bullet.Orientation.DOWN;
    }

    @Override
    protected void remove(MinigamesEngine gameEngine) {
        gameEngine.removeGameObject(this);
        parent.releaseBullet(this);
    }

    @Override
    public void onCollision(MinigamesEngine gameEngine, VisualGameObject otherObject) {}
}
