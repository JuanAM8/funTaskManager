package com.example.testingtfg.taskOrganizer;

import java.util.ArrayList;
import java.util.Calendar;

/*Clase que contiene los parámetros de los eventos*/

public class Event {

    //region Parámetros
    private int id;
    private String title;
    private Calendar date;
    //endregion

    //Constructor
    public Event(String title, Calendar date){
        this.title = title;
        this.date = date;
    }

    //region getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    //endregion
}
