package com.example.testingtfg.minigames.shooterMinigame;

import android.view.MotionEvent;
import android.view.View;

import com.example.testingtfg.R;
import com.example.testingtfg.minigames.Controller;

/*Clase que gestiona el control del minijuego 1*/
public class ShooterController extends Controller {

    //region Parámetros
    private float startingPosX;
    private float startingPosY;
    private final double maxDistance;
    public boolean isFiring;
    //endregion

    //Constructor
    public ShooterController(View v){
        v.findViewById(R.id.shooter_control_main).setOnTouchListener(new JoystickTouchListener());
        v.findViewById(R.id.shooter_control_touch).setOnTouchListener(new FireButtonTouchListener());
        double pixelFactor = v.getHeight() / 400d;
        maxDistance = 50 * pixelFactor;
    }

    //Método de control del movimiento
    private class JoystickTouchListener implements View.OnTouchListener{
        @Override
        public boolean onTouch(View v, MotionEvent event){
            int action = event.getActionMasked();
            if (action == MotionEvent.ACTION_DOWN) {
                startingPosX = event.getX(0);
                startingPosY = event.getY(0);
            } else if (action == MotionEvent.ACTION_UP) {
                hFactor = 0;
                vFactor = 0;
            } else if (action == MotionEvent.ACTION_MOVE) {
                hFactor = (event.getX(0) - startingPosX) / maxDistance;
                if (hFactor > 1) {hFactor = 1;}
                else if (hFactor < -1) {hFactor = -1;}

                vFactor = (event.getY(0) - startingPosY) / maxDistance;
                if (vFactor > 1) {vFactor = 1;}
                else if (vFactor < -1) {vFactor = -1;}
            }
            return true;
        }
    }

    //Método del control de disparo
    private class FireButtonTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getActionMasked();
            if (action == MotionEvent.ACTION_DOWN) {isFiring = true;}
            else if (action == MotionEvent.ACTION_UP) {isFiring = false;}
            return true;
        }
    }

}
