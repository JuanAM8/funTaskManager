package com.example.testingtfg.taskOrganizer;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.testingtfg.MainActivity;

import java.util.Calendar;

/*Clase que gestiona la información de las estadísticas*/
public class StatsTracker {

    //region Constantes
    public static int TOTAL_EGGS = 8;
    public static int TOTAL_CREATURES = 24;
    public static int TOTAL_ITEMS = 3;
    public static int TOTAL_MAZE_LEVELS = 6;
    //endregion

    //region Parámetros
    private int tasksFinishedToday;
    private int tasksFinishedThisWeek;
    private int tasksFinishedThisMonth;
    private int tasksFinishedThisYear;
    private int tasksFinishedHistorical;
    private int eggsObtained;
    private int evolutionsObtained;
    private int itemsObtained;
    private int expendablesObtained;
    private int top1_record_game1;
    private int top2_record_game1;
    private int top3_record_game1;
    private int top1_record_game2;
    private int top2_record_game2;
    private int top3_record_game2;
    private int top1_record_game3;
    private int top2_record_game3;
    private int top3_record_game3;
    private int finishedMazeLevels;
    private int pickedMazeItems;
    private int lastDay;
    private int lastMonth;
    private int lastYear;
    //endregion

    //region SharedPreferences
    SharedPreferences preferences;
    private static final String STATS_PREFERENCES = "stats_prefs";
    private static final String TASKS_TODAY = "tasks_today";
    private static final String TASKS_THIS_WEEK = "tasks_this_week";
    private static final String TASKS_THIS_MONTH = "tasks_this_month";
    private static final String TASKS_THIS_YEAR = "tasks_this_year";
    private static final String TASKS_HISTORICAL = "tasks_historical";
    private static final String EGGS_OBTAINED = "eggs_obtained";;
    private static final String EVOS_OBTAINED = "evos_obtained";
    private static final String ITEMS_OBTAINED = "items_obtained";
    private static final String EXPENDABLES_OBTAINED = "expendables_obtained";
    private static final String TOP1_RECORD_G1 = "record_t1_g1";
    private static final String TOP2_RECORD_G1 = "record_t2_g1";
    private static final String TOP3_RECORD_G1 = "record_t3_g1";
    private static final String TOP1_RECORD_G2 = "record_t1_g2";
    private static final String TOP2_RECORD_G2 = "record_t2_g2";
    private static final String TOP3_RECORD_G2 = "record_t3_g2";
    private static final String TOP1_RECORD_G3 = "record_t1_g3";
    private static final String TOP2_RECORD_G3 = "record_t2_g3";
    private static final String TOP3_RECORD_G3 = "record_t3_g3";
    private static final String FINISHED_MAZE_LEVELS = "maze_levels";
    private static final String PICKED_MAZE_ITEMS = "maze_items";
    private static final String LAST_DAY = "last_day";
    private static final String LAST_MONTH = "last_month";
    private static final String LAST_YEAR = "last_year";
    //endregion

    MainActivity activity;

    //Constructor
    public StatsTracker(MainActivity a){
        activity = a;
        loadPreferences();
        resetStats();
    }

