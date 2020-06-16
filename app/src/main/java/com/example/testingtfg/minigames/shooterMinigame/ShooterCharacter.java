package com.example.testingtfg.minigames.shooterMinigame;

import com.example.testingtfg.R;
import com.example.testingtfg.minigames.MinigamesEngine;
import com.example.testingtfg.minigames.SoundEvent;
import com.example.testingtfg.minigames.VisualGameObject;
import com.example.testingtfg.minigames.SpriteGameObject;

import java.util.ArrayList;
import java.util.List;

/*Clase que gestiona el comportamiento de la nave*/
public class ShooterCharacter extends SpriteGameObject {

    //region Constantes
    private static final int INIT_BULLET_POOL = 12;
    private static final long TIME_BETWEEN_BULLETS = 250;
    //endregion

    //region Parámetros
    List<FriendlyBullet> bullets = new ArrayList<FriendlyBullet>();
    private long timeSinceLastFire;
    private int maxX;
    private int maxY;
    private double speed;
    private double startingSpeed;
    private double speedFactor;
    private int life;
    private int drawableFriendlyBullet;
    //endregion

    //Constructor
    public ShooterCharacter(MinigamesEngine gameEngine, int drawable){
        super(gameEngine, drawable);
        drawableFriendlyBullet = R.drawable.bullet_blue;
        maxX = gameEngine.width - width;
        maxY = gameEngine.height - height;
        startingSpeed = 100d;
        life = 3;
        speed = startingSpeed;
        speedFactor = pixelFactor * speed / 1000d;
        gameEngine.uiManager.currentLife = life;
        initBulletPool(gameEngine);
    }

    //Inicializa las balas de la recámara
    private void initBulletPool(MinigamesEngine ge) {
        for (int i=0; i < INIT_BULLET_POOL; i++) {
            bullets.add(new FriendlyBullet(ge, drawableFriendlyBullet));
        }
    }

    //Recoge una bala de la recámara
    private FriendlyBullet getBullet() {
        if (bullets.isEmpty()) {return null;}
        return bullets.remove(0);
    }

    void releaseBullet(MinigamesEngine gameEngine, FriendlyBullet bullet) {
        bullets.add(bullet);
    }

    @Override
    public void start() {posX = maxX / 2; posY = maxY / 2;}

    //Actualiza su posición en función al controlador y dispara si procede
    @Override
    public void update(long elapsedMillis, MinigamesEngine gameEngine) {
        updatePosition(elapsedMillis, (ShooterController)gameEngine.controller);
        checkFiring(elapsedMillis, gameEngine);
    }

    private void updatePosition(long elapsedMillis, ShooterController controller) {
        posX += speedFactor * controller.hFactor * elapsedMillis;
        if (posX < 0) {posX = 0;}
        if (posX > maxX) {posX = maxX;}
        posY += speedFactor * controller.vFactor * elapsedMillis;
        if (posY < 0) {posY = 0;}
        if (posY > maxY) {posY = maxY;}
    }

    private void checkFiring(long elapsedMillis, MinigamesEngine gameEngine) {
        if (((ShooterController)gameEngine.controller).isFiring && timeSinceLastFire > TIME_BETWEEN_BULLETS) {
            fire(gameEngine);
        }
        else {timeSinceLastFire += elapsedMillis;}
    }

    //Método de disparo
    private void fire(MinigamesEngine gameEngine){
        FriendlyBullet bullet = getBullet();
        if (bullet != null) {
            bullet.initFriendlyBullet(this, posX + width / 2, posY);
            gameEngine.addGameObject(bullet);
            gameEngine.onSoundEvent(SoundEvent.Shoot);
        }
        timeSinceLastFire = 0;
    }

    //Si choca con un enemigo, pierde puntos y una vida
    @Override
    public void onCollision(MinigamesEngine gameEngine, VisualGameObject other){
        if (other instanceof Enemy){
            killPlayer(gameEngine);
            Enemy enemy = (Enemy) other;
            enemy.remove(gameEngine);
            gameEngine.onSoundEvent(SoundEvent.MeteorHit);
        } else if (other instanceof EnemyBullet){
            killPlayer(gameEngine);
            EnemyBullet bullet = (EnemyBullet) other;
            bullet.remove(gameEngine);
            gameEngine.onSoundEvent(SoundEvent.MeteorHit);
        }
    }

    //Pierde una vida y, si procede, game over
    private void killPlayer(MinigamesEngine gameEngine){
        life--;
        gameEngine.uiManager.currentLife = life;
        speed = startingSpeed;
        gameEngine.uiManager.decreaseScore();
        if (life == 0){
            gameEngine.removeGameObject(this);
            gameEngine.stopGame(false, false, 0);
        } else {
            posX = maxX / 2; posY = maxY / 2;
        }
    }
}
