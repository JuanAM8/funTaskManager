package com.example.testingtfg.taskOrganizer;

import android.app.Activity;

import com.example.testingtfg.MainActivity;
import com.example.testingtfg.database.DatabaseManager;

import java.util.ArrayList;
import java.util.Calendar;

/*Clase que gestiona lo referido al calendario*/
public class CalendarManager {

    //region Parámetros
    private ArrayList<Event> events;
    private ArrayList<Task> tasks;
    private Activity activity;
    //endregion

    //Constructor
    public CalendarManager(Activity a){
        activity = a;
        events = DatabaseManager.readDataEvent(activity);
        tasks = DatabaseManager.readData(activity);
    }

    //region Métodos
    //Recoge las tareas para una fecha concreta de la lista de tareas
    public ArrayList<Task> getTasksFromDate(Calendar date){
        ArrayList<Task> matchingTasks = new ArrayList<>();
        for (Task task : tasks){
            if (task.getExpirationDate().get(Calendar.DAY_OF_MONTH) == date.get(Calendar.DAY_OF_MONTH)
                && task.getExpirationDate().get(Calendar.MONTH) == date.get(Calendar.MONTH)
                && task.getExpirationDate().get(Calendar.YEAR) == date.get(Calendar.YEAR)){
                matchingTasks.add(task);
            }
        }
        return matchingTasks;
    }
    //Recoge los eventos para una fecha concreta de la lista de eventos
    public ArrayList<Event> getEventsFromDate(Calendar date){
        ArrayList<Event> matchingEvents = new ArrayList<>();
        for (Event event : events){
            if (event.getDate().get(Calendar.DAY_OF_MONTH) == date.get(Calendar.DAY_OF_MONTH)
                    && event.getDate().get(Calendar.MONTH) == date.get(Calendar.MONTH)
                    && event.getDate().get(Calendar.YEAR) == date.get(Calendar.YEAR)){
                matchingEvents.add(event);
            }
        }
        return matchingEvents;
    }
    //Crea un nuevo evento
    public void addEvent(Event event){
        events.add(event);
        DatabaseManager.saveDataEvent(event, activity);
    }

    //endregion
}
