package com.example.testingtfg.minigames;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.example.testingtfg.MainActivity;
import com.example.testingtfg.R;
import com.example.testingtfg.minigames.ballMinigame.BallGameOverFragment;
import com.example.testingtfg.minigames.mazeMinigame.MazeGameOverFragment;
import com.example.testingtfg.minigames.shooterMinigame.ShooterGameOverFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*Clase que gestiona el motor genérico para todos los minijuegos*/
public class MinigamesEngine {

    //region Listas
    private List<GameObject> gameObjects = new ArrayList<GameObject>();
    private List<GameObject> objectsToAdd = new ArrayList<GameObject>();
    private List<GameObject> objectsToRemove = new ArrayList<GameObject>();
    private List<Collision> detectedCollisions = new ArrayList<Collision>();
    private CollisionManager collisionManager = new CollisionManager();
    //endregion
    //region Threads, Control y Managers
    private UpdateThread updateThread;
    private DrawThread drawThread;
    public Controller controller;
    private SoundsManager soundsManager;
    public UIManager uiManager;
    public Random random = new Random();
    //endregion

    //region Parámetros Visuales
    private final GameView gameView;
    public int width;
    public int height;
    public double pixelFactor;
    private Activity mainActivity;
    //endregion

    //Constructor
    public MinigamesEngine(Activity activity, GameView view, int gameIndex){
        mainActivity = activity;
        gameView = view;
        gameView.setGameObjects(this.gameObjects);
        CollisionManager.init();
        width = gameView.getWidth() - gameView.getPaddingRight() - gameView.getPaddingLeft();
        height = gameView.getHeight() - gameView.getPaddingBottom() - gameView.getPaddingTop();
        pixelFactor = height / 400d;
        uiManager = new UIManager(this, gameIndex);
    }

    //Inicio del juego
    public void startGame(int gameIndex){
        stopGame(true, false, gameIndex);
        int numGO = gameObjects.size();
        for (int i = 0; i < numGO; i++){gameObjects.get(i).start();}

        updateThread = new UpdateThread(this);
        updateThread.start();
        drawThread = new DrawThread(this);
        drawThread.start();
    }

    //Detención del motor
    public void stopGame(boolean activeBefore, boolean victory, int gameIndex){
        if (updateThread != null){updateThread.stopGame();}
        if (drawThread != null){drawThread.stopGame();}
        if (!activeBefore){
            finishGame(victory, uiManager.currentScore, gameIndex);
        }
    }

    //Finalización de partida
    private void finishGame(boolean victory, int score, int gameIndex){
        Bundle bundle = new Bundle();
        if(victory) {bundle.putSerializable("victory", true);}
        else {bundle.putSerializable("victory", false);}
        bundle.putInt("score", score);
        switch(gameIndex){
            case 0:
                ((MainActivity)mainActivity).goToFragment(new ShooterGameOverFragment(),
                        R.id.game_screen, bundle);
                break;
            case 1:
                ((MainActivity)mainActivity).goToFragment(new BallGameOverFragment(),
                        R.id.game_screen, bundle);
                break;
            case 2:
                ((MainActivity)mainActivity).goToFragment(new MazeGameOverFragment(),
                        R.id.game_screen, bundle);
                break;
            default:
                ((MainActivity)mainActivity).toastMessage("ERROR: Should never happen");
                break;
        }

    }

    //Pausa
    public void pauseGame(){
        if (updateThread != null){updateThread.pauseGame();}
        if (drawThread != null){drawThread.pauseGame();}
    }

    //Reanudación
    public void resumeGame(){
        if (updateThread != null){updateThread.resumeGame();}
        if (drawThread != null){drawThread.resumeGame();}
    }

    //Añadir un nuevo objeto de juego al motor
    public void addGameObject(GameObject gameObject){
        if (isRunning()){objectsToAdd.add(gameObject);}
        else {addGameObjectNow(gameObject);}
        mainActivity.runOnUiThread(gameObject.addRunnable);
    }

    //Elimina un objeto de juego del motor
    public void removeGameObject(GameObject gameObject){
        objectsToRemove.add(gameObject);
        mainActivity.runOnUiThread(gameObject.removeRunnable);
    }

    //Actualización en cada frame
    public void update(long elapsedTime){
        int numGO = gameObjects.size();

        for (int i = 0; i < numGO; i++){
            GameObject go = gameObjects.get(i);
            go.update(elapsedTime, this);
            if (go instanceof VisualGameObject){
                ((VisualGameObject)go).onPostUpdate(this);
            }
        }

        checkCollisions();

        synchronized (gameObjects){
            while (!objectsToRemove.isEmpty()){
                GameObject objectToRemove = objectsToRemove.remove(0);
                gameObjects.remove(objectToRemove);
                if (objectToRemove instanceof VisualGameObject){
                    collisionManager.removeGameObject((VisualGameObject) objectToRemove);
                }
            }

            while(!objectsToAdd.isEmpty()){
                GameObject gameObject = objectsToAdd.remove(0);
                addGameObjectNow(gameObject);
            }
        }
    }

    //Dibujado
    public void draw(){
        gameView.draw();
    }

    //Control de colisiones
    private void checkCollisions(){
        while (!detectedCollisions.isEmpty()) {Collision.release(detectedCollisions.remove(0));}
        collisionManager.checkCollisions(this, detectedCollisions);
    }

    //Añade un objeto a la lista
    private void addGameObjectNow(GameObject obj){
        gameObjects.add(obj);
        if (obj instanceof VisualGameObject) {
            VisualGameObject sgo = (VisualGameObject) obj;
            collisionManager.addGameObject(sgo);
        }
    }

    //Evento de sonido
    public void onSoundEvent(SoundEvent event){
        soundsManager.playSound(event);
    }

    //region getters y setters
    public Context getContext(){
        return gameView.getContext();
    }

    public boolean isRunning(){
        return updateThread != null && updateThread.isGameRunning();
    }

    public boolean isPaused(){
        return updateThread != null && updateThread.isGamePaused();
    }

    public void setController(Controller controller){this.controller = controller;}
    public void setSoundsManager(SoundsManager sm){
        soundsManager = sm;}
    //endregion
}