    //Reinicia las estadísticas temporales de las tareas cuando se alcanzan las fechas necesarias
    private void resetStats(){
        if (lastDay != Calendar.getInstance().get(Calendar.DAY_OF_MONTH)){
            tasksFinishedToday = 0;
            lastDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        }
        if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY){
            tasksFinishedThisWeek = 0;
        }
        if (lastMonth != Calendar.getInstance().get(Calendar.MONTH)){
            tasksFinishedThisMonth = 0;
            lastMonth = Calendar.getInstance().get(Calendar.MONTH);
        }
        if (lastYear != Calendar.getInstance().get(Calendar.YEAR)){
            tasksFinishedThisYear = 0;
            lastYear = Calendar.getInstance().get(Calendar.YEAR);
        }
        savePreferences();
    }

    //Guarda en SharedPreferences la información
    private void savePreferences(){
        SharedPreferences.Editor spEditor = preferences.edit();
        spEditor.putInt(TASKS_TODAY, tasksFinishedToday);
        spEditor.putInt(TASKS_THIS_WEEK, tasksFinishedThisWeek);
        spEditor.putInt(TASKS_THIS_MONTH, tasksFinishedThisMonth);
        spEditor.putInt(TASKS_THIS_YEAR, tasksFinishedThisYear);
        spEditor.putInt(TASKS_HISTORICAL, tasksFinishedHistorical);
        spEditor.putInt(EGGS_OBTAINED, eggsObtained);
        spEditor.putInt(EVOS_OBTAINED, evolutionsObtained);
        spEditor.putInt(ITEMS_OBTAINED, itemsObtained);
        spEditor.putInt(EXPENDABLES_OBTAINED, expendablesObtained);
        spEditor.putInt(TOP1_RECORD_G1, top1_record_game1);
        spEditor.putInt(TOP2_RECORD_G1, top2_record_game1);
        spEditor.putInt(TOP3_RECORD_G1, top3_record_game1);
        spEditor.putInt(TOP1_RECORD_G2, top1_record_game2);
        spEditor.putInt(TOP2_RECORD_G2, top2_record_game2);
        spEditor.putInt(TOP3_RECORD_G2, top3_record_game2);
        spEditor.putInt(TOP1_RECORD_G3, top1_record_game3);
        spEditor.putInt(TOP2_RECORD_G3, top2_record_game3);
        spEditor.putInt(TOP3_RECORD_G3, top3_record_game3);
        spEditor.putInt(FINISHED_MAZE_LEVELS, finishedMazeLevels);
        spEditor.putInt(PICKED_MAZE_ITEMS, pickedMazeItems);
        spEditor.putInt(LAST_DAY, lastDay);
        spEditor.putInt(LAST_MONTH, lastMonth);
        spEditor.putInt(LAST_YEAR, lastYear);
        spEditor.commit();
    }

    //Recoge la información de SharedPreferences y la guarda en los parámetros
    private void loadPreferences(){
        preferences = activity.getSharedPreferences(STATS_PREFERENCES, Context.MODE_PRIVATE);
        tasksFinishedToday = preferences.getInt(TASKS_TODAY, 0);
        tasksFinishedThisWeek = preferences.getInt(TASKS_THIS_WEEK, 0);
        tasksFinishedThisMonth = preferences.getInt(TASKS_THIS_MONTH, 0);
        tasksFinishedThisYear = preferences.getInt(TASKS_THIS_YEAR, 0);
        tasksFinishedHistorical = preferences.getInt(TASKS_HISTORICAL, 0);
        eggsObtained = preferences.getInt(EGGS_OBTAINED, 0);
        evolutionsObtained = preferences.getInt(EVOS_OBTAINED, 0);
        itemsObtained = preferences.getInt(ITEMS_OBTAINED, 0);
        expendablesObtained = preferences.getInt(EXPENDABLES_OBTAINED, 0);
        top1_record_game1 = preferences.getInt(TOP1_RECORD_G1, 0);
        top2_record_game1 = preferences.getInt(TOP2_RECORD_G1, 0);
        top3_record_game1 = preferences.getInt(TOP3_RECORD_G1, 0);
        top1_record_game2 = preferences.getInt(TOP1_RECORD_G2, 0);
        top2_record_game2 = preferences.getInt(TOP1_RECORD_G2, 0);
        top3_record_game2 = preferences.getInt(TOP3_RECORD_G2, 0);
        top1_record_game3 = preferences.getInt(TOP1_RECORD_G3, 0);
        top2_record_game3 = preferences.getInt(TOP2_RECORD_G3, 0);
        top3_record_game3 = preferences.getInt(TOP3_RECORD_G3, 0);
        finishedMazeLevels = preferences.getInt(FINISHED_MAZE_LEVELS, 0);
        pickedMazeItems = preferences.getInt(PICKED_MAZE_ITEMS, 0);
        lastDay = preferences.getInt(LAST_DAY, 0);
        lastMonth = preferences.getInt(LAST_MONTH, 0);
        lastYear = preferences.getInt(LAST_YEAR, 0);
    }

    //Cuando se finaliza una tarea, se aumenta la cantidad de tareas finalizadas
    public void finishTasks(int tasksFinished){
        tasksFinishedToday += tasksFinished;
        tasksFinishedThisWeek += tasksFinished;
        tasksFinishedThisMonth += tasksFinished;
        tasksFinishedThisYear += tasksFinished;
        tasksFinishedHistorical += tasksFinished;
        savePreferences();
    }

    //regions getters y setters
    public void increaseEggCount(){
        eggsObtained++;
        savePreferences();
    }

    public void increaseEvolutionCount() {
        evolutionsObtained++;
        savePreferences();
    }

    public void increaseItemCount(){
        itemsObtained++;
        savePreferences();
    }

    public void increaseExpendablesCount(){
        expendablesObtained++;
        savePreferences();
    }

    public int getTop1_record_game1() {
        return top1_record_game1;
    }

    public void setTop1_record_game1(int top1_record_game1) {
        this.top1_record_game1 = top1_record_game1;
        savePreferences();
    }

    public int getTop2_record_game1() {
        return top2_record_game1;
    }

    public void setTop2_record_game1(int top2_record_game1) {
        this.top2_record_game1 = top2_record_game1;
        savePreferences();
    }

    public int getTop3_record_game1() {
        return top3_record_game1;
    }

    public void setTop3_record_game1(int top3_record_game1) {
        this.top3_record_game1 = top3_record_game1;
        savePreferences();
    }

    public int getTop1_record_game2() {
        return top1_record_game2;
    }

    public void setTop1_record_game2(int top1_record_game2) {
        this.top1_record_game2 = top1_record_game2;
        savePreferences();
    }

    public int getTop2_record_game2() {
        return top2_record_game2;
    }

    public void setTop2_record_game2(int top2_record_game2) {
        this.top2_record_game2 = top2_record_game2;
        savePreferences();
    }

    public int getTop3_record_game2() {
        return top3_record_game2;
    }

    public void setTop3_record_game2(int top3_record_game2) {
        this.top3_record_game2 = top3_record_game2;
        savePreferences();
    }

    public int getTop1_record_game3() {
        return top1_record_game3;
    }

    public void setTop1_record_game3(int top1_record_game3) {
        this.top1_record_game3 = top1_record_game3;
        savePreferences();
    }

    public int getTop2_record_game3() {
        return top2_record_game3;
    }

    public void setTop2_record_game3(int top2_record_game3) {
        this.top2_record_game3 = top2_record_game3;
        savePreferences();
    }

    public int getTop3_record_game3() {
        return top3_record_game3;
    }

    public void setTop3_record_game3(int top3_record_game3) {
        this.top3_record_game3 = top3_record_game3;
        savePreferences();
    }

    public int getFinishedMazeLevels() {
        return finishedMazeLevels;
    }

    public void setFinishedMazeLevels(int finishedMazeLevels) {
        this.finishedMazeLevels = finishedMazeLevels;
        savePreferences();
    }

    public int getPickedMazeItems() {
        return pickedMazeItems;
    }

    public void setPickedMazeItems(int pickedMazeItems) {
        this.pickedMazeItems = pickedMazeItems;
        savePreferences();
    }

    public int getTasksFinishedToday() {
        return tasksFinishedToday;
    }

    public int getTasksFinishedThisWeek() {
        return tasksFinishedThisWeek;
    }

    public int getTasksFinishedThisMonth() {
        return tasksFinishedThisMonth;
    }

    public int getTasksFinishedThisYear() {
        return tasksFinishedThisYear;
    }

    public int getTasksFinishedHistorical() {
        return tasksFinishedHistorical;
    }

    public int getEggsObtained() {
        return eggsObtained;
    }

    public int getEvolutionsObtained() {
        return evolutionsObtained;
    }

    public int getItemsObtained() {
        return itemsObtained;
    }

    public int getExpendablesObtained() {
        return expendablesObtained;
    }
    //endregion
}
