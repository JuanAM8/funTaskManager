package com.example.testingtfg.minigames.ballMinigame;

import com.example.testingtfg.minigames.MinigamesEngine;
import com.example.testingtfg.minigames.VisualGameObject;
import com.example.testingtfg.minigames.SpriteGameObject;

/*Clase que gestiona el comportamiento de la bola del minijuego 2*/
public class Ball extends SpriteGameObject {

    //region Parametros
    protected final BallGameManager gameManager;
    protected double speed;
    protected double speedY;
    protected double rotSpeed;
    //endregion

    //Constructor
    public Ball(BallGameManager gameManager, MinigamesEngine gameEngine, int drawable){
        super(gameEngine, drawable);
        this.gameManager = gameManager;
        this.speed = 700d * pixelFactor / 1000d;
    }

    //Inicializa la posición y velocidad de la bola
    public void init(MinigamesEngine gameEngine, int speedFactor){
        double angle = gameEngine.random.nextDouble()*Math.PI/3d-Math.PI/6d;
        posX = gameEngine.random.nextInt(gameEngine.width / 2) + gameEngine.width/4;
        speedY = speed * Math.cos(angle) + speedFactor*0.25;
        posY = -height;
        rotSpeed = 0;
        rotation = 0;
    }

    @Override
    public void start(){}

    //Elimina la bola y avisa al manager de lanzar otra
    public void remove(MinigamesEngine gameEngine){
        gameEngine.removeGameObject(this);
        gameManager.isBallReady = true;
        gameManager.returnToPool(this);
    }

    //Actualiza la posición de la bola y, si esta ha salido de la pantalla, vuelve al inicio
    @Override
    public void update(long elapsedMillis, MinigamesEngine gameEngine){
        posY += speedY * elapsedMillis * 0.25;
        if (posY > gameEngine.height) {
            gameEngine.removeGameObject(this);
            gameManager.isBallReady = true;
            gameManager.returnToPool(this);
            gameManager.ballsFailed++;
            gameEngine.uiManager.decreaseScore();
            if (gameManager.ballsFailed >= gameManager.MAX_LIVES){
                gameEngine.stopGame(false, false, 1);
            }
        }
    }

    @Override
    public void onCollision(MinigamesEngine gameEngine, VisualGameObject other){}
}
