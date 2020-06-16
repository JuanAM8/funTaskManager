package com.example.testingtfg.minigames;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.testingtfg.minigames.mazeMinigame.MazeItem;
import com.example.testingtfg.minigames.shooterMinigame.Meteor;
import com.example.testingtfg.minigames.shooterMinigame.EnemyShip;

/*Clase que gestiona la interfaz de los minijuegos*/
public class UIManager extends GameObject {

    //region Constantes y Parámetros
    private static final int BALLS_TO_WIN = 15;
    public static final int MAX_SCORE = 20;
    private final int STARTING_SCORE = 0;
    private int gameIndex;
    private Paint scorePaint;
    private Paint lifePaint;
    private final float textWidth;
    private final float textHeight;
    public int currentScore;
    public int currentLife;
    private String scoreText = "";
    private String lifeText = "";
    //endregion

    //Constructor
    public UIManager(MinigamesEngine gameEngine, int gameIndex) {
        this.gameIndex = gameIndex;
        textHeight = (float) (25 * gameEngine.pixelFactor);
        textWidth = (float) (50 * gameEngine.pixelFactor);

        scorePaint = new Paint();
        scorePaint.setTextAlign(Paint.Align.CENTER);
        scorePaint.setTextSize(textHeight / 2);

        lifePaint = new Paint();
        lifePaint.setTextAlign(Paint.Align.CENTER);
        lifePaint.setTextSize(textHeight / 2);
    }

    //Sube la puntuacion en función del enemigo destruido/objeto recogido
    public void increaseScore(SpriteGameObject enemyDestroyed){
        if (gameIndex == 0){
            if (enemyDestroyed instanceof Meteor){ currentScore++;}
            else if (enemyDestroyed instanceof EnemyShip){ currentScore += 2;}
        } else if (gameIndex == 1){
            currentScore += 1;
        } else if (gameIndex == 2){
            if (enemyDestroyed instanceof MazeItem){ currentScore++;}
        }
        if (currentScore > MAX_SCORE){ currentScore = MAX_SCORE;}
    }

    //Baja la puntuación
    public void decreaseScore(){
        if (gameIndex == 0){
            currentScore--;
            if (currentScore < 0){ currentScore = 0;}
        } else if (gameIndex == 1){
            currentLife--;
        }
    }

    @Override
    public void start() {
        currentScore = STARTING_SCORE;
        if (gameIndex == 1){currentLife = 3;}
    }

    @Override
    public void update(long elapsedMillis, MinigamesEngine gameEngine) {
        if (gameIndex == 0){
            scoreText = "Puntos: " + currentScore;
            lifeText = "Vidas: " + currentLife;
        } else if (gameIndex == 1){
            scoreText = "  Bolas recogidas: " + currentScore + "/" +  BALLS_TO_WIN;
            lifeText = "Intentos restantes: " + currentLife;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        lifePaint.setColor(Color.WHITE);
        scorePaint.setColor(Color.WHITE);
        if (gameIndex == 0){
            canvas.drawText(lifeText, (int)(canvas.getWidth()/4 - textWidth / 2),
                    (int) (canvas.getHeight()/8 - (textHeight / 2)), lifePaint);
            canvas.drawText(scoreText, canvas.getWidth()/2 - textWidth / 2, canvas.getHeight()/8, scorePaint);
        } else if (gameIndex == 1){
            canvas.drawText(lifeText, (int)(canvas.getWidth()/2 - textWidth),
                    (int) (canvas.getHeight()/8 - (textHeight / 2)), lifePaint);
            canvas.drawText(scoreText, canvas.getWidth()/2 - textWidth, canvas.getHeight()/8, scorePaint);
        }
    }

}
