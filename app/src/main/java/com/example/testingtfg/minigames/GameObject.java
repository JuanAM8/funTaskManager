package com.example.testingtfg.minigames;

import android.graphics.Canvas;
/*Clase abstracta de la que heredan todos los objetos del juego*/
public abstract class GameObject {

    public abstract void start();

    public abstract void update(long elapsedMillis, MinigamesEngine gameEngine);

    public abstract void draw(Canvas canvas);

    public final Runnable addRunnable = new Runnable() {
        @Override
        public void run() {
            addToGameThread();
        }
    };

    public void addToGameThread(){
    }

    public final Runnable removeRunnable = new Runnable() {
        @Override
        public void run() {
            removeFromGameThread();
        }
    };

    public void removeFromGameThread(){
    }
}
