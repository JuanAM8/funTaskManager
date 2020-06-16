package com.example.testingtfg.minigames.shooterMinigame;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.testingtfg.BaseFragment;
import com.example.testingtfg.MainActivity;
import com.example.testingtfg.R;

/*Fragment del menú del minijuego 1*/
public class ShooterMenuFragment extends BaseFragment {

    Button buttonStart;
    Button buttonGoBack;
    TextView gameTitle;
    TextView gameDesc;

    public ShooterMenuFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.fragment_shooter_menu, container, false);
        loadColors(root);
        setupBackground(root);
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        gameTitle = (TextView) view.findViewById(R.id.placeholderView);
        gameDesc = (TextView) view.findViewById(R.id.placeholderView2);
        gameTitle.setTextColor(defaultTextMainColor);
        gameDesc.setTextColor(defaultTextMainColor);
        if (shadowsActive){
            gameTitle.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
            gameDesc.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
        } else {
            gameTitle.setShadowLayer(0, 0, 0, defaultTextMainColor);
            gameDesc.setShadowLayer(0, 0, 0, defaultTextMainColor);
        }

        buttonStart = (Button) view.findViewById(R.id.shooter_btn_start);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });
        buttonGoBack = (Button) view.findViewById(R.id.shooter_btn_close);
        buttonGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeGame(v);
            }
        });
        buttonStart.setTextColor(defaultTextSecondaryColor);
        buttonGoBack.setTextColor(defaultTextSecondaryColor);

        GradientDrawable drawableStart = (GradientDrawable) buttonStart.getBackground();
        GradientDrawable drawableBack = (GradientDrawable) buttonGoBack.getBackground();
        if (gradientMainColorActive){
            drawableStart.setColors(new int[]{defaultMainGradientColor1,
                    defaultMainGradientColor2, defaultMainGradientColor3});
            drawableBack.setColors(new int[]{defaultMainGradientColor1,
                    defaultMainGradientColor2, defaultMainGradientColor3});
        } else {
            drawableStart.setColor(defaultMainColor);
            drawableBack.setColor(defaultMainColor);
        }
        if (bordersActive){
            drawableStart.setStroke(defaultButtonRadius, defaultSecondaryColor);
            drawableBack.setStroke(defaultButtonRadius, defaultSecondaryColor);
        } else {
            drawableStart.setStroke(defaultButtonRadius, defaultMainColor);
            drawableBack.setStroke(defaultButtonRadius, defaultMainColor);
        }
    }

    private void startGame(){
        if (getMainActivity().getVirtualPetManager().getPlayPoints() > 0) {
            getMainActivity().blockNavView(false);
            getMainActivity().setBackLocked(true);
            getMainActivity().getVirtualPetManager().increasePlayPoints(-1);
            Fragment gameFragment = new ShooterGameFragment();
            ((MainActivity) getActivity()).goToFragment(gameFragment, R.id.game_screen);
        } else {
            getMainActivity().toastMessage("Para jugar necesitas al menos una ficha de juego. Consíguelas completando tareas");
        }
    }

    private void closeGame(View view){
        getActivity().getSupportFragmentManager().popBackStack();
    }
}
