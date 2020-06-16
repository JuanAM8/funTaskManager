package com.example.testingtfg.minigames;

/*Clase que gestiona la actualización del estado del juego (GameLoop) */
public class UpdateThread extends Thread {

    //region Parámetros
    private final MinigamesEngine gameEngine;
    private boolean isRunning = true;
    private boolean isPaused = false;
    private Object threadLock = new Object();
    //endregion

    //Constructor
    public UpdateThread(MinigamesEngine gameEngine){
        this.gameEngine = gameEngine;
    }

    @Override
    public void start(){
        isRunning = true;
        isPaused = false;
        super.start();
    }

    public void stopGame(){
        isRunning = false;
        resumeGame();
    }

    public void pauseGame(){
        isPaused = true;
    }

    public void resumeGame(){
        if (isPaused){
            isPaused = false;
            synchronized (threadLock){threadLock.notify();}
        }
    }

    @Override
    public void run(){
        long previousTime;
        long currentTime;
        long elapsedTime;
        previousTime = System.currentTimeMillis();

        while (isRunning){
            currentTime = System.currentTimeMillis();
            elapsedTime = currentTime - previousTime;

            if (isPaused){
                while(isPaused){
                    try{
                        synchronized (threadLock){
                            threadLock.wait();
                        }
                    } catch(InterruptedException e){
                        /*Stay on loop*/
                    }
                }
                currentTime = System.currentTimeMillis();
            }
            gameEngine.update(elapsedTime);
            previousTime = currentTime;
        }
    }

    public boolean isGameRunning(){return isRunning;}
    public boolean isGamePaused(){return isPaused;}

}
