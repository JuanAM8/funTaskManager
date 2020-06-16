package com.example.testingtfg.minigames;

import android.graphics.Rect;

/*Objeto propio del juego que se mostrará en pantalla*/
public abstract class VisualGameObject extends GameObject {

    protected double posX;
    protected double posY;
    protected int width;
    protected int height;
    public double radius;
    public Rect mBoundingRect = new Rect(-1, -1, -1, -1);

    public abstract void onCollision(MinigamesEngine gameEngine, VisualGameObject other);

    public void onPostUpdate(MinigamesEngine gameEngine) {
        mBoundingRect.set(
                (int) posX,
                (int) posY,
                (int) posX + width,
                (int) posY + height);
    }

    public boolean checkCollision(VisualGameObject other) {
        double distanceX = (posX + width /2) - (other.posX + other.width /2);
        double distanceY = (posY + height /2) - (other.posY + other.height /2);
        double squareDistance = distanceX*distanceX + distanceY*distanceY;
        double collisionDistance = (radius + other.radius);
        return squareDistance <= collisionDistance*collisionDistance;
    }
}
