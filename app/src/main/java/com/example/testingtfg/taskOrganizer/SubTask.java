package com.example.testingtfg.taskOrganizer;

/*Clase que gestiona el comportamiento de las sub-tareas*/
public class SubTask {

    //region Parámetros
    private int id;
    private int idInView;
    private int fatherId;
    private String name;
    private boolean finished;
    //endregion

    //region Constructores
    //Constructor parametrizado invocado al crear una nueva subtarea
    public SubTask(int fatherId, String name, boolean finished){
        this.fatherId = fatherId;
        this.name = name;
        this.finished = finished;
    }

    //Constructor con ids para crear una subtarea desde la base de datos
    public SubTask(int id, int fatherId, String name, int finished, int idInView) {
        this.id = id;
        this.fatherId = fatherId;
        this.name = name;
        if (finished == 1){ this.finished = true;} else { this.finished = false; }
        this.idInView = idInView;
    }
    //endregion

    //region Getters y Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public int getIdInView(){
        return idInView;
    }

    public void setIdInView(int idInView){
        this.idInView = idInView;
    }

    public int getFatherId() {
        return fatherId;
    }

    public void setFatherId(int fatherId) {
        this.fatherId = fatherId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    //endregion
}
