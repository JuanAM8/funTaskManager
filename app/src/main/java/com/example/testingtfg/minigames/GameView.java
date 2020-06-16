package com.example.testingtfg.minigames;

import android.content.Context;

import java.util.List;

/*Interfaz de la pantalla de juego*/
public interface GameView {
    void draw();

    void setGameObjects(List<GameObject> gameObjects);

    int getWidth();

    int getHeight();

    int getPaddingLeft();

    int getPaddingRight();

    int getPaddingTop();

    int getPaddingBottom();

    Context getContext();
}
