package com.example.testingtfg.taskOrganizer;

/*Clase que gestiona las anotaciones de las tareas*/
public class Annotation {

    //region Parámetros
    private int id;
    private int idInView;
    private int fatherId;
    private String content;
    //endregion

    //region Constructores
    //Constructor básico
    public Annotation(int fatherId, String content){
        this.fatherId = fatherId;
        this.content = content;
    }

    //Constructor con id de la base de datos y de la View
    public Annotation(int id, int fatherId, String content, int idInView) {
        this.id = id;
        this.fatherId = fatherId;
        this.content = content;
        this.idInView = idInView;
    }
    //endregion

    //region getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdInView() {
        return idInView;
    }

    public void setIdInView(int idInView) {
        this.idInView = idInView;
    }

    public int getFatherId() {
        return fatherId;
    }

    public void setFatherId(int fatherId) {
        this.fatherId = fatherId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    //endregion
}
