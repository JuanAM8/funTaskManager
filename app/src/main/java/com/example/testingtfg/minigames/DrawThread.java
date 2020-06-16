package com.example.testingtfg.minigames;

/*Thread de dibujado que muestra la escena*/
public class DrawThread extends Thread {

    private final MinigamesEngine gameEngine;
    private boolean isRunning = true;
    private boolean isPaused = false;
    private Object threadLock = new Object();

    public DrawThread(MinigamesEngine gameEngine){
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
        long elapsedTime;
        long currentTime;
        long previousTime = System.currentTimeMillis();

        while (isRunning){
            currentTime = System.currentTimeMillis();
            elapsedTime = currentTime - previousTime;

            if (isPaused){
                while (isPaused){
                    try {
                        synchronized(threadLock){
                            threadLock.wait();
                        }
                    } catch(InterruptedException e){
                    }
                }
                currentTime = System.currentTimeMillis();
            }

            if (elapsedTime < 20){
                try {
                    Thread.sleep(20-elapsedTime);
                } catch(InterruptedException e){/*Continue*/}
            }
            gameEngine.draw();
            previousTime = currentTime;
        }
    }

    public boolean isGameRunning(){return isRunning;}
    public boolean isGamePaused(){return isPaused;}
}
