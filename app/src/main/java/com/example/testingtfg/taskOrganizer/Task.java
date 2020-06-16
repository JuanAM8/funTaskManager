package com.example.testingtfg.taskOrganizer;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

/* Clase que gestiona el comportamiento de las tareas*/
public class Task implements Serializable {

    //region Enumeraciones
    public enum timeTag {TODAY, TOMORROW, THIS_WEEK, LATER, EXPIRED};
    public enum priorityTag {LOW, MID, HIGH, NONE};
    //endregion
    //region Parámetros
    private int id;//ID en la base de datos
    private int idInView;//ID en la View
    private String name;//Nombre de la tarea
    private Calendar expirationDate;//Fecha de expiración
    private float estimatedTimeHours;//Horas estimadas para completarla
    private float estimatedTimeMinutes;//Minutos estimados para completarla
    private priorityTag priority;//Prioridad
    private String category;//Categoría
    private timeTag timeForTask;//Etiqueta de en qué menú se debe situar la tarea
    private boolean finished;//Compleción de la tarea
    private ArrayList<SubTask> subTasks;//Lista de subtareas
    private ArrayList<Annotation> annotations;//Lista de anotaciones
    private ArrayList<Integer> addedFilesIds;//Lista de archivos añadidos. Sin uso
    //endregion

    //region Constructores
    //Constructor vacío
    public Task(){
        addedFilesIds = new ArrayList<Integer>();
    }

    //Constructor básico
    public Task(String name){
        this.name = name;
        addedFilesIds = new ArrayList<Integer>();
        annotations = new ArrayList<Annotation>();
    }

    //Constructor parametrizado
    public Task(String name, Calendar expirationDate, float estimatedHours,
                float estimatedMinutes, String priority, String category, String time,
                int finished, int idView){
        this.name = name;
        setTimeForTaskAsString(time);
        this.expirationDate = expirationDate;
        this.estimatedTimeHours = estimatedHours;
        this.estimatedTimeMinutes = estimatedMinutes;
        setPriorityAsString(priority);
        this.category = category;
        if (finished == 1) { this.finished = true; } else { this.finished = false; }
        this.idInView = idView;
        annotations = new ArrayList<Annotation>();
    }

    //Constructor parametrizado con id de la base de datos
    public Task(int id, String name, Calendar expirationDate, float estimatedHours,
                float estimatedMinutes, String priority, String category, String time,
                int finished, int idView){
        this.id = id;
        this.name = name;
        setTimeForTaskAsString(time);
        this.expirationDate = expirationDate;
        this.estimatedTimeHours = estimatedHours;
        this.estimatedTimeMinutes = estimatedMinutes;
        setPriorityAsString(priority);
        this.category = category;
        if (finished == 1) { this.finished = true; } else { this.finished = false; }
        this.idInView = idView;
        //annotations = new ArrayList<Annotation>();
    }
    //endregion

    //region Getters y Setters
    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getIdInView() {
        return idInView;
    }

