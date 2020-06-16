package com.example.testingtfg.minigames.shooterMinigame;

import com.example.testingtfg.minigames.MinigamesEngine;

/*Clase encarga de gestionar los enemigos de tipo meteorito*/
public class Meteor extends Enemy {

    //Constructor
    public Meteor(ShooterGameManager gameManager, MinigamesEngine gameEngine, int drawable){
        super(gameManager, gameEngine, drawable);
        speed = 200d * pixelFactor / 1000d;
    }

    //Inicialización de posición, velocidad y rotación
    @Override
    public void init(MinigamesEngine gameEngine){
        double angle = gameEngine.random.nextDouble()*Math.PI/3d-Math.PI/6d;
        speedX = speed * Math.sin(angle);
        speedY = speed * Math.cos(angle);
        posX = gameEngine.random.nextInt(gameEngine.width / 2) + gameEngine.width/4;
        posY = -height;
        rotSpeed = angle * (180d / Math.PI) / 250d;
        rotation = gameEngine.random.nextInt(360);
    }

    @Override
    public void start(){}

    //Actualización de la posición
    @Override
    public void update(long elapsedMillis, MinigamesEngine gameEngine){
        posX += speedX * elapsedMillis;
        posY += speedY * elapsedMillis;
        rotation += rotSpeed * elapsedMillis;
        if (rotation > 360) {rotation = 0;}
        else if (rotation < 0) {rotation = 360;}
        if (posY > gameEngine.height){
            gameEngine.removeGameObject(this);
            gameManager.returnToPool(this);
        }
    }
}
