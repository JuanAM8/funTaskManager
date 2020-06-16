package com.example.testingtfg.taskOrganizer;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import com.example.testingtfg.MainActivity;
import com.example.testingtfg.R;

/*Clase que gestiona las notificaciones*/
public class DailyNotifier {

    //region Parámetros
    private MainActivity activity;
    private NotificationManager notificationManager;
    public static final String CHANNEL_VP_ID = "channel_vp";
    public static final String CHANNEL_TASKS_ID = "channel_tasks";
    //endregion

    //Constructor
    public DailyNotifier(MainActivity activity){
        this.activity = activity;
        initNotificationChannel();
    }

    //Inicializa los canales de notificaciones
    private void initNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_VP_ID,
                    "Channel_VirtualPet", NotificationManager.IMPORTANCE_LOW);
            notificationChannel.setDescription("Canal de Virtual Pet");
            NotificationChannel notificationChannel2 = new NotificationChannel(CHANNEL_TASKS_ID,
                    "Channel_Task", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription("Canal de Tareas");

            notificationManager = activity.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
            notificationManager.createNotificationChannel(notificationChannel2);
        }
    }

    //Envía una notificación al canal 1 con el título y contenido recibidos como parámetros
    public void sendChannel1Notification(String title, String content){
        Notification notification = new Notification.Builder(activity, CHANNEL_VP_ID)
                .setSmallIcon(R.mipmap.icon_launcher)
                .setContentTitle(title)
                .setContentText(content)
                .setCategory(Notification.CATEGORY_REMINDER)
                .build();

        notificationManager.notify(1, notification);
    }

    //Envía una notificación al canal 2 con el título y contenido recibidos como parámetros
    public void sendChannel2Notification(String title, String content){
        Notification notification = new Notification.Builder(activity, CHANNEL_TASKS_ID)
                .setSmallIcon(R.mipmap.icon_launcher)
                .setContentTitle(title)
                .setContentText(content)
                .setCategory(Notification.CATEGORY_REMINDER)
                .build();

        notificationManager.notify(2, notification);
    }
}
