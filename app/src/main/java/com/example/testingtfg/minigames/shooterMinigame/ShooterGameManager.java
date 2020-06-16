package com.example.testingtfg.minigames.shooterMinigame;

import android.graphics.Canvas;

import com.example.testingtfg.R;
import com.example.testingtfg.minigames.MinigamesEngine;
import com.example.testingtfg.minigames.GameObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*Clase que gestiona el comportamiento exclusivo del minijuego 1*/
public class ShooterGameManager extends GameObject {

    //region Constantes
    private static final int TIME_BETWEEN_ENEMIES = 1000;
    private static final int NUM_ENEMIES = 25;
    //endregion

    //region Parámetros
    private long currentTime;
    private List<Enemy> enemyPool = new ArrayList<Enemy>();
    private int enemiesSpawned;
    private int drawableAsteroid;
    private int drawableEnemyShip;
    //endregion

    //Constructor
    public ShooterGameManager(MinigamesEngine gameEngine){
        drawableAsteroid = R.drawable.sprite_mg1_asteroid;
        drawableEnemyShip = R.drawable.sprite_mg1_enemy;

        Random rng = new Random();
        int rngIndex;

        //Inicializa los enemigos
        for (int i = 0; i < NUM_ENEMIES; i++){
            rngIndex = rng.nextInt(2);
            if (rngIndex == 1){
                enemyPool.add(new Meteor(this, gameEngine, drawableAsteroid));
            } else {
                enemyPool.add(new EnemyShip(this, gameEngine, drawableEnemyShip));
            }
        }
    }

    @Override
    public void start(){
        currentTime = 0;
        enemiesSpawned = 0;
    }

    @Override
    public void update(long elapsedMillis, MinigamesEngine gameEngine){
        currentTime += elapsedMillis;

        //Genera enemigo nuevo
        long timeForSpawn = enemiesSpawned*TIME_BETWEEN_ENEMIES;
        if (currentTime > timeForSpawn){
            Enemy enemy = enemyPool.remove(0);
            enemy.init(gameEngine);
            gameEngine.addGameObject(enemy);
            enemiesSpawned++;
            return;
        }
    }

    @Override
    public void draw(Canvas canvas){}

    public void returnToPool(Enemy enemy){
        enemyPool.add(enemy);
    }

}
