package com.example.testingtfg.minigames.mazeMinigame;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.testingtfg.BaseFragment;
import com.example.testingtfg.R;

/*Fragment de la pantalla de Game Over del minijuego 3*/
public class MazeGameOverFragment extends BaseFragment {
    private int score;
    private boolean victory;

    public MazeGameOverFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceBundle){
        View root = inflater.inflate(R.layout.fragment_maze_game_over, container, false);
        victory = (boolean) getArguments().getSerializable("victory");
        score = getArguments().getInt("score");
        loadColors(root);
        setupBackground(root);
        setupLeaderboard();
        getMainActivity().blockNavView(true);
        getMainActivity().setBackLocked(false);
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView gameOverText = (TextView) view.findViewById(R.id.gameOverText);
        if (victory){
            gameOverText.setText("Victoria");
        } else {
            gameOverText.setText("Derrota");
        }
        TextView scoreText = (TextView) view.findViewById(R.id.scoreText);
        scoreText.setText("Objetos recogidos: " + score);

        gameOverText.setTextColor(defaultTextMainColor);
        scoreText.setTextColor(defaultTextMainColor);
        if (shadowsActive){
            gameOverText.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
            scoreText.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
        } else {
            gameOverText.setShadowLayer(0, 0, 0, defaultTextMainColor);
            scoreText.setShadowLayer(0, 0, 0, defaultTextMainColor);
        }

        Button buttonBack = (Button) view.findViewById(R.id.buttonRetryMaze);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMainActivity().resetGame(3);
            }
        });
        buttonBack.setTextColor(defaultTextSecondaryColor);
        GradientDrawable drawableBack = (GradientDrawable) buttonBack.getBackground();
        if (gradientMainColorActive){
            drawableBack.setColors(new int[]{defaultMainGradientColor1,
                    defaultMainGradientColor2, defaultMainGradientColor3});
        } else {
            drawableBack.setColor(defaultMainColor);
        }
        if (bordersActive){
            drawableBack.setStroke(defaultButtonRadius, defaultSecondaryColor);
        } else {
            drawableBack.setStroke(defaultButtonRadius, defaultMainColor);
        }
        getMainActivity().setGameRunning(false);
    }

    //Establece los puntos
    private void setupLeaderboard(){
        int pickedItems = getMainActivity().getStatsTracker().getPickedMazeItems() + score;
        int finishedLevels = getMainActivity().getStatsTracker().getFinishedMazeLevels();
        if (victory){
            finishedLevels++;
            getMainActivity().getStatsTracker().setFinishedMazeLevels(finishedLevels);
        }
        getMainActivity().getStatsTracker().setPickedMazeItems(pickedItems);
    }
}
