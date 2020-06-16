package com.example.testingtfg.minigames.mazeMinigame;

import com.example.testingtfg.minigames.MinigamesEngine;
import com.example.testingtfg.minigames.SoundEvent;
import com.example.testingtfg.minigames.VisualGameObject;
import com.example.testingtfg.minigames.SpriteGameObject;

/*Clase que gestiona al personaje controlable del minijuego 3*/
public class MazeCharacter extends SpriteGameObject {

    //region Parámetros
    private double speed;
    private double speedFactor;
    private int life;
    private int remainingItems;
    private double previousX;
    private double previousY;
    private double startingX;
    private double startingY;
    private final float STEP_UNIT = 0.08f;
    //endregion

    //Constructor
    public MazeCharacter(MinigamesEngine gameEngine, int drawable, int remainingItems){
        super(gameEngine, drawable);
        life = 1;
        speed = 1d;
        speedFactor = pixelFactor * speed / 1000d;
        gameEngine.uiManager.currentLife = life;
        this.remainingItems = remainingItems;
    }

    @Override
    public void start() {
        posX = 0;
        posY = 0;
    }

    //Actualiza la posición en función del control
    @Override
    public void update(long elapsedMillis, MinigamesEngine gameEngine) {
        updatePosition((MazeController)gameEngine.controller);
    }

    private void updatePosition(MazeController controller) {
        previousX = posX;
        previousY = posY;
        if (controller.leftPressed){
            posX -= STEP_UNIT;
        } else if (controller.rightPressed){
            posX += STEP_UNIT;
        }else if (controller.upPressed){
            posY -= STEP_UNIT;
        }else if (controller.downPressed){
            posY += STEP_UNIT;
        }
    }

    //Inicializa la posición
    public void init(int posX, int posY){
        this.posX = posX + width/10;
        this.posY = posY + height/10;
        startingX = this.posX;
        startingY = this.posY;
    }

    //Vuelve a la posición anterior
    private void getToPreviousPosition(){
        posX = previousX;
        posY = previousY;
    }

    //Cuando choca:
    //Con un enemigo->Pierde
    //Con un muro->No avanza posición
    //Con un objeto->Obtiene puntos
    @Override
    public void onCollision(MinigamesEngine gameEngine, VisualGameObject other){
        if (other instanceof MazeEnemy){
            killPlayer(gameEngine);
            MazeEnemy enemy = (MazeEnemy) other;
            enemy.remove(gameEngine);
            gameEngine.onSoundEvent(SoundEvent.Defeat);
        } else if (other instanceof MazeTile){
            getToPreviousPosition();
        } else if (other instanceof MazeItem){
            MazeItem item = (MazeItem) other;
            item.remove(gameEngine);
            remainingItems--;
            gameEngine.uiManager.increaseScore(item);
            gameEngine.onSoundEvent(SoundEvent.Coin);
            if (remainingItems <= 0){
                gameEngine.stopGame(false, true, 2);
            }
        }
    }

    //Mata al personaje y, si procede, fin del juego
    private void killPlayer(MinigamesEngine gameEngine){
        life--;
        gameEngine.uiManager.currentLife = life;
        gameEngine.uiManager.decreaseScore();
        if (life == 0){
            gameEngine.removeGameObject(this);
            gameEngine.stopGame(false, false, 2);
        } else {
            posX = startingX; posY = startingY;
        }
    }
}
