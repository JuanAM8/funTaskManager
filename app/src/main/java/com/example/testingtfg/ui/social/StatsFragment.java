package com.example.testingtfg.ui.social;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;

import com.example.testingtfg.BaseFragment;
import com.example.testingtfg.R;
import com.example.testingtfg.taskOrganizer.StatsTracker;
/*Clase que gestiona el Fragmento de Estadísticas*/
public class StatsFragment extends BaseFragment {

    private StatsViewModel statsViewModel;
    public static final String TWEET_TITLE = "Mis estadísticas en Fun Task Manager:";

    //region Parámetros Visuales
    TextView infoTasks;
    TextView infoPets;
    TextView infoGame1;
    TextView infoGame2;
    TextView infoGame3;

    TextView titleMG1;
    TextView titleMG2;
    TextView titleMG3;

    Button tweetButtonTasks;
    Button tweetButtonPets;
    Button tweetButtonMinigames;
    //endregion

    //region Parámetros de gestión
    private StatsTracker statsTracker;
    //endregion

    //region Métodos Visuales
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        statsViewModel =
                ViewModelProviders.of(this).get(StatsViewModel.class);

        View root = inflater.inflate(R.layout.fragment_social, container, false);
        loadColors(root);
        initViews(root);
        fillTextInformation();
        return root;
    }

    private void initViews(View view){
        setupBackground(view);
        view.findViewById(R.id.separationBar).setBackgroundColor(defaultSecondaryColor);
        view.findViewById(R.id.separationBar2).setBackgroundColor(defaultSecondaryColor);

        ((TextView)view.findViewById(R.id.title)).setTextColor(defaultTextMainColor);
        ((TextView)view.findViewById(R.id.title_tasks)).setTextColor(defaultTextMainColor);
        ((TextView)view.findViewById(R.id.title_pets)).setTextColor(defaultTextMainColor);
        ((TextView)view.findViewById(R.id.title_games)).setTextColor(defaultTextMainColor);

        ((TextView)view.findViewById(R.id.title)).setTextSize(((TextView)view.findViewById(R.id.title)).getTextSize()/getResources().
                getDisplayMetrics().scaledDensity+defaultTextSizeChange);
        ((TextView)view.findViewById(R.id.title_tasks)).setTextSize(((TextView)view.findViewById(R.id.title_tasks)).getTextSize()/getResources().
                getDisplayMetrics().scaledDensity+defaultTextSizeChange);
        ((TextView)view.findViewById(R.id.title_pets)).setTextSize(((TextView)view.findViewById(R.id.title_pets)).getTextSize()/getResources().
                getDisplayMetrics().scaledDensity+defaultTextSizeChange);
        ((TextView)view.findViewById(R.id.title_games)).setTextSize(((TextView)view.findViewById(R.id.title_games)).getTextSize()/getResources().
                getDisplayMetrics().scaledDensity+defaultTextSizeChange);

        infoTasks = (TextView) view.findViewById(R.id.info_tasks);
        infoPets = (TextView) view.findViewById(R.id.info_pets);
        infoGame1 = (TextView) view.findViewById(R.id.info_mg1);
        infoGame2 = (TextView) view.findViewById(R.id.info_mg2);
        infoGame3 = (TextView) view.findViewById(R.id.info_mg3);
        titleMG1 = (TextView) view.findViewById(R.id.title_mg1);
        titleMG2 = (TextView) view.findViewById(R.id.title_mg2);
        titleMG3 = (TextView) view.findViewById(R.id.title_mg3);
        tweetButtonTasks = (Button) view.findViewById(R.id.tweetButton_tasks);
        tweetButtonPets = (Button) view.findViewById(R.id.tweetButton_pets);
        tweetButtonMinigames = (Button) view.findViewById(R.id.tweetButton_games);

        tweetButtonTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendTweet(0);
            }
        });
        tweetButtonPets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendTweet(1);
            }
        });
        tweetButtonMinigames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendTweet(2);
            }
        });

        infoTasks.setTextColor(defaultTextMainColor);
        infoPets.setTextColor(defaultTextMainColor);
        infoGame1.setTextColor(defaultTextMainColor);
        infoGame2.setTextColor(defaultTextMainColor);
        infoGame3.setTextColor(defaultTextMainColor);
        titleMG1.setTextColor(defaultTextMainColor);
        titleMG2.setTextColor(defaultTextMainColor);
        titleMG3.setTextColor(defaultTextMainColor);
        tweetButtonTasks.setTextColor(defaultTextSecondaryColor);
        tweetButtonPets.setTextColor(defaultTextSecondaryColor);
        tweetButtonMinigames.setTextColor(defaultTextSecondaryColor);

        infoTasks.setTextSize(infoTasks.getTextSize()/getResources().getDisplayMetrics().scaledDensity+defaultTextSizeChange);
        infoGame1.setTextSize(infoGame1.getTextSize()/getResources().getDisplayMetrics().scaledDensity+defaultTextSizeChange);
        infoGame2.setTextSize(infoGame2.getTextSize()/getResources().getDisplayMetrics().scaledDensity+defaultTextSizeChange);
        infoGame3.setTextSize(infoGame3.getTextSize()/getResources().getDisplayMetrics().scaledDensity+defaultTextSizeChange);
        infoPets.setTextSize(infoPets.getTextSize()/getResources().getDisplayMetrics().scaledDensity+defaultTextSizeChange);
        titleMG1.setTextSize(titleMG1.getTextSize()/getResources().getDisplayMetrics().scaledDensity+defaultTextSizeChange);
        titleMG2.setTextSize(titleMG2.getTextSize()/getResources().getDisplayMetrics().scaledDensity+defaultTextSizeChange);
        titleMG3.setTextSize(titleMG3.getTextSize()/getResources().getDisplayMetrics().scaledDensity+defaultTextSizeChange);

        if (shadowsActive){
            ((TextView)view.findViewById(R.id.title)).setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
            ((TextView)view.findViewById(R.id.title_tasks)).setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
            ((TextView)view.findViewById(R.id.title_pets)).setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
            ((TextView)view.findViewById(R.id.title_games)).setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
            infoTasks.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
            infoPets.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
            infoGame1.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
            infoGame2.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
            infoGame3.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
            titleMG1.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
            titleMG2.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
            titleMG3.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
            tweetButtonTasks.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextSecondaryColor);
            tweetButtonPets.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextSecondaryColor);
            tweetButtonMinigames.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextSecondaryColor);
        } else {
            ((TextView)view.findViewById(R.id.title)).setShadowLayer(0, 0, 0, defaultTextMainColor);
            ((TextView)view.findViewById(R.id.title_tasks)).setShadowLayer(0, 0, 0, defaultTextMainColor);
            ((TextView)view.findViewById(R.id.title_pets)).setShadowLayer(0, 0, 0, defaultTextMainColor);
            ((TextView)view.findViewById(R.id.title_games)).setShadowLayer(0, 0, 0, defaultTextMainColor);
            infoTasks.setShadowLayer(0, 0, 0, defaultTextMainColor);
            infoPets.setShadowLayer(0, 0, 0, defaultTextMainColor);
            infoGame1.setShadowLayer(0, 0, 0, defaultTextMainColor);
            infoGame2.setShadowLayer(0, 0, 0, defaultTextMainColor);
            infoGame3.setShadowLayer(0, 0, 0, defaultTextMainColor);
            titleMG1.setShadowLayer(0, 0, 0, defaultTextMainColor);
            titleMG2.setShadowLayer(0, 0, 0, defaultTextMainColor);
            titleMG3.setShadowLayer(0, 0, 0, defaultTextMainColor);
            tweetButtonTasks.setShadowLayer(0, 0, 0, defaultTextSecondaryColor);
            tweetButtonPets.setShadowLayer(0, 0, 0, defaultTextSecondaryColor);
            tweetButtonMinigames.setShadowLayer(0, 0, 0, defaultTextSecondaryColor);
        }

        GradientDrawable drawableButtonTasks = (GradientDrawable) tweetButtonTasks.getBackground();
        GradientDrawable drawableButtonPets = (GradientDrawable) tweetButtonPets.getBackground();
        GradientDrawable drawableButtonMinigames= (GradientDrawable) tweetButtonMinigames.getBackground();
        if (gradientMainColorActive){
            drawableButtonTasks.setColors(new int[]{defaultMainGradientColor1,
                    defaultMainGradientColor2, defaultMainGradientColor3});
            drawableButtonPets.setColors(new int[]{defaultMainGradientColor1,
                    defaultMainGradientColor2, defaultMainGradientColor3});
            drawableButtonMinigames.setColors(new int[]{defaultMainGradientColor1,
                    defaultMainGradientColor2, defaultMainGradientColor3});
        } else {
            drawableButtonTasks.setColor(defaultMainColor);
            drawableButtonPets.setColor(defaultMainColor);
            drawableButtonMinigames.setColor(defaultMainColor);
        }
        if (bordersActive){
            drawableButtonTasks.setStroke(defaultButtonRadius, defaultSecondaryColor);
            drawableButtonPets.setStroke(defaultButtonRadius, defaultSecondaryColor);
            drawableButtonMinigames.setStroke(defaultButtonRadius, defaultSecondaryColor);
        } else {
            drawableButtonTasks.setStroke(defaultButtonRadius, defaultMainColor);
            drawableButtonPets.setStroke(defaultButtonRadius, defaultMainColor);
            drawableButtonMinigames.setStroke(defaultButtonRadius, defaultMainColor);
        }

        statsTracker = getMainActivity().getStatsTracker();
    }

    private void fillTextInformation(){
        infoTasks.setText("Tareas completadas hoy: " + statsTracker.getTasksFinishedToday() + "\n" +
                "Tareas completadas esta semana: " + statsTracker.getTasksFinishedThisWeek() + "\n" +
                "Tareas completadas este mes: " + statsTracker.getTasksFinishedThisMonth() + "\n" +
                "Tareas completadas este año: " + statsTracker.getTasksFinishedThisYear() + "\n" +
                "Tareas completadas en total: " + statsTracker.getTasksFinishedHistorical());
        infoPets.setText("Huevos de Taskys obtenidos: " + statsTracker.getEggsObtained() +
                "/" + statsTracker.TOTAL_EGGS + "\n" +
                "Especies de Taskys descubiertas: " + statsTracker.getEvolutionsObtained() +
                "/" + statsTracker.TOTAL_CREATURES + "\n" +
                "Objetos comprados: " + statsTracker.getItemsObtained() + "/" +
                statsTracker.TOTAL_ITEMS + "\n" +
                "Consumibles comprados: " + statsTracker.getExpendablesObtained());
        infoGame1.setText("Mejor puntuación: " + statsTracker.getTop1_record_game1() + "\n" +
                "Segunda mejor: " + statsTracker.getTop2_record_game1() + "\n" +
                "Tercera mejor: " + statsTracker.getTop3_record_game1());
        infoGame2.setText("Mejor puntuación: " + statsTracker.getTop1_record_game2() + "\n" +
                "Segunda mejor: " + statsTracker.getTop2_record_game2() + "\n" +
                "Tercera mejor: " + statsTracker.getTop3_record_game2());
        infoGame3.setText("Monedas recogidas: " + statsTracker.getPickedMazeItems() + "\n" +
                "Niveles superados: " + statsTracker.getFinishedMazeLevels());
    }
    //endregion

    //Inicia la app de Twitter y envía un mensaje
    private void sendTweet(int index){
        //Source: https://stackoverflow.com/questions/5741818/how-to-tweet-from-android-app
        String twitterURL = "https://twitter.com/intent/tweet?text=";
        String message = "";
        switch (index){
            case 0:
                message = twitterURL + TWEET_TITLE + "\n¡Tareas completadas!" + "\n" +
                        "Hoy: " + statsTracker.getTasksFinishedToday() + "\n" +
                        "Esta semana: " + statsTracker.getTasksFinishedThisWeek() + "\n" +
                        "Este mes: " + statsTracker.getTasksFinishedThisMonth() + "\n" +
                        "Este año: " + statsTracker.getTasksFinishedThisYear() + "\n" +
                        "Total: " + statsTracker.getTasksFinishedHistorical();
                break;
            case 1:
                message = twitterURL + TWEET_TITLE + "\nHuevos de Tasky abiertos: " + statsTracker.getEggsObtained() +
                        "/" + statsTracker.TOTAL_EGGS + "\n" +
                        "Especies de Tasky descubiertas: " + statsTracker.getEvolutionsObtained() +
                        "/" + statsTracker.TOTAL_CREATURES;
                break;
            case 2:
                message = twitterURL + TWEET_TITLE + "\n¡Récords de los minijuegos!" +
                        "\nShoot'Em All: " + statsTracker.getTop1_record_game1() + " puntos" +
                        "\nPick the Ball: " + statsTracker.getTop1_record_game2() + " puntos" +
                        "\nFind the Coins: " + statsTracker.getFinishedMazeLevels() +
                        " niveles superados";
                break;
            default:
                message = twitterURL + "ERROR";//No debería ocurrir nunca
                break;
        }
        Uri uri = Uri.parse(message);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }
}
