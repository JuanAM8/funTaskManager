package com.example.testingtfg.minigames.ballMinigame;

import android.graphics.Canvas;

import com.example.testingtfg.R;
import com.example.testingtfg.minigames.MinigamesEngine;
import com.example.testingtfg.minigames.GameObject;

import java.util.ArrayList;
import java.util.List;

/*Clase que gestiona el funcionamiento específico del minijuego 2*/
public class BallGameManager extends GameObject {

    //region Parámetros
    private static final int TIME_BETWEEN_NEW_BALL = 1000;
    private static final int NUM_BALLS = 25;
    public static final int MAX_LIVES = 3;
    public static boolean isBallReady;
    private List<Ball> ballPool = new ArrayList<Ball>();
    private int drawableBall;
    private int currentTime;
    private int ballsSpawned;
    public int ballsFailed;
    private MinigamesEngine gameEngine;
    //endregion

    public BallGameManager(MinigamesEngine gameEngine){
        this.gameEngine = gameEngine;
        drawableBall = R.drawable.ball_sprite;
        isBallReady = true;
        ballsFailed = 0;
        for (int i = 0; i < NUM_BALLS; i++){
            ballPool.add(new Ball(this, gameEngine, drawableBall));
        }
    }

    @Override
    public void start(){
        currentTime = 0;
        ballsSpawned = 0;
    }

    @Override
    public void update(long elapsedMillis, MinigamesEngine gameEngine){
        currentTime += elapsedMillis;
        long timeForSpawn = ballsSpawned*TIME_BETWEEN_NEW_BALL;
        if (currentTime > timeForSpawn && isBallReady){
            Ball newBall = ballPool.remove(0);
            newBall.init(gameEngine, ballsSpawned);
            gameEngine.addGameObject(newBall);
            isBallReady = false;
            ballsSpawned++;
            return;
        }
    }

    @Override
    public void draw(Canvas canvas){}

    public void returnToPool(Ball ball){
        ballPool.add(ball);
    }

}
