package com.example.testingtfg.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/*Clase encargada de inicializar, crear y actualizar la base de datos*/
public class AppDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 7;//Versión de la BDD para actualizar cuando hay cambios

    public static final String DB_NAME = "task_database";
    //region Tabla de Tareas
    public static final String TASK_TABLE_NAME = "task";
    public static final String TASK_COLUMN_ID = "_id";
    public static final String TASK_COLUMN_NAME = "name";
    public static final String TASK_COLUMN_EXPIRATION_DAY = "exp_day";
    public static final String TASK_COLUMN_EXPIRATION_MONTH = "exp_month";
    public static final String TASK_COLUMN_EXPIRATION_YEAR = "exp_year";
    public static final String TASK_COLUMN_EST_HOURS = "est_hours";
    public static final String TASK_COLUMN_EST_MINS = "est_mins";
    public static final String TASK_COLUMN_PRIORITY = "priority";
    public static final String TASK_COLUMN_CATEGORY = "category";
    public static final String TASK_COLUMN_TIMETAG = "time_tag";
    public static final String TASK_COLUMN_FINISHED = "finished";
    public static final String TASK_COLUMN_ANNOTATIONS = "annotations";
    public static final String TASK_COLUMN_IDVIEW= "id_view";
    //endregion
    //region Tabla de Subtareas
    public static final String SUBTASK_TABLE_NAME = "subtask";
    public static final String SUBTASK_COLUMN_ID = "_id";
    public static final String SUBTASK_COLUMN_FATHER_TASK = "father_id";
    public static final String SUBTASK_COLUMN_NAME = "name";
    public static final String SUBTASK_COLUMN_FINISHED = "finished";
    public static final String SUBTASK_COLUMN_IDVIEW = "id_view";
    //endregion
    //region Tabla de Anotaciones
    public static final String ANNOTATIONS_TABLE_NAME = "annotation";
    public static final String ANNOTATIONS_COLUMN_ID = "_id";
    public static final String ANNOTATIONS_COLUMN_FATHER_TASK = "father_id";
    public static final String ANNOTATIONS_COLUMN_CONTENT = "content";
    public static final String ANNOTATIONS_COLUMN_IDVIEW = "id_view";
    //endregion
    //region Tabla de Calendario
    public static final String EVENT_TABLE_NAME = "event";
    public static final String EVENT_COLUMN_ID = "_id";
    public static final String EVENT_COLUMN_NAME = "name";
    public static final String EVENT_COLUMN_EXPIRATION_DAY = "exp_day";
    public static final String EVENT_COLUMN_EXPIRATION_MONTH = "exp_month";
    public static final String EVENT_COLUMN_EXPIRATION_YEAR = "exp_year";
    //endregion
    //region Tabla de Mercado
    public static final String MARKET_TABLE_NAME = "market";
    public static final String MARKET_COLUMN_ID = "_id";
    public static final String MARKET_COLUMN_NAME = "name";
    public static final String MARKET_COLUMN_DESCRIPTION = "description";
    public static final String MARKET_COLUMN_COST = "cost";
    public static final String MARKET_COLUMN_AVAILABLE = "available";
    public static final String MARKET_COLUMN_CLASS = "class";
    //endregion
    //region Tabla de Mascota Virtual
    public static final String VIRTUALPET_TABLE_NAME = "virtual_pet";
    public static final String VIRTUALPET_COLUMN_ID = "_id";
    public static final String VIRTUALPET_COLUMN_NAME = "name";
    public static final String VIRTUALPET_COLUMN_CLASS = "class";
    public static final String VIRTUALPET_COLUMN_EVOLUTION = "evolution";
    public static final String VIRTUALPET_COLUMN_EXPERIENCE = "experience";
    public static final String VIRTUALPET_COLUMN_HUNGER = "hunger";
    public static final String VIRTUALPET_COLUMN_DIRT = "dirt";
    public static final String VIRTUALPET_COLUMN_EVOLUTION_READY = "ready_evo";
    public static final String VIRTUALPET_COLUMN_EQUIPED_INDEX = "equipment";
    //endregion

    //Constructor
    public AppDatabase(@Nullable Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
    }

    //Creación de la base de datos
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Creación de la tabla de tareas
        db.execSQL("CREATE TABLE " + TASK_TABLE_NAME + " (" +
                TASK_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TASK_COLUMN_NAME + " TEXT, " +
                TASK_COLUMN_EXPIRATION_DAY + " INTEGER, " +
                TASK_COLUMN_EXPIRATION_MONTH + " INTEGER, " +
                TASK_COLUMN_EXPIRATION_YEAR + " INTEGER, " +
                TASK_COLUMN_EST_HOURS + " REAL, " +
                TASK_COLUMN_EST_MINS + " REAL, " +
                TASK_COLUMN_PRIORITY + " TEXT, " +
                TASK_COLUMN_CATEGORY + " TEXT, " +
                TASK_COLUMN_TIMETAG + " TEXT, " +
                TASK_COLUMN_FINISHED + " INTEGER, " +
                TASK_COLUMN_ANNOTATIONS + " TEXT, " +
                TASK_COLUMN_IDVIEW + " TEXT" +
                ")");

        //Creación de la tabla de subtareas
        db.execSQL("CREATE TABLE " + SUBTASK_TABLE_NAME + " (" +
                SUBTASK_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                SUBTASK_COLUMN_FATHER_TASK + " INTEGER, " +
                SUBTASK_COLUMN_NAME + " TEXT, " +
                SUBTASK_COLUMN_FINISHED + " INTEGER, " +
                SUBTASK_COLUMN_IDVIEW + " INTEGER" +
                ")");

        //Creación de la tabla de anotaciones
        db.execSQL("CREATE TABLE " + ANNOTATIONS_TABLE_NAME + " (" +
                ANNOTATIONS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ANNOTATIONS_COLUMN_FATHER_TASK + " INTEGER, " +
                ANNOTATIONS_COLUMN_CONTENT + " TEXT, " +
                ANNOTATIONS_COLUMN_IDVIEW + " INTEGER" +
                ")");

        //Creación de la tabla de calendario
        db.execSQL("CREATE TABLE " + EVENT_TABLE_NAME + " (" +
                EVENT_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                EVENT_COLUMN_NAME + " STRING, " +
                EVENT_COLUMN_EXPIRATION_DAY + " INTEGER, " +
                EVENT_COLUMN_EXPIRATION_MONTH + " INTEGER, " +
                EVENT_COLUMN_EXPIRATION_YEAR + " INTEGER" +
                ")");

        //Creación de la tabla de mercado
        db.execSQL("CREATE TABLE " + MARKET_TABLE_NAME + " (" +
                MARKET_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MARKET_COLUMN_NAME + " TEXT, " +
                MARKET_COLUMN_DESCRIPTION + " TEXT, " +
                MARKET_COLUMN_COST + " INTEGER, " +
                MARKET_COLUMN_AVAILABLE + " INTEGER, " +
                MARKET_COLUMN_CLASS + " INTEGER" +
                ")");

        //Creación de la tabla de mascota virtual
        db.execSQL("CREATE TABLE " + VIRTUALPET_TABLE_NAME + " (" +
                VIRTUALPET_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                VIRTUALPET_COLUMN_NAME + " TEXT, " +
                VIRTUALPET_COLUMN_CLASS + " INTEGER, " +
                VIRTUALPET_COLUMN_EVOLUTION + " INTEGER, " +
                VIRTUALPET_COLUMN_EXPERIENCE + " INTEGER, " +
                VIRTUALPET_COLUMN_HUNGER + " INTEGER, " +
                VIRTUALPET_COLUMN_DIRT + " INTEGER, " +
                VIRTUALPET_COLUMN_EVOLUTION_READY + " INTEGER, " +
                VIRTUALPET_COLUMN_EQUIPED_INDEX + " INTEGER" +
                ")");
    }

    //Actualización de la base de datos (cuando se cambia de versión)
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Borra todas las tablas y las crea de nuevo
        db.execSQL("DROP TABLE IF EXISTS " + TASK_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SUBTASK_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ANNOTATIONS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + EVENT_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MARKET_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + VIRTUALPET_TABLE_NAME);
        onCreate(db);
    }
}
