package com.example.testingtfg.minigames;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/*Objetos en movimiento de los minijuegos*/
public abstract class SpriteGameObject extends VisualGameObject {

    //region Parámetros
    protected double rotation;
    protected double pixelFactor;
    protected Bitmap bitmapImg;
    private final Matrix matrix = new Matrix();
    //endregion

    //Constructor
    protected SpriteGameObject(MinigamesEngine gameEngine, int drawableRes) {
        Resources r = gameEngine.getContext().getResources();
        Drawable spriteDrawable = r.getDrawable(drawableRes, null);
        this.pixelFactor = gameEngine.pixelFactor;
        this.height = (int) (spriteDrawable.getIntrinsicHeight() * this.pixelFactor);
        this.width = (int) (spriteDrawable.getIntrinsicWidth() * this.pixelFactor);
        this.bitmapImg = ((BitmapDrawable) spriteDrawable).getBitmap();
        radius = Math.max(height, width)/2;
    }

    //Dibujado
    @Override
    public void draw(Canvas canvas) {
        if (posX > canvas.getWidth()
                || posY > canvas.getHeight()
                || posX < - width
                || posY < - height) {
            return;
        }
        matrix.reset();
        matrix.postScale((float) pixelFactor, (float) pixelFactor);
        matrix.postTranslate((float) posX, (float) posY);
        matrix.postRotate((float) rotation, (float) (posX + width/2), (float) (posY + height/2));
        canvas.drawBitmap(bitmapImg, matrix, null);
    }
}
