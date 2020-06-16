package com.example.testingtfg.minigames.ballMinigame;

import com.example.testingtfg.minigames.MinigamesEngine;
import com.example.testingtfg.minigames.SoundEvent;
import com.example.testingtfg.minigames.VisualGameObject;
import com.example.testingtfg.minigames.SpriteGameObject;

/*Clase que gestiona al vaso del minijuego2*/
public class BallCharacter extends SpriteGameObject {

    //region Parámetros
    private static final int BALLS_TO_WIN = 15;
    private int maxX;
    private int maxY;
    private double speed;
    private double startingSpeed;
    private double speedFactor;
    private int ballsCatched;
    //endregion

    //Constructor
    public BallCharacter(MinigamesEngine gameEngine, int drawableSprite){
        super(gameEngine, drawableSprite);
        maxX = gameEngine.width - width;
        maxY = gameEngine.height - height;
        startingSpeed = 200d;
        ballsCatched = 0;
        speed = startingSpeed;
        speedFactor = pixelFactor * speed / 1000d;
    }

    //Inicia la posición del vaso
    @Override
    public void start() {posX = maxX / 2; posY = 6*maxY / 8;}

    //Actualiza la posición del vaso en función del control
    @Override
    public void update(long elapsedMillis, MinigamesEngine gameEngine) {
        updatePosition(elapsedMillis, (BallController)gameEngine.controller);
    }

    private void updatePosition(long elapsedMillis, BallController controller) {
        posX += speedFactor * controller.hFactor * elapsedMillis;
        if (posX < 0) {posX = 0;}
        if (posX > maxX) {posX = maxX;}
    }

    //Al colisionar con una bola, elimina la bola e incrementa la puntuación
    @Override
    public void onCollision(MinigamesEngine gameEngine, VisualGameObject other){
        if (other instanceof Ball){
            Ball ball = (Ball) other;
            ball.remove(gameEngine);
            ballsCatched++;
            gameEngine.uiManager.increaseScore(ball);
            gameEngine.onSoundEvent(SoundEvent.Glass);
            if (ballsCatched >= BALLS_TO_WIN){
                gameEngine.stopGame(false, true, 1);
            }
        }
    }
}