    public void setIdInView(int idInView){
        this.idInView = idInView;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Calendar getExpirationDate() {
        return expirationDate;
    }

    public int getExpirationDay(){
        return expirationDate.get(Calendar.DAY_OF_MONTH);
    }

    public int getExpirationMonth(){
        return expirationDate.get(Calendar.MONTH);
    }

    public int getExpirationYear(){
        return expirationDate.get(Calendar.YEAR);
    }

    public void setExpirationDate(Calendar expirationDate) {
        this.expirationDate = expirationDate;
    }

    public float getEstimatedTimeHours() {
        return estimatedTimeHours;
    }

    public void setEstimatedTimeHours(float hours) {
        estimatedTimeHours = hours;
    }

    public float getEstimatedTimeMinutes() {
        return estimatedTimeMinutes;
    }

    public void setEstimatedTimeMinutes(float minutes){
        estimatedTimeMinutes = minutes;
    }

    public void setEstimatedTime(float hours, float minutes){
        estimatedTimeHours = hours;
        estimatedTimeMinutes = minutes;
    }

    public priorityTag getPriority() {
        return priority;
    }

    public String getPriorityAsString(){
        String returnString;
        switch (priority){
            case LOW:
                returnString = "Baja";
                break;
            case MID:
                returnString = "Media";
                break;
            case HIGH:
                returnString = "Alta";
                break;
            default:
                returnString = "";
                break;
        }
        return returnString;
    }

    public int getPriorityIndex(){
        switch(priority){
            case HIGH:
                return 0;
            case MID:
                return 1;
            case LOW:
                return 2;
            default:
                return 3;
        }
    }

    public void setPriority(priorityTag priority) {
        this.priority = priority;
    }

    public void setPriorityAsString(String priorityString){
        switch (priorityString){
            case "Alta":
                priority = priorityTag.HIGH;
                break;
            case "Media":
                priority = priorityTag.MID;
                break;
            case "Baja":
                priority = priorityTag.LOW;
                break;
            default:
                priority = priorityTag.NONE;
                break;
        }
    }

    public String getCategory() {
        return category;
    }

    public int getCategoryIndex(){
        switch(category){
            case "Estudios":
                return 0;
            case "Trabajo":
                return 1;
            case "Ejercicio":
                return 2;
            case "Personal":
                return 3;
            default:
                return 4;
        }
    }

    public void setCategory(String category){
        this.category = category;
    }

    public timeTag getTimeForTask() {
        return timeForTask;
    }

    public String getTimeForTaskAsString(){
        switch (timeForTask){
            case TODAY:
                return "Hoy";
            case TOMORROW:
                return "Mañana";
            case THIS_WEEK:
                return "Esta semana";
            case EXPIRED:
                return "Atrasada";
            default:
                return "Más tarde";
        }
    }

    public int getTimeForTaskIndex(){
        switch(timeForTask){
            case TODAY:
                return 0;
            case TOMORROW:
                return 1;
            case THIS_WEEK:
                return 2;
            case LATER:
                return 3;
            default:
                return 4;
        }
    }

    public void setTimeForTask(timeTag timeForTask) {
        this.timeForTask = timeForTask;
    }

    public void setTimeForTaskAsString(String timeString){
        switch(timeString){
            case "Hoy":
                timeForTask = timeTag.TODAY;
                break;
            case "Mañana":
                timeForTask = timeTag.TOMORROW;
                break;
            case "Esta semana":
                timeForTask = timeTag.THIS_WEEK;
                break;
            case "Atrasada":
                timeForTask = timeTag.EXPIRED;
                break;
            default:
                timeForTask = timeTag.LATER;
                break;
        }
    }

    public boolean isFinished(){
        return finished;
    }

    public void setFinished(boolean finished){
        this.finished = finished;
    }

    public void setAnnotations(ArrayList<Annotation> annotations){
        this.annotations = annotations;
    }
    public ArrayList<Annotation> getAnnotations(){
        return annotations;
    }

    public void addAnnotation(Annotation annotation){
        annotations.add(annotation);
    }
    //endregion

    //region subtasks and files management
    public void addSubTask(SubTask subTask){
        subTasks.add(subTask);
    }

    public void removeSubTask(Task subTask){
        subTasks.remove(subTask);
    }

    public ArrayList<SubTask> getSubTasks(){
        return subTasks;
    }

    public void addFile(int fileId){
        addedFilesIds.add(fileId);
    }

    public void removeFile(int fileId){
        addedFilesIds.remove(fileId);
    }

    public ArrayList<Integer> getAddedFilesIds(){
        return addedFilesIds;
    }

    public void finishSubTask(int index, boolean finished){
        SubTask subTaskToChange = getSubTaskById(index);
        subTaskToChange.setFinished(finished);
    }

    public SubTask getSubTaskById(int index){
        SubTask subTaskToReturn = new SubTask(id, "Not me", false);
        boolean found = false;
        int i = 0;
        while (!found && i < subTasks.size()){
            if (subTasks.get(i).getIdInView() == index){
                found = true;
                subTaskToReturn = subTasks.get(i);
            }
            i++;
        }
        return subTaskToReturn;
    }

    public void setSubTasks(ArrayList<SubTask> subTasksList){
        subTasks = subTasksList;
    }

    public ArrayList<SubTask> getSubTasksList(){
        return subTasks;
    }

    public int getSubTasksListSize(){
        return subTasks.size();
    }

    public int getAnnotationsListSize(){
        return annotations.size();
    }
    //endregion
}
