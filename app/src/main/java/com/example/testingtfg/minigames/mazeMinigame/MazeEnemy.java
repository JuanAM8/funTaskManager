package com.example.testingtfg.minigames.mazeMinigame;

import com.example.testingtfg.minigames.MinigamesEngine;
import com.example.testingtfg.minigames.VisualGameObject;
import com.example.testingtfg.minigames.SpriteGameObject;

/*Clase que gestiona a los enemigos del minijuego 3*/
public class MazeEnemy extends SpriteGameObject {

    //region Parámetros
    protected final MazeGameManager gameManager;
    protected double speed;
    protected double speedX;
    protected double speedY;
    protected boolean horizontal;
    protected boolean left;
    private final float STEP_UNIT = 0.05f;
    //endregion

    //Constructor
    public MazeEnemy(MazeGameManager gameManager, MinigamesEngine gameEngine, int drawable,
                     boolean horizontal, boolean left){
        super(gameEngine, drawable);
        this.gameManager = gameManager;
        this.speed = 500d * pixelFactor / 1000d;
        this.horizontal = horizontal;
        this.left = left;
    }

    //Inicialización de la posición
    public void init(MinigamesEngine gameEngine, int posX, int posY){
        speedX = speed;
        speedY = speed;
        this.posX = posX + width/10;
        this.posY = posY + height/10;
        rotation = 0;
    }

    @Override
    public void start(){}

    //Actualización de la posición
    public void update(long elapsedMillis, MinigamesEngine gameEngine){
        updatePosition();
    }

    private void updatePosition(){
        if (horizontal && left){
            posX -= STEP_UNIT;
        } else if (horizontal && !left){
            posX += STEP_UNIT;
        } else if (!horizontal && left){
            posY -= STEP_UNIT;
        } else if (!horizontal && !left){
            posY += STEP_UNIT;
        }
    }

    private void reverseDirection(){
        left = !left;
    }

    //Elimina al enemigo al chocar con el personaje
    public void remove(MinigamesEngine gameEngine){
        gameEngine.removeGameObject(this);
        gameManager.returnToPool(this);
    }

    //Cuando choca con un muro, se da la vuelta
    @Override
    public void onCollision(MinigamesEngine gameEngine, VisualGameObject other){
        if (other instanceof MazeTile){
            reverseDirection();
        }
    }
}
