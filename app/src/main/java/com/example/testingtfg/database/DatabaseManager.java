package com.example.testingtfg.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.testingtfg.minigames.virtualPet.PurchasableItem;
import com.example.testingtfg.minigames.virtualPet.VirtualPet;
import com.example.testingtfg.taskOrganizer.Annotation;
import com.example.testingtfg.taskOrganizer.Event;
import com.example.testingtfg.taskOrganizer.SubTask;
import com.example.testingtfg.taskOrganizer.Task;

import java.util.ArrayList;
import java.util.Calendar;

public class DatabaseManager {

    //region Tareas
    //Recoge la información de la tarea y la guarda en un objeto ContentValues
    private static ContentValues setValues(Task task){
        ContentValues valuesFromTask = new ContentValues();
        int finished = task.isFinished() ? 1 : 0;
        valuesFromTask.put(AppDatabase.TASK_COLUMN_NAME, task.getName());
        valuesFromTask.put(AppDatabase.TASK_COLUMN_EXPIRATION_DAY, task.getExpirationDay());
        valuesFromTask.put(AppDatabase.TASK_COLUMN_EXPIRATION_MONTH, task.getExpirationMonth());
        valuesFromTask.put(AppDatabase.TASK_COLUMN_EXPIRATION_YEAR, task.getExpirationYear());
        valuesFromTask.put(AppDatabase.TASK_COLUMN_EST_HOURS, task.getEstimatedTimeHours());
        valuesFromTask.put(AppDatabase.TASK_COLUMN_EST_MINS, task.getEstimatedTimeMinutes());
        valuesFromTask.put(AppDatabase.TASK_COLUMN_PRIORITY, task.getPriorityAsString());
        valuesFromTask.put(AppDatabase.TASK_COLUMN_CATEGORY, task.getCategory());
        valuesFromTask.put(AppDatabase.TASK_COLUMN_TIMETAG, task.getTimeForTaskAsString());
        valuesFromTask.put(AppDatabase.TASK_COLUMN_FINISHED, finished);
        valuesFromTask.put(AppDatabase.TASK_COLUMN_IDVIEW, task.getIdInView());

        return valuesFromTask;
    }
    //Guarda la tarea en la base de datos
    public static int saveData(Task task, Context context){
        SQLiteDatabase dataBase = new AppDatabase(context).getWritableDatabase();
        ContentValues valuesFromTask = setValues(task);
        int id = (int) dataBase.insert(AppDatabase.TASK_TABLE_NAME, null, valuesFromTask);
        return id;
    }
    //Localiza una tarea por id y reescribe sus valores
    public static void editData(Task task, Context context){
        SQLiteDatabase dataBase = new AppDatabase(context).getWritableDatabase();
        String id = "" + task.getId() + "";
        ContentValues newValues = setValues(task);

        String whereClause = AppDatabase.TASK_COLUMN_ID + " == ?";
        String[] whereArgs = new String[]{
                id
        };

        dataBase.update(AppDatabase.TASK_TABLE_NAME, newValues, whereClause, whereArgs);
    }
    //Localiza una tarea por id y la elimina de la tabla
    public static void deleteData(Task task, Context context){
        SQLiteDatabase dataBase = new AppDatabase(context).getWritableDatabase();
        String id = "" + task.getId() + "";
        String whereClause = AppDatabase.TASK_COLUMN_ID + " == ?";
        String[] whereArgs = new String[]{
                id
        };

        dataBase.delete(AppDatabase.TASK_TABLE_NAME, whereClause, whereArgs);
    }
    //Recoge todas las tareas de la tabla y las devuelve en una lista
    public static ArrayList<Task> readData(Context context){
        ArrayList tasksList = new ArrayList<Task>();

        SQLiteDatabase database = new AppDatabase(context).getReadableDatabase();

        String[] projection = {
                AppDatabase.TASK_COLUMN_ID,
                AppDatabase.TASK_COLUMN_NAME,
                AppDatabase.TASK_COLUMN_EXPIRATION_DAY,
                AppDatabase.TASK_COLUMN_EXPIRATION_MONTH,
                AppDatabase.TASK_COLUMN_EXPIRATION_YEAR,
                AppDatabase.TASK_COLUMN_EST_HOURS,
                AppDatabase.TASK_COLUMN_EST_MINS,
                AppDatabase.TASK_COLUMN_PRIORITY,
                AppDatabase.TASK_COLUMN_CATEGORY,
                AppDatabase.TASK_COLUMN_TIMETAG,
                AppDatabase.TASK_COLUMN_FINISHED,
                AppDatabase.TASK_COLUMN_IDVIEW,
        };

        Cursor cursor = database.query(
                AppDatabase.TASK_TABLE_NAME,                           //Nombre
                projection,                                             //Columnas a devolver
                null,                                          //Where
                null,
                null,
                null,
                null
        );

        for (int i = 0; i < cursor.getCount(); i++){
            cursor.moveToNext();

            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            int expDay = cursor.getInt(2);
            int expMonth = cursor.getInt(3);
            int expYear = cursor.getInt(4);
            float estHours = cursor.getFloat(5);
            float estMins = cursor.getFloat(6);
            String priority= cursor.getString(7);
            String category = cursor.getString(8);
            String timeTag = cursor.getString(9);
            int finished = cursor.getInt(10);
            int idView = cursor.getInt(11);

            Calendar date = Calendar.getInstance();
            date.set(expYear, expMonth, expDay);
            Task task = new Task(id, name, date, estHours, estMins, priority, category, timeTag,
                    finished, idView);
            tasksList.add(task);
        }

        return tasksList;
    }
    //endregion
    //region Subtareas
    //Recoge la información de la subtarea y la guarda en un objeto ContentValues
    private static ContentValues setSubValues(SubTask subTask){
        ContentValues valuesFromSubTask = new ContentValues();

        int finished = subTask.isFinished() ? 1 : 0;

        valuesFromSubTask.put(AppDatabase.SUBTASK_COLUMN_NAME, subTask.getName());
        valuesFromSubTask.put(AppDatabase.SUBTASK_COLUMN_FATHER_TASK, subTask.getFatherId());
        valuesFromSubTask.put(AppDatabase.SUBTASK_COLUMN_FINISHED, finished);
        valuesFromSubTask.put(AppDatabase.SUBTASK_COLUMN_IDVIEW, subTask.getIdInView());

        return valuesFromSubTask;
    }
    //Guarda la subtarea en la base de datos
    public static int saveDataSub(SubTask subTask, Context context){
        SQLiteDatabase dataBase = new AppDatabase(context).getWritableDatabase();
        ContentValues valuesFromSubTask = setSubValues(subTask);
        int id = (int) dataBase.insert(AppDatabase.SUBTASK_TABLE_NAME, null, valuesFromSubTask);
        return id;
    }
    //Localiza una subtarea por id y reescribe sus valores
    public static void editDataSub(SubTask subTask, Context context){
        SQLiteDatabase dataBase = new AppDatabase(context).getWritableDatabase();
        String id = "" + subTask.getId() + "";
        ContentValues newValues = setSubValues(subTask);

        String whereClause = AppDatabase.SUBTASK_COLUMN_ID + " == ?";
        String[] whereArgs = new String[]{
                id
        };

        dataBase.update(AppDatabase.SUBTASK_TABLE_NAME, newValues, whereClause, whereArgs);
    }
    //Localiza una subtarea por id y la elimina de la tabla
    public static void deleteDataSub(SubTask subTask, Context context){
        SQLiteDatabase dataBase = new AppDatabase(context).getWritableDatabase();
        String id = "" + subTask.getId() + "";
        String whereClause = AppDatabase.SUBTASK_COLUMN_ID + " == ?";
        String[] whereArgs = new String[]{
                id
        };

        dataBase.delete(AppDatabase.SUBTASK_TABLE_NAME, whereClause, whereArgs);
    }
    //Recoge todas las subtareas correspondientes a una tarea concreta y las devuelve en una lista
    public static ArrayList<SubTask> readDataSub(Task task, Context context){
        ArrayList subTaskList = new ArrayList<SubTask>();

        SQLiteDatabase database = new AppDatabase(context).getReadableDatabase();

        String[] projection = {
                AppDatabase.SUBTASK_COLUMN_ID,
                AppDatabase.SUBTASK_COLUMN_FATHER_TASK,
                AppDatabase.SUBTASK_COLUMN_NAME,
                AppDatabase.SUBTASK_COLUMN_FINISHED,
                AppDatabase.SUBTASK_COLUMN_IDVIEW,
        };

        String whereClause = AppDatabase.SUBTASK_COLUMN_FATHER_TASK + " == ?";
        String[] whereArgs = new String[]{
                ""+task.getId()+""
        };

        Cursor cursor = database.query(
                AppDatabase.SUBTASK_TABLE_NAME,                           //Nombre
                projection,                                             //Columnas a devolver
                whereClause,                                          //Where
                whereArgs,
                null,
                null,
                null
        );

        for (int i = 0; i < cursor.getCount(); i++){
            cursor.moveToNext();

            int id = cursor.getInt(0);
            int fatherId = cursor.getInt(1);
            String name = cursor.getString(2);
            int finished = cursor.getInt(3);
            int viewId = cursor.getInt(4);

            SubTask subTask = new SubTask(id, fatherId, name, finished, viewId);
            subTaskList.add(subTask);
        }
        return subTaskList;
    }
    //endregion
    //region Anotaciones
    //Recoge la información de la anotación y la guarda en un objeto ContentValues
    private static ContentValues setAnnotationValues(Annotation annotation){
        ContentValues valuesFromSubTask = new ContentValues();

        valuesFromSubTask.put(AppDatabase.ANNOTATIONS_COLUMN_CONTENT, annotation.getContent());
        valuesFromSubTask.put(AppDatabase.ANNOTATIONS_COLUMN_FATHER_TASK, annotation.getFatherId());
        valuesFromSubTask.put(AppDatabase.ANNOTATIONS_COLUMN_IDVIEW, annotation.getIdInView());
        return valuesFromSubTask;
    }
    //Guarda la anotación en la base de datos
    public static int saveDataAnnotations(Annotation annotation, Context context){
        SQLiteDatabase dataBase = new AppDatabase(context).getWritableDatabase();
        ContentValues valuesFromAnnotation = setAnnotationValues(annotation);
        int id = (int) dataBase.insert(AppDatabase.ANNOTATIONS_TABLE_NAME, null, valuesFromAnnotation);
        return id;
    }
    //Localiza una anotación por id y reescribe sus valores
    public static void editDataAnnotation(Annotation annotation, Context context){
        SQLiteDatabase dataBase = new AppDatabase(context).getWritableDatabase();
        String id = "" + annotation.getId() + "";
        ContentValues newValues = setAnnotationValues(annotation);

        String whereClause = AppDatabase.ANNOTATIONS_COLUMN_ID + " == ?";
        String[] whereArgs = new String[]{
                id
        };

        dataBase.update(AppDatabase.ANNOTATIONS_TABLE_NAME, newValues, whereClause, whereArgs);
    }
    //Localiza una anotación por id y la elimina de la tabla
    public static void deleteDataAnnotation(Annotation annotation, Context context){
        SQLiteDatabase dataBase = new AppDatabase(context).getWritableDatabase();
        String id = "" + annotation.getId() + "";
        String whereClause = AppDatabase.ANNOTATIONS_COLUMN_ID + " == ?";
        String[] whereArgs = new String[]{
                id
        };

        dataBase.delete(AppDatabase.ANNOTATIONS_TABLE_NAME, whereClause, whereArgs);
    }
    //Recoge todas las anotaciones correspondientes a una tarea concreta y las devuelve en una lista
    public static ArrayList<Annotation> readDataAnnotation(Task task, Context context){
        ArrayList annotationList = new ArrayList<Annotation>();

        SQLiteDatabase database = new AppDatabase(context).getReadableDatabase();

        String[] projection = {
                AppDatabase.ANNOTATIONS_COLUMN_ID,
                AppDatabase.ANNOTATIONS_COLUMN_FATHER_TASK,
                AppDatabase.ANNOTATIONS_COLUMN_CONTENT,
                AppDatabase.ANNOTATIONS_COLUMN_IDVIEW,
        };

        String whereClause = AppDatabase.ANNOTATIONS_COLUMN_FATHER_TASK + " == ?";
        String[] whereArgs = new String[]{
                ""+task.getId()+""
        };

        Cursor cursor = database.query(
                AppDatabase.ANNOTATIONS_TABLE_NAME,                           //Nombre
                projection,                                             //Columnas a devolver
                whereClause,                                          //Where
                whereArgs,
                null,
                null,
                null
        );

        for (int i = 0; i < cursor.getCount(); i++){
            cursor.moveToNext();

            int id = cursor.getInt(0);
            int fatherId = cursor.getInt(1);
            String content = cursor.getString(2);
            int viewId = cursor.getInt(3);

            Annotation annotation = new Annotation(id, fatherId, content, viewId);
            annotationList.add(annotation);
        }
        return annotationList;
    }
    //endregion
    //region Calendario/Eventos
    //Recoge la información del evento y la guarda en un objeto ContentValues
    private static ContentValues setValuesEvent(Event event){
        ContentValues valuesFromEvent = new ContentValues();

        valuesFromEvent.put(AppDatabase.EVENT_COLUMN_NAME, event.getTitle());
        valuesFromEvent.put(AppDatabase.EVENT_COLUMN_EXPIRATION_DAY, event.getDate().get(Calendar.DAY_OF_MONTH));
        valuesFromEvent.put(AppDatabase.EVENT_COLUMN_EXPIRATION_MONTH, event.getDate().get(Calendar.MONTH));
        valuesFromEvent.put(AppDatabase.EVENT_COLUMN_EXPIRATION_YEAR, event.getDate().get(Calendar.YEAR));

        return valuesFromEvent;
    }
    //Guarda el evento en la base de datos
    public static int saveDataEvent(Event event, Context context){
        SQLiteDatabase dataBase = new AppDatabase(context).getWritableDatabase();
        ContentValues valuesFromEvent = setValuesEvent(event);
        int id = (int) dataBase.insert(AppDatabase.EVENT_TABLE_NAME, null, valuesFromEvent);
        return id;
    }
    //Localiza un evento por id y reescribe sus valores
    public static void editDataEvent(Event event, Context context){
        SQLiteDatabase dataBase = new AppDatabase(context).getWritableDatabase();
        String id = "" + event.getId() + "";
        ContentValues newValues = setValuesEvent(event);

        String whereClause = AppDatabase.EVENT_COLUMN_ID + " == ?";
        String[] whereArgs = new String[]{
                id
        };

        dataBase.update(AppDatabase.EVENT_TABLE_NAME, newValues, whereClause, whereArgs);
    }
    //Localiza un evento por id y lo elimina de la tabla
    public static void deleteDataEvent(Event event, Context context){
        SQLiteDatabase dataBase = new AppDatabase(context).getWritableDatabase();
        String id = "" + event.getId() + "";
        String whereClause = AppDatabase.EVENT_COLUMN_ID + " == ?";
        String[] whereArgs = new String[]{
                id
        };

        dataBase.delete(AppDatabase.EVENT_TABLE_NAME, whereClause, whereArgs);
    }
    //Recoge todos los eventos y los devuelve en una lista
    public static ArrayList<Event> readDataEvent(Context context){
        ArrayList eventList = new ArrayList<Event>();

        SQLiteDatabase database = new AppDatabase(context).getReadableDatabase();

        String[] projection = {
                AppDatabase.EVENT_COLUMN_ID,
                AppDatabase.EVENT_COLUMN_NAME,
                AppDatabase.EVENT_COLUMN_EXPIRATION_DAY,
                AppDatabase.EVENT_COLUMN_EXPIRATION_MONTH,
                AppDatabase.EVENT_COLUMN_EXPIRATION_YEAR,
        };



        Cursor cursor = database.query(
                AppDatabase.EVENT_TABLE_NAME,                           //Nombre
                projection,                                             //Columnas a devolver
                null,                                          //Where
                null,
                null,
                null,
                null
        );

        for (int i = 0; i < cursor.getCount(); i++){
            cursor.moveToNext();

            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            int day = cursor.getInt(2);
            int month = cursor.getInt(3);
            int year = cursor.getInt(4);
            Calendar date = Calendar.getInstance();
            date.set(year, month, day);
            Event event = new Event(name, date);
            event.setId(id);
            eventList.add(event);
        }

        return eventList;
    }
    //endregion
    //region Mercado
    //Recoge la información del item y la guarda en un objeto ContentValues
    private static ContentValues setValuesMarket(PurchasableItem item){
        ContentValues valuesFromItem = new ContentValues();
        int available = item.isAvailable() ? 1 : 0;
        valuesFromItem.put(AppDatabase.MARKET_COLUMN_NAME, item.getName());
        valuesFromItem.put(AppDatabase.MARKET_COLUMN_DESCRIPTION, item.getDescription());
        valuesFromItem.put(AppDatabase.MARKET_COLUMN_COST, item.getCost());
        valuesFromItem.put(AppDatabase.MARKET_COLUMN_AVAILABLE, available);
        valuesFromItem.put(AppDatabase.MARKET_COLUMN_CLASS, item.getItemClassAsInt());

        return valuesFromItem;
    }
    //Guarda el item en la base de datos
    public static int saveDataMarket(PurchasableItem item, Context context){
        SQLiteDatabase dataBase = new AppDatabase(context).getWritableDatabase();
        ContentValues valuesFromItem = setValuesMarket(item);
        int id = (int) dataBase.insert(AppDatabase.MARKET_TABLE_NAME, null, valuesFromItem);
        return id;
    }
    //Localiza un item por id y reescribe sus valores
    public static void editDataMarket(PurchasableItem item, Context context){
        SQLiteDatabase dataBase = new AppDatabase(context).getWritableDatabase();
        String id = "" + item.getId() + "";
        ContentValues newValues = setValuesMarket(item);

        String whereClause = AppDatabase.MARKET_COLUMN_ID + " == ?";
        String[] whereArgs = new String[]{
                id
        };

        dataBase.update(AppDatabase.MARKET_TABLE_NAME, newValues, whereClause, whereArgs);
    }
    //Localiza un evento por id y lo elimina de la tabla
    public static void deleteDataMarket(PurchasableItem item, Context context){
        SQLiteDatabase dataBase = new AppDatabase(context).getWritableDatabase();
        String id = "" + item.getId() + "";
        String whereClause = AppDatabase.MARKET_COLUMN_ID + " == ?";
        String[] whereArgs = new String[]{
                id
        };

        dataBase.delete(AppDatabase.MARKET_TABLE_NAME, whereClause, whereArgs);
    }
    //Recoge todos los items y los devuelve en una lista
    public static ArrayList<PurchasableItem> readDataMarket(Context context){
        ArrayList itemsList = new ArrayList<PurchasableItem>();

        SQLiteDatabase database = new AppDatabase(context).getReadableDatabase();

        String[] projection = {
                AppDatabase.MARKET_COLUMN_ID,
                AppDatabase.MARKET_COLUMN_NAME,
                AppDatabase.MARKET_COLUMN_DESCRIPTION,
                AppDatabase.MARKET_COLUMN_COST,
                AppDatabase.MARKET_COLUMN_AVAILABLE,
                AppDatabase.MARKET_COLUMN_CLASS,
        };

        Cursor cursor = database.query(
                AppDatabase.MARKET_TABLE_NAME,                           //Nombre
                projection,                                             //Columnas a devolver
                null,                                          //Where
                null,
                null,
                null,
                null
        );

        for (int i = 0; i < cursor.getCount(); i++){
            cursor.moveToNext();

            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String desc = cursor.getString(2);
            int cost = cursor.getInt(3);
            int available = cursor.getInt(4);
            boolean isAvailable = false;
            if (available == 1){ isAvailable = true;}
            int classIndex = cursor.getInt(5);
            PurchasableItem item = new PurchasableItem(classIndex, name, desc, cost, isAvailable);
            item.setId(id);
            itemsList.add(item);
        }

        return itemsList;
    }
    //endregion
    //region Mascota Virtual
    //Recoge la información de la mascota y la guarda en un objeto ContentValues
    private static ContentValues setValuesVirtualPet(VirtualPet virtualPet){
        ContentValues valuesFromVP = new ContentValues();
        valuesFromVP.put(AppDatabase.VIRTUALPET_COLUMN_NAME, virtualPet.getPetName());
        valuesFromVP.put(AppDatabase.VIRTUALPET_COLUMN_CLASS, virtualPet.getClassIndex());
        valuesFromVP.put(AppDatabase.VIRTUALPET_COLUMN_EVOLUTION, virtualPet.getEvolutionIndex());
        valuesFromVP.put(AppDatabase.VIRTUALPET_COLUMN_EXPERIENCE, virtualPet.getExperience());
        valuesFromVP.put(AppDatabase.VIRTUALPET_COLUMN_HUNGER, virtualPet.getHunger());
        valuesFromVP.put(AppDatabase.VIRTUALPET_COLUMN_DIRT, virtualPet.getDirt());
        valuesFromVP.put(AppDatabase.VIRTUALPET_COLUMN_EVOLUTION_READY, virtualPet.isReadyForEvolve());
        valuesFromVP.put(AppDatabase.VIRTUALPET_COLUMN_EQUIPED_INDEX, virtualPet.getIndexEquippedItem());

        return valuesFromVP;
    }
    //Guarda la mascota en la base de datos
    public static int saveDataVirtualPet(VirtualPet virtualPet, Context context){
        SQLiteDatabase dataBase = new AppDatabase(context).getWritableDatabase();
        ContentValues valuesFromVP = setValuesVirtualPet(virtualPet);
        int id = (int) dataBase.insert(AppDatabase.VIRTUALPET_TABLE_NAME, null, valuesFromVP);
        return id;
    }
    //Localiza una mascota por id y reescribe sus valores
    public static void editDataVirtualPet(VirtualPet virtualPet, Context context){
        SQLiteDatabase dataBase = new AppDatabase(context).getWritableDatabase();
        String id = "" + virtualPet.getId() + "";
        ContentValues newValues = setValuesVirtualPet(virtualPet);

        String whereClause = AppDatabase.VIRTUALPET_COLUMN_ID + " == ?";
        String[] whereArgs = new String[]{
                id
        };

        dataBase.update(AppDatabase.VIRTUALPET_TABLE_NAME, newValues, whereClause, whereArgs);
    }
    //Localiza una mascota por id y la elimina de la tabla
    public static void deleteDataVirtualPet(VirtualPet virtualPet, Context context){
        SQLiteDatabase dataBase = new AppDatabase(context).getWritableDatabase();
        String id = "" + virtualPet.getId() + "";
        String whereClause = AppDatabase.VIRTUALPET_COLUMN_ID + " == ?";
        String[] whereArgs = new String[]{
                id
        };

        dataBase.delete(AppDatabase.VIRTUALPET_TABLE_NAME, whereClause, whereArgs);
    }
    //Recoge todas las mascotas y los devuelve en una lista
    public static ArrayList<VirtualPet> readDataVirtualPet(Context context){
        ArrayList virtualPetList = new ArrayList<VirtualPet>();

        SQLiteDatabase database = new AppDatabase(context).getReadableDatabase();

        String[] projection = {
                AppDatabase.VIRTUALPET_COLUMN_ID,
                AppDatabase.VIRTUALPET_COLUMN_NAME,
                AppDatabase.VIRTUALPET_COLUMN_CLASS,
                AppDatabase.VIRTUALPET_COLUMN_EVOLUTION,
                AppDatabase.VIRTUALPET_COLUMN_EXPERIENCE,
                AppDatabase.VIRTUALPET_COLUMN_HUNGER,
                AppDatabase.VIRTUALPET_COLUMN_DIRT,
                AppDatabase.VIRTUALPET_COLUMN_EVOLUTION_READY,
                AppDatabase.VIRTUALPET_COLUMN_EQUIPED_INDEX,
        };

        Cursor cursor = database.query(
                AppDatabase.VIRTUALPET_TABLE_NAME,                           //Nombre
                projection,                                             //Columnas a devolver
                null,                                          //Where
                null,
                null,
                null,
                null
        );

        for (int i = 0; i < cursor.getCount(); i++){
            cursor.moveToNext();

            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            int classIndex = cursor.getInt(2);
            int evoIndex = cursor.getInt(3);
            int exp = cursor.getInt(4);
            int hunger = cursor.getInt(5);
            int dirt = cursor.getInt(6);
            int readyInt = cursor.getInt(7);
            boolean isReady = false;
            if (readyInt == 1){isReady = true;}
            int equiped = cursor.getInt(8);
            VirtualPet virtualPet = new VirtualPet(classIndex, name, exp, evoIndex, hunger,
                    dirt, isReady, equiped);
            virtualPet.setId(id);
            virtualPet.setIdInList(virtualPetList.size());
            virtualPetList.add(virtualPet);
        }

        return virtualPetList;
    }
    //endregion
    //region Debug
    //Borra el contenido de todas las tablas
    public static void clearTables(Context context){
        SQLiteDatabase dataBase = new AppDatabase(context).getWritableDatabase();
        dataBase.execSQL("DELETE FROM " + AppDatabase.TASK_TABLE_NAME);
        dataBase.execSQL("DELETE FROM " + AppDatabase.SUBTASK_TABLE_NAME);
        dataBase.execSQL("DELETE FROM " + AppDatabase.ANNOTATIONS_TABLE_NAME);
        dataBase.execSQL("DELETE FROM " + AppDatabase.EVENT_TABLE_NAME);
        dataBase.execSQL("DELETE FROM " + AppDatabase.MARKET_TABLE_NAME);
        dataBase.execSQL("DELETE FROM " + AppDatabase.VIRTUALPET_TABLE_NAME);
    }
    //Elimina el contenido de la tabla seleccionada
    public static void clearTargetTable(Context context, int table){
        SQLiteDatabase dataBase = new AppDatabase(context).getWritableDatabase();
        switch (table){
            case 0:
                dataBase.execSQL("DELETE FROM " + AppDatabase.TASK_TABLE_NAME);
                break;
            case 1:
                dataBase.execSQL("DELETE FROM " + AppDatabase.SUBTASK_TABLE_NAME);
                break;
            case 2:
                dataBase.execSQL("DELETE FROM " + AppDatabase.MARKET_TABLE_NAME);
                break;
            case 3:
                dataBase.execSQL("DELETE FROM " + AppDatabase.VIRTUALPET_TABLE_NAME);
                break;
            case 4:
                dataBase.execSQL("DELETE FROM " + AppDatabase.EVENT_TABLE_NAME);
                break;
            default:
                break;
        }
    }
    //Borra todas las tablas
    public static void removeDatabase(Context context){
        SQLiteDatabase dataBase = new AppDatabase(context).getWritableDatabase();
        dataBase.execSQL("DROP TABLE IF EXISTS " + AppDatabase.TASK_TABLE_NAME);
        dataBase.execSQL("DROP TABLE IF EXISTS " + AppDatabase.SUBTASK_TABLE_NAME);
        dataBase.execSQL("DROP TABLE IF EXISTS " + AppDatabase.ANNOTATIONS_TABLE_NAME);
        dataBase.execSQL("DROP TABLE IF EXISTS " + AppDatabase.EVENT_TABLE_NAME);
        dataBase.execSQL("DROP TABLE IF EXISTS " + AppDatabase.MARKET_TABLE_NAME);
        dataBase.execSQL("DROP TABLE IF EXISTS " + AppDatabase.VIRTUALPET_TABLE_NAME);
    }
    //endregion

}
