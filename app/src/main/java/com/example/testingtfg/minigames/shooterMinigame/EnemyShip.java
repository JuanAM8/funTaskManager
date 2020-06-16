package com.example.testingtfg.minigames.shooterMinigame;

import com.example.testingtfg.R;
import com.example.testingtfg.minigames.MinigamesEngine;
import com.example.testingtfg.minigames.SoundEvent;

import java.util.ArrayList;
import java.util.List;

/*Clase que gestiona el comportamiento de las naves enemigas*/
public class EnemyShip extends Enemy{

    //region Parámetros
    private static final int INIT_BULLET_POOL = 6;
    private static final long TIME_BETWEEN_BULLETS = 900;
    List<EnemyBullet> bullets = new ArrayList<EnemyBullet>();
    private long timeSinceLastFire;
    private int drawableEnemyBullet;
    //endregion

    //Constructor
    public EnemyShip(ShooterGameManager gameManager, MinigamesEngine gameEngine, int drawable){
        super(gameManager, gameEngine, drawable);
        drawableEnemyBullet = R.drawable.bullet_green_down;
        this.speed = 500d * pixelFactor / 1000d;
    }

    //Inicialización de la posición, velocidad y de las balas
    @Override
    public void init(MinigamesEngine gameEngine){
        double angle = gameEngine.random.nextDouble()*Math.PI/3d-Math.PI/6d;
        speedX = speed * Math.sin(angle);
        speedY = speed * Math.cos(angle);
        posX = gameEngine.random.nextInt(gameEngine.width / 2) + gameEngine.width/4;
        posY = -height;
        rotSpeed = 0;
        rotation = 0;
        initBulletPool(gameEngine);
    }

    //Añade una cantidad determinada de balas a la recámara
    private void initBulletPool(MinigamesEngine gameEngine){
        for (int i = 0; i < INIT_BULLET_POOL; i++)
            bullets.add(new EnemyBullet(gameEngine, drawableEnemyBullet));
    }

    //Reinicia la recámara de balas
    private void resetBulletPool(){
        bullets = new ArrayList<EnemyBullet>();
    }

    //Recoge una bala de la recámara
    private EnemyBullet getBullet() {
        if (bullets.isEmpty()) {return null;}
        return bullets.remove(0);
    }

    void releaseBullet(EnemyBullet bullet) {
        bullets.add(bullet);
    }

    //Comprueba si está disparando y actúa en consecuencia
    private void checkFiring(long elapsedMillis, MinigamesEngine gameEngine) {
        if (timeSinceLastFire > TIME_BETWEEN_BULLETS) {
            EnemyBullet bullet = getBullet();
            if (bullet == null) {return;}
            bullet.initEnemyBullet(gameEngine, this, posX + width/2, posY + height);
            gameEngine.addGameObject(bullet);
            timeSinceLastFire = 0;
            gameEngine.onSoundEvent(SoundEvent.Shoot);
        }
        else {timeSinceLastFire += elapsedMillis;}
    }

    @Override
    public void start(){}

    //Actualiza la posición, comprueba si ha salido del escenario y si debe disparar
    @Override
    public void update(long elapsedMillis, MinigamesEngine gameEngine){
        posY += speedY * elapsedMillis * 0.25;
        if (posY > gameEngine.height) {
            gameEngine.removeGameObject(this);
            gameManager.returnToPool(this);
            resetBulletPool();
            initBulletPool(gameEngine);
        }
        checkFiring(elapsedMillis, gameEngine);
    }
}
