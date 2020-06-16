package com.example.testingtfg.minigames.shooterMinigame;

import com.example.testingtfg.minigames.MinigamesEngine;
import com.example.testingtfg.minigames.SoundEvent;
import com.example.testingtfg.minigames.VisualGameObject;

/*Clase que gestiona la bala disparada por el personaje controlable*/
public class FriendlyBullet extends Bullet {

    //region Parámetros
    private ShooterCharacter parent;
    private boolean horizontal = false;
    private boolean reversed = false;
    //endregion

    //Constructor
    public FriendlyBullet(MinigamesEngine gameEngine, int drawable){
        super(gameEngine, drawable);
    }

    @Override
    public void start(){}

    /*Actualización de la posición: Pese a que solo dispara hacia arriba, se permite la posibilidad
    de ampliar el código para que la bala se dispare en cualquier dirección*/
    @Override
    public void update(long elapsedMillis, MinigamesEngine gameEngine){
        if (!horizontal && !reversed){//Hacia arriba
            posY += speedFactor * elapsedMillis;
            if (posY < -gameEngine.height) {removeBullet(gameEngine);}
        } else if (!horizontal && reversed) {//Hacia abajo
            posY -= speedFactor * elapsedMillis;
            if (posY > gameEngine.height){removeBullet(gameEngine);}
        } else if (horizontal && !reversed) {//Hacia izquierda
            posX -= speedFactor * elapsedMillis;
            if (posX > gameEngine.width){removeBullet(gameEngine);}
        } else if (horizontal && reversed){//Hacia derecha
            posX += speedFactor * elapsedMillis;
            if (posX < 0){removeBullet(gameEngine);}
        }
    }

    //Destruye la bala y la devuelve a la recámara
    private void removeBullet(MinigamesEngine gameEngine){
        gameEngine.removeGameObject(this);
        parent.releaseBullet(gameEngine, this);
    }

    //Inicializa la bala
    public void initFriendlyBullet(ShooterCharacter parent, double initX, double initY){
        init(initX, initY);
        this.parent = parent;
    }

    @Override
    protected void remove(MinigamesEngine gameEngine){
        gameEngine.removeGameObject(this);
        parent.releaseBullet(gameEngine, this);
     }

     //Si choca con un enemigo, destruirá a ese enemigo y desatará los eventos correspondientes
     @Override
     public void onCollision(MinigamesEngine gameEngine, VisualGameObject other){
        if (other instanceof Enemy){
            remove(gameEngine);
            Enemy enemy = (Enemy) other;
            gameEngine.uiManager.increaseScore(enemy);
            if (gameEngine.uiManager.currentScore >= gameEngine.uiManager.MAX_SCORE) {
                gameEngine.stopGame(false, true, 0);
            }
            enemy.remove(gameEngine);
            gameEngine.onSoundEvent(SoundEvent.MeteorHit);
        }
     }




}
