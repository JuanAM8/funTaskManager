package com.example.testingtfg.minigames.ballMinigame;

import android.view.MotionEvent;
import android.view.View;

import com.example.testingtfg.R;
import com.example.testingtfg.minigames.Controller;

/*Clase que gestiona el control del minijuego 2*/
public class BallController extends Controller {

    private float startingPosX;
    private final double maxDistance;

    //Constructor: Recoge la view del controlador
    public BallController(View v){
        v.findViewById(R.id.left_touch).setOnTouchListener(new TouchListener());
        double pixelFactor = v.getHeight() / 400d;
        maxDistance = 50 * pixelFactor;
    }

    //Control básico del movimiento
    private class TouchListener implements View.OnTouchListener{
        @Override
        public boolean onTouch(View v, MotionEvent event){
            int action = event.getActionMasked();
            if (action == MotionEvent.ACTION_DOWN) {
                startingPosX = event.getX(0);
            } else if (action == MotionEvent.ACTION_UP) {
                hFactor = 0;
            } else if (action == MotionEvent.ACTION_MOVE) {
                hFactor = (event.getX(0) - startingPosX) / maxDistance;
                if (hFactor > 1) {hFactor = 1;}
                else if (hFactor < -1) {hFactor = -1;}
            }
            return true;
        }
    }

}
