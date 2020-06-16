package com.example.testingtfg;

import android.media.AudioManager;
import android.os.Bundle;
import android.widget.Toast;

import com.example.testingtfg.database.DatabaseManager;
import com.example.testingtfg.minigames.SoundsManager;
import com.example.testingtfg.minigames.ballMinigame.BallMenuFragment;
import com.example.testingtfg.minigames.mazeMinigame.MazeMenuFragment;
import com.example.testingtfg.minigames.shooterMinigame.ShooterMenuFragment;
import com.example.testingtfg.minigames.virtualPet.VirtualPetManager;
import com.example.testingtfg.taskOrganizer.DailyNotifier;
import com.example.testingtfg.taskOrganizer.StatsTracker;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

/*Actividad que alberga los fragmentos y gestiona el comportamiento base de la aplicación*/
public class MainActivity extends AppCompatActivity {

    //region Managers
    private SoundsManager soundsManager;
    private VirtualPetManager virtualPetManager;
    private DailyNotifier notificationManager;
    private StatsTracker statsTracker;
    private StylesManager stylesManager;
    //endregion
    //region Parámetros
    private boolean debugMode = false;
    private boolean isGameRunning;
    private boolean backLocked;
    //endregion
    //region Visual
    NavController navController;
    BottomNavigationView navView;
    //endregion

    //region Eventos de la Actividad
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_tasks, R.id.navigation_calendar, R.id.navigation_games,
                R.id.navigation_social, R.id.navigation_configuration).build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if (isGameRunning){ stopGame();}
            }
        });
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        if (debugMode){
            DatabaseManager.removeDatabase(this);
        }
        notificationManager = new DailyNotifier(this);
        statsTracker = new StatsTracker(this);
        stylesManager = new StylesManager(this);
        soundsManager = new SoundsManager(getApplicationContext());
        isGameRunning = false;
        backLocked = false;
        virtualPetManager = new VirtualPetManager(this);
    }
    //endregion

    //region Gestión de Fragments
    /*Inicia un nuevo fragment sin enviar información desde el fragment anterior*/
    public void goToFragment(Fragment targetFragment, int container){
        getSupportFragmentManager().beginTransaction().
                replace(container, targetFragment).
                addToBackStack(null).commit();
    }
    /*Inicia un nuevo fragment recogiendo información del fragment anterior*/
    public void goToFragment(Fragment targetFragment, int container, Bundle infoPackage){
        targetFragment.setArguments(infoPackage);
        getSupportFragmentManager().beginTransaction().
                replace(container, targetFragment).
                addToBackStack(null).commit();
    }

    public void blockNavView(boolean enabled){
        for (int i = 0; i < navView.getMenu().size(); i++) {
            navView.getMenu().getItem(i).setEnabled(enabled);
        }
    }
    //endregion

    //region Gestión de los Minijuegos
    public void resetGame(int gameIndex){
        switch(gameIndex){
            case 1:
                goToFragment(new ShooterMenuFragment(), R.id.game_screen);
                break;
            case 2:
                goToFragment(new BallMenuFragment(), R.id.game_screen);
                break;
            case 3:
                goToFragment(new MazeMenuFragment(), R.id.game_screen);
                break;
            default:
                break;
        }
    }

    private void stopGame(){
    }
    //endregion

    //region getters y setters
    public BottomNavigationView getNavView(){return navView;}

    public SoundsManager getSoundsManager(){
        return soundsManager;
    }

    public VirtualPetManager getVirtualPetManager(){
        return virtualPetManager;
    }

    public DailyNotifier getNotificationManager(){return notificationManager;}

    public StatsTracker getStatsTracker(){return statsTracker;}

    public StylesManager getStylesManager(){return stylesManager;}

    public void setGameRunning(boolean gameRunning){
        isGameRunning = gameRunning;
    }

    public boolean isGameRunning(){
        return isGameRunning;
    }

    public void setBackLocked(boolean locked){backLocked = locked;}

    public boolean isBackLocked(){return backLocked;}

    //endregion

    //region Mensajes y Notificaciones
    public void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void notifyMessage(String title, String message, int channel){
        switch (channel){
            case 0:
                notificationManager.sendChannel1Notification(title, message);
                break;
            case 1:
                notificationManager.sendChannel2Notification(title, message);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (!backLocked) {
            super.onBackPressed();
        }
    }
    //endregion
}
