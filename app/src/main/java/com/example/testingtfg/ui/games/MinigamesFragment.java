package com.example.testingtfg.ui.games;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.testingtfg.BaseFragment;
import com.example.testingtfg.MainActivity;
import com.example.testingtfg.R;
import com.example.testingtfg.minigames.ballMinigame.BallMenuFragment;
import com.example.testingtfg.minigames.mazeMinigame.MazeMenuFragment;
import com.example.testingtfg.minigames.shooterMinigame.ShooterMenuFragment;

/*Clase que gestiona el Fragment de la pantalla de selección de Minijuegos*/
public class MinigamesFragment extends BaseFragment {

    //region Parámetros Visuales
    Button buttonMinigame1;
    Button buttonMinigame2;
    Button buttonMinigame3;
    Button buttonBack;
    //endregion

    //region Métodos Visuales
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_games, container, false);
        loadColors(root);
        setupBackground(root);
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        buttonMinigame1 = (Button) view.findViewById(R.id.button_minigame1);
        buttonMinigame2 = (Button) view.findViewById(R.id.button_minigame2);
        buttonMinigame3 = (Button) view.findViewById(R.id.button_minigame3);
        buttonBack = (Button) view.findViewById(R.id.buttonBackMiniGames);
        GradientDrawable drawableMG1 = (GradientDrawable) buttonMinigame1.getBackground();
        GradientDrawable drawableMG2 = (GradientDrawable) buttonMinigame2.getBackground();
        GradientDrawable drawableMG3 = (GradientDrawable) buttonMinigame3.getBackground();
        GradientDrawable drawableBack = (GradientDrawable) buttonBack.getBackground();
        if(gradientMainColorActive){
            drawableMG1.setColors(new int[]{defaultMainGradientColor1,
                    defaultMainGradientColor2, defaultMainGradientColor3});
            drawableMG2.setColors(new int[]{defaultMainGradientColor1,
                    defaultMainGradientColor2, defaultMainGradientColor3});
            drawableMG3.setColors(new int[]{defaultMainGradientColor1,
                    defaultMainGradientColor2, defaultMainGradientColor3});
            drawableBack.setColors(new int[]{defaultMainGradientColor1,
                    defaultMainGradientColor2, defaultMainGradientColor3});
        } else {
            drawableMG1.setColor(defaultMainColor);
            drawableMG2.setColor(defaultMainColor);
            drawableMG3.setColor(defaultMainColor);
            drawableBack.setColor(defaultMainColor);
        }

        if (bordersActive){
            drawableMG1.setStroke(defaultButtonRadius, defaultSecondaryColor);
            drawableMG2.setStroke(defaultButtonRadius, defaultSecondaryColor);
            drawableMG3.setStroke(defaultButtonRadius, defaultSecondaryColor);
            drawableBack.setStroke(defaultButtonRadius, defaultSecondaryColor);
        } else {
            drawableMG1.setStroke(defaultButtonRadius, defaultMainColor);
            drawableMG2.setStroke(defaultButtonRadius, defaultMainColor);
            drawableMG3.setStroke(defaultButtonRadius, defaultMainColor);
            drawableBack.setStroke(defaultButtonRadius, defaultMainColor);
        }
        buttonMinigame1.setBackground(drawableMG1);
        buttonMinigame2.setBackground(drawableMG2);
        buttonMinigame3.setBackground(drawableMG3);
        buttonBack.setBackground(drawableBack);
        buttonMinigame1.setTextColor(defaultTextSecondaryColor);
        buttonMinigame2.setTextColor(defaultTextSecondaryColor);
        buttonMinigame3.setTextColor(defaultTextSecondaryColor);
        buttonBack.setTextColor(defaultTextSecondaryColor);
        buttonMinigame1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMiniGame1();
            }
        });
        buttonMinigame2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMiniGame2();
            }
        });
        buttonMinigame3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMiniGame3();
            }
        });
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMainActivity().getSupportFragmentManager().popBackStack();
            }
        });
        if (shadowsActive){
            buttonMinigame1.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextSecondaryColor);
            buttonMinigame2.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextSecondaryColor);
            buttonMinigame3.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextSecondaryColor);
            buttonBack.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextSecondaryColor);
        } else {
            buttonMinigame1.setShadowLayer(0, 0, 0, defaultTextSecondaryColor);
            buttonMinigame2.setShadowLayer(0, 0, 0,  defaultTextSecondaryColor);
            buttonMinigame3.setShadowLayer(0, 0, 0,  defaultTextSecondaryColor);
            buttonBack.setShadowLayer(0, 0, 0,  defaultTextSecondaryColor);
        }
    }
    private void hideButtons(){
        buttonMinigame1.setVisibility(View.GONE);
        buttonMinigame2.setVisibility(View.GONE);
        buttonMinigame3.setVisibility(View.GONE);
        buttonBack.setVisibility(View.GONE);
    }
    //endregion

    //region Navegación
    private void startMiniGame1(){
        hideButtons();
        Fragment gameMenuFragment = new ShooterMenuFragment();
        ((MainActivity)getActivity()).goToFragment(gameMenuFragment, R.id.game_screen);
    }

    private void startMiniGame2(){
        hideButtons();
        Fragment gameMenuFragment = new BallMenuFragment();
        ((MainActivity)getActivity()).goToFragment(gameMenuFragment, R.id.game_screen);
    }

    private void startMiniGame3(){
        hideButtons();
        Fragment gameMenuFragment = new MazeMenuFragment();
        ((MainActivity)getActivity()).goToFragment(gameMenuFragment, R.id.game_screen);
    }
    //endregion


}
