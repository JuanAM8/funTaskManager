package com.example.testingtfg.minigames.ballMinigame;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.testingtfg.BaseFragment;
import com.example.testingtfg.R;

/*Fragment de Game Over*/
public class BallGameOverFragment extends BaseFragment {

    private int score;
    private boolean victory;

    public BallGameOverFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceBundle){
        View root = inflater.inflate(R.layout.fragment_glass_game_over, container, false);
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
        scoreText.setText("Puntuación: " + score);

        gameOverText.setTextColor(defaultTextMainColor);
        scoreText.setTextColor(defaultTextMainColor);
        if (shadowsActive){
            gameOverText.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
            scoreText.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
        } else {
            gameOverText.setShadowLayer(0, 0, 0, defaultTextMainColor);
            scoreText.setShadowLayer(0, 0, 0, defaultTextMainColor);
        }

        Button buttonBack = (Button) view.findViewById(R.id.buttonRetryGlass);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    getMainActivity().resetGame(2);
                }
            }
        );
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

    //Establece los records
    private void setupLeaderboard(){
        int top1 = getMainActivity().getStatsTracker().getTop1_record_game2();
        int top2 = getMainActivity().getStatsTracker().getTop2_record_game2();
        int top3 = getMainActivity().getStatsTracker().getTop3_record_game2();

        if (score > top1){
            getMainActivity().getStatsTracker().setTop1_record_game2(score);
            getMainActivity().getStatsTracker().setTop2_record_game2(top2);
            getMainActivity().getStatsTracker().setTop3_record_game2(top3);
            getMainActivity().toastMessage("¡Nuevo récord!");
        } else if (score > top2){
            getMainActivity().getStatsTracker().setTop2_record_game2(score);
            getMainActivity().getStatsTracker().setTop3_record_game2(top2);
            getMainActivity().toastMessage("¡Nuevo récord!");
        } else if (score > top3){
            getMainActivity().getStatsTracker().setTop3_record_game2(score);
            getMainActivity().toastMessage("¡Nuevo récord!");
        } else {
            getMainActivity().toastMessage("No has conseguido un nuevo récord");
        }
    }
}
