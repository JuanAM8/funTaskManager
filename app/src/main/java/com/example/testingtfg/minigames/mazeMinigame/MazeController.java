package com.example.testingtfg.minigames.mazeMinigame;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;

import com.example.testingtfg.MainActivity;
import com.example.testingtfg.R;
import com.example.testingtfg.minigames.Controller;

/*Clase que gestiona el control del minijuego 3*/
public class MazeController extends Controller {

    //region Parámetros
    private MainActivity act;
    public boolean leftPressed;
    public boolean rightPressed;
    public boolean upPressed;
    public boolean downPressed;
    //endregion

    //Constructor
    public MazeController(View v, MainActivity a){
        v.findViewById(R.id.maze_control_up).setOnTouchListener(new UpButtonTouchListener());
        v.findViewById(R.id.maze_control_down).setOnTouchListener(new DownButtonTouchListener());
        v.findViewById(R.id.maze_control_left).setOnTouchListener(new LeftButtonTouchListener());
        v.findViewById(R.id.maze_control_right).setOnTouchListener(new RightButtonTouchListener());
        act = a;
    }

    //Pulsación de Izquierda
    private class LeftButtonTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getActionMasked();
            if (action == MotionEvent.ACTION_DOWN) {
                leftPressed = true;
            }
            if (action == MotionEvent.ACTION_UP) {
                leftPressed = false;
            }
            return true;
        }
    }
    //Pulsación de Derecha
    private class RightButtonTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getActionMasked();
            if (action == MotionEvent.ACTION_DOWN) {
                rightPressed = true;
            }
            if (action == MotionEvent.ACTION_UP) {
                rightPressed = false;
            }
            return true;
        }
    }
    //Pulsación de Arriba
    private class UpButtonTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getActionMasked();
            if (action == MotionEvent.ACTION_DOWN) {
                upPressed = true;
            }
            if (action == MotionEvent.ACTION_UP) {
                upPressed = false;
            }
            return true;
        }
    }
    //Pulsación de Abajo
    private class DownButtonTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getActionMasked();
            if (action == MotionEvent.ACTION_DOWN) {
                downPressed = true;
            }
            if (action == MotionEvent.ACTION_UP) {
                downPressed = false;
            }
            return true;
        }
    }
}
