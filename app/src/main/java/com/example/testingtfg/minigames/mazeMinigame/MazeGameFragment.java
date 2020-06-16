package com.example.testingtfg.minigames.mazeMinigame;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.example.testingtfg.BaseFragment;
import com.example.testingtfg.MainActivity;
import com.example.testingtfg.R;
import com.example.testingtfg.minigames.MinigamesEngine;
import com.example.testingtfg.minigames.GameView;

/*Fragmento donde se desarrolla el minijuego 3*/
public class MazeGameFragment extends BaseFragment {

    private MinigamesEngine gameEngine;
    private final int GAME_INDEX = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_maze_game, container, false);
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final ViewTreeObserver observer = view.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener(){
            @Override
            public void onGlobalLayout(){
                observer.removeOnGlobalLayoutListener(this);
                initGame();
            }
        });
    }

    @Override
    public void onPause(){
        super.onPause();
        if (gameEngine.isRunning()){pauseGame();}
    }

    @Override
    public void onDestroy() {
        getMainActivity().setGameRunning(false);
        super.onDestroy();
        gameEngine.stopGame(false, false, GAME_INDEX);
    }

    private void initGame(){
        GameView gameView = (GameView) getView().findViewById(R.id.gameView);
        gameEngine = new MinigamesEngine(getActivity(), gameView, GAME_INDEX);
        gameEngine.setSoundsManager(((MainActivity)getActivity()).getSoundsManager());
        gameEngine.setController(new MazeController(getView(), getMainActivity()));
        //gameEngine.addGameObject(new ParallaxBackground(gameEngine, 30, drawableBackground));
        //gameEngine.addGameObject(new MazeCharacter(gameEngine, characterSprite));
        gameEngine.addGameObject(gameEngine.uiManager);
        gameEngine.addGameObject(new MazeGameManager(gameEngine));
        gameEngine.startGame(GAME_INDEX);
        getMainActivity().setGameRunning(true);
    }

    private void pauseGame(){
        gameEngine.pauseGame();
        new AlertDialog.Builder(getActivity())
                .setTitle("Pausa")
                .setMessage("Juego en pausa")
                .setPositiveButton("Reanudar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        gameEngine.resumeGame();
                    }
                })
                .setNegativeButton("Salir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        gameEngine.stopGame(true, false, GAME_INDEX);
                        getMainActivity().blockNavView(true);
                        getMainActivity().setBackLocked(false);
                        getMainActivity().resetGame(3);
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        gameEngine.resumeGame();
                    }
                })
                .create()
                .show();
    }
}
