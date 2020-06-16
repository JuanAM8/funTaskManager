package com.example.testingtfg.minigames.shooterMinigame;

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

/*Fragment donde se desarrolla la partida del minijuego 1*/
public class ShooterGameFragment extends BaseFragment {

    private MinigamesEngine gameEngine;
    private final int GAME_INDEX = 0;
    private int friendlyShipSprite;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_shooter_game, container, false);
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
                        getMainActivity().resetGame(1);
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

    private void initGame(){
        GameView gameView = (GameView) getView().findViewById(R.id.gameView);
        friendlyShipSprite = R.drawable.sprite_mg1_character;
        gameEngine = new MinigamesEngine(getActivity(), gameView, GAME_INDEX);
        gameEngine.setSoundsManager(((MainActivity)getActivity()).getSoundsManager());
        gameEngine.setController(new ShooterController(getView()));
        gameEngine.addGameObject(new ShooterCharacter(gameEngine, friendlyShipSprite));
        gameEngine.addGameObject(gameEngine.uiManager);
        gameEngine.addGameObject(new ShooterGameManager(gameEngine));
        gameEngine.startGame(GAME_INDEX);
        getMainActivity().setGameRunning(true);
    }
}
