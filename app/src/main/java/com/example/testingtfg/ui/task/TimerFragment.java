package com.example.testingtfg.ui.task;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.testingtfg.BaseFragment;
import com.example.testingtfg.R;

import java.util.concurrent.TimeUnit;
/*Clase que gestiona el Fragment del Temporizador*/
public class TimerFragment extends BaseFragment {

    //region Parámetros Visuales
    TextView secondsText;
    TextView minutesText;
    TextView hoursText;
    Button buttonClose;
    Button buttonStart;
    TimePicker timePicker;
    //endregion

    //region Métodos Visuales
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_timer, container, false);
        final TextView textView = root.findViewById(R.id.text_task_editor);
        loadColors(root);
        setupViews(root);
        return root;
    }

    private void setupViews(View v){
        ((TextView)v.findViewById(R.id.timerTitle)).setTextColor(defaultTextSecondaryColor);
        ((TextView)v.findViewById(R.id.timerTitle)).setTextSize(((TextView)v.
                findViewById(R.id.timerTitle)).getTextSize()/getResources().getDisplayMetrics().
                scaledDensity+defaultTextSizeChange);

        GradientDrawable windowDrawable = (GradientDrawable) v.getBackground();
        if (gradientMainColorActive){
            windowDrawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,
                    new int[]{defaultMainGradientColor1,
                            defaultMainGradientColor2, defaultMainGradientColor3});
            windowDrawable.setCornerRadius(100);
        } else {
            windowDrawable.setColor(defaultMainColor);
        }
        windowDrawable.setStroke(defaultButtonRadius+3, defaultSecondaryColor);
        v.setBackground(windowDrawable);

        secondsText = (TextView) v.findViewById(R.id.textSecs);
        minutesText = (TextView) v.findViewById(R.id.textMins);
        hoursText = (TextView) v.findViewById(R.id.textHours);
        buttonClose = (Button) v.findViewById(R.id.buttonClose);
        buttonStart = (Button) v.findViewById(R.id.buttonStart);
        timePicker = (TimePicker) v.findViewById(R.id.timePickerCountdown);
        secondsText.setText("s");
        minutesText.setText("m");
        hoursText.setText("h");
        secondsText.setTextColor(defaultTextSecondaryColor);
        minutesText.setTextColor(defaultTextSecondaryColor);
        hoursText.setTextColor(defaultTextSecondaryColor);
        hoursText.setTextSize(hoursText.getTextSize()/getResources().getDisplayMetrics().scaledDensity+defaultTextSizeChange);
        minutesText.setTextSize(minutesText.getTextSize()/getResources().getDisplayMetrics().scaledDensity+defaultTextSizeChange);
        secondsText.setTextSize(secondsText.getTextSize()/getResources().getDisplayMetrics().scaledDensity+defaultTextSizeChange);
        GradientDrawable drawableButton = (GradientDrawable) getMainActivity().getDrawable(R.drawable.button3_shape);
        if (gradientSecondaryColorActive){
            drawableButton.setColors(new int[]{defaultSecondaryGradientColor1,
                    defaultSecondaryGradientColor2, defaultSecondaryGradientColor3});
        } else {
            drawableButton.setColor(defaultSecondaryColor);
        }
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickStart();
            }
        });
        buttonStart.setBackground(drawableButton);
        buttonStart.setTextColor(defaultTextMainColor);
        Bundle bundle = getArguments();
        timePicker.setMinute(bundle.getInt("minutes"));
        timePicker.setHour(bundle.getInt("hours"));
        if (bordersActive) {
            ((GradientDrawable) v.getBackground()).setStroke(defaultButtonRadius + 3, defaultSecondaryColor);
            drawableButton.setStroke(defaultButtonRadius, defaultBackgroundColor);
        }
    }
    //endregion

    //region Eventos
    private void onClickClose(){

    }

    private void onClickStart(){
        final int reward = timePicker.getHour();
        buttonStart.setClickable(false);
        getMainActivity().blockNavView(false);
        getMainActivity().setBackLocked(true);
        timePicker.setVisibility(View.GONE);
        long timerMillis = (timePicker.getMinute()*60 + timePicker.getHour()*3600)*1000;
        new CountDownTimer(timerMillis, 1000){
            public void onTick(long millisUntilFinished){
                hoursText.setText(TimeUnit.MILLISECONDS.toHours(millisUntilFinished) + "h");
                minutesText.setText(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)) + "m");
                secondsText.setText(TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)) + "s");
            }
            public void onFinish(){
                getMainActivity().toastMessage("Fin del periodo de concentración. Has obtenido " + reward + " fichas de juego");
                getMainActivity().getNotificationManager().sendChannel1Notification("Fin", "Ha finalizado el tiempo");
                getMainActivity().getVirtualPetManager().increasePlayPoints(reward);
                timePicker.setVisibility(View.VISIBLE);
                getMainActivity().blockNavView(true);
                getMainActivity().setBackLocked(false);
                buttonStart.setClickable(true);
            }
        }.start();
    }
    //endregion
}
