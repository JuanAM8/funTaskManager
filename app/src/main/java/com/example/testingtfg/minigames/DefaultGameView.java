package com.example.testingtfg.minigames;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/*Clase que implementa la interfaz de la vista de juego*/
public class DefaultGameView extends View implements GameView {

    private List<GameObject> gameObjects;

    public DefaultGameView(Context context){
        super(context);
        this.gameObjects = new ArrayList<GameObject>();
    }

    public DefaultGameView(Context context, AttributeSet attrs){
        super(context, attrs);
        this.gameObjects = new ArrayList<GameObject>();
    }

    public DefaultGameView(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
        this.gameObjects = new ArrayList<GameObject>();
    }

    @Override
    public void draw(){
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        synchronized (gameObjects){
            int numGO = gameObjects.size();
            for (int i = 0; i < numGO; i++){
                gameObjects.get(i).draw(canvas);
            }
        }
    }

    public void setGameObjects(List<GameObject> gameObjects){
        this.gameObjects = gameObjects;
    }

}
