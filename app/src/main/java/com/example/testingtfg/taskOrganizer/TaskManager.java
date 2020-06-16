package com.example.testingtfg.taskOrganizer;

import android.app.Activity;

import com.example.testingtfg.MainActivity;
import com.example.testingtfg.database.DatabaseManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

/*Clase que gestiona qué se muestra, añade, modifica o elimina de la pantalla de tareas*/
public class TaskManager {

    //region Parámetros
    private ArrayList<Task> tasksList;
    private Activity activity;
    private Calendar stablishedDate;
    //endregion

    //Constructor
    public TaskManager(Activity a){
        activity = a;
        tasksList = DatabaseManager.readData(activity);//Lee la base de datos
        stablishedDate = Calendar.getInstance();
        updateTasksTimeTag();//Actualiza los TimeTag en función a la fecha actual
    }

    /*Crea un nuevo objeto de Task, lo añade a la base de datos, le establece un id y le
    asocia subtareas si fuese necesario*/
    public void createNewTask(String submittedName, int index){
        Task newTask = new Task(submittedName, stablishedDate, 0, 0, "Baja",
                "Estudios", "Hoy", 0, index);
        int id = DatabaseManager.saveData(newTask, activity); //When adding a new Task, it adds it to database
        newTask.setId(id);
        retrieveSubTaskList(newTask);
        retrieveAnnotationLists(newTask);
        tasksList.add(newTask);
    }

    //Crea un nuevo objeto SubTask, lo añade a la base de datos y establece un id único
    public void createNewSubTask(Task fatherTask, String submittedName, int idView){
        SubTask subTask = new SubTask(fatherTask.getId(), submittedName, false);
        subTask.setIdInView(idView);
        int id = DatabaseManager.saveDataSub(subTask, activity);
        subTask.setId(id);
        fatherTask.addSubTask(subTask);
    }

    //Crea un nuevo objeto Annotation, lo añade a la base de datos y establece un id único
    public void createNewAnnotation(Task fatherTask, String submittedContent, int idView){
        Annotation annotation = new Annotation(fatherTask.getId(), submittedContent);
        annotation.setIdInView(idView);
        int id = DatabaseManager.saveDataAnnotations(annotation, activity);
        annotation.setId(id);
        fatherTask.addAnnotation(annotation);
    }

    //Obtiene la lista de subtareas para una tarea en concreto
    public void retrieveSubTaskList(Task t){
        ArrayList<SubTask> subTasksList = DatabaseManager.readDataSub(t,activity);
        t.setSubTasks(subTasksList);
    }
    //Obtiene la lista de anotaciones para una tarea en concreto
    public void retrieveAnnotationLists(Task t){
        ArrayList<Annotation> annotationsList = DatabaseManager.readDataAnnotation(t,activity);
        t.setAnnotations(annotationsList);
    }
    //Edita una tarea en la base de datos
    public void editTask(Task taskToEdit){
        DatabaseManager.editData(taskToEdit, activity);
    }
    //Edita una subtarea en la base de datos
    public void editSubTask(SubTask subTaskToEdit){
        DatabaseManager.editDataSub(subTaskToEdit, activity);
    }
    //Elimina una subtarea de la base de datos
    public void removeSubTask(SubTask subTaskToRemove){
        DatabaseManager.deleteDataSub(subTaskToRemove, activity);
    }
    //Elimina las tareas finalizadas de la lista de tareas y de la base de datos
    public void clearFinishedTasks(){
        int tasksCleared = 0;
        Iterator it = tasksList.iterator();
        while (it.hasNext()){
            Task t = (Task) it.next();
            if (t.isFinished()){
                it.remove();
                DatabaseManager.deleteData(t, activity);
                tasksCleared++;
            }
        }
        ((MainActivity)activity).getVirtualPetManager().increasePlayPoints(tasksCleared);
        ((MainActivity)activity).getStatsTracker().finishTasks(tasksCleared);
    }

    //Elimina las subtareas finalizadas de la lista de la tarea padre y de la base de datos
    public void clearFinishedSubTasks(Task task){
        Iterator it = task.getSubTasksList().iterator();
        while (it.hasNext()){
            SubTask st = (SubTask) it.next();
            if (st.isFinished()){
                it.remove();
                DatabaseManager.deleteDataSub(st, activity);
            }
        }
    }

    //Actualiza el TimeTag en función de la fecha de finalización y la fecha actual
    public void updateTasksTimeTag(){
        Iterator it = tasksList.iterator();
        while (it.hasNext()){
            Task t = (Task) it.next();
            if (t.getExpirationYear() < Calendar.getInstance().get(Calendar.YEAR)) {
                t.setTimeForTask(Task.timeTag.EXPIRED);
            } else if (t.getExpirationYear() == Calendar.getInstance().get(Calendar.YEAR)){
                if (t.getExpirationMonth() < Calendar.getInstance().get(Calendar.MONTH)){
                    t.setTimeForTask(Task.timeTag.EXPIRED);
                } else if (t.getExpirationMonth() == Calendar.getInstance().get(Calendar.MONTH)) {
                    if (t.getExpirationDay() < Calendar.getInstance().get(Calendar.DAY_OF_MONTH)) {
                        t.setTimeForTask(Task.timeTag.EXPIRED);
                    } else if (t.getExpirationDay() == Calendar.getInstance().get(Calendar.DAY_OF_MONTH)){
                        t.setTimeForTask(Task.timeTag.TODAY);
                    } else if (t.getExpirationDay() == Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + 1){
                        t.setTimeForTask(Task.timeTag.TOMORROW);
                    } else if (t.getExpirationDay() >= Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + 2 &&
                            t.getExpirationDay() <= Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + 6){
                        t.setTimeForTask(Task.timeTag.THIS_WEEK);
                    } else {
                        t.setTimeForTask(Task.timeTag.LATER);
                    }
                } else {
                    t.setTimeForTask(Task.timeTag.LATER);
                }
            } else {
                t.setTimeForTask(Task.timeTag.LATER);
            }
        }
    }

    //Recoge una tarea de la lista asociada a un id de View pasado por parámetro
    public Task getTaskById(int id){
        Task taskToReturn = new Task("Not me");
        boolean found = false;
        int i = 0;
        while (!found || i < tasksList.size()){
            if (tasksList.get(i).getIdInView() == id){
                found = true;
                taskToReturn = tasksList.get(i);
            }
            i++;
        }
        return taskToReturn;
    }

    //Devuelve el tamaño de la lista
    public int getTasksListSize(){
        return tasksList.size();
    }

    //Devuelve la lista completa de tareas
    public ArrayList<Task> getTasksList(){
        return tasksList;
    }

    public void stablishDate(int year, int month, int day){
        stablishedDate.set(year, month, day);
    }

    public String getDate(){
        return "D:" + stablishedDate.get(Calendar.DAY_OF_MONTH)
                + " M:" + (stablishedDate.get(Calendar.MONTH) +1)
                + " Y:" + stablishedDate.get(Calendar.YEAR);
    }

    //Reinicia la fecha
    public void resetDate(){
        stablishedDate = Calendar.getInstance();
    }

    //Devuelve el número de tareas de un TimeTag concreto
    public int getNumberOfTasks(Task.timeTag category){
        int count = 0;
        for (Task t : tasksList){
            if (t.getTimeForTask() == category){
                count++;
            }
        }
        return count;
    }
}
