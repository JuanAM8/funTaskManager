package com.example.testingtfg.minigames.virtualPet;

import android.app.Activity;
import android.graphics.Bitmap;

import com.example.testingtfg.MainActivity;
import com.example.testingtfg.R;

/*Clase que gestiona los objetos comprables en el mercado*/
public class PurchasableItem {

    //region Parámetros
    public enum purchasableClass {Pet, Attachment, Expendable};
    private int id;//Base de datos
    private String name;
    private String description;
    private int spriteDrawable;
    private int cost;
    private boolean available;
    private purchasableClass itemClass;
    private int idInView;
    //endregion
    //region Nombres
    private static final String EGG_NAME = "Huevo de ";
    private static final String CHILD_NAME_0 = "Iki";
    private static final String CHILD_NAME_1 = "Rillu";
    private static final String CHILD_NAME_2 = "Gmui";
    private static final String CHILD_NAME_3 = "Kralpin";
    private static final String CHILD_NAME_4 = "Gnovarr";
    private static final String CHILD_NAME_5 = "Whoola";
    private static final String CHILD_NAME_6 = "Feathy";
    private static final String CHILD_NAME_7 = "Astrik";
    private static final String EXP_BELT_NAME = "Cinturón de experiencia";
    private static final String FOOD_BELT_NAME = "Cinturón antihambre";
    private static final String CLEAN_BELT_NAME = "Cinturón antisuciedad";
    private static final String CLEAN_PILL_NAME = "Cápsula de limpieza";
    private static final String FOOD_PILL_NAME = "Cápsula de comida";
    private static final String EXP_PILL_NAME = "Cápsula de experiencia";
    private static final String EVO_ROCK_NAME = "Roca de evolución";
    //endregion

    //Constructor
    public PurchasableItem(int classIndex, String name, String description, int cost,
                           boolean available){
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.available = available;
        setClassByIndex(classIndex);
        setSpriteByName();
    }

    //region getters y setters
    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAvailable(){
        return available;
    }

    public void setAvailable(boolean available){
        this.available = available;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSprite() {
        return spriteDrawable;
    }

    public void setSprite(int sprite) {
        this.spriteDrawable = sprite;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getIdInView(){
        return idInView;
    }

    public void setIdInView(int id){
        idInView = id;
    }

    public purchasableClass getItemClass() {
        return itemClass;
    }

    public int getItemClassAsInt(){
        switch(itemClass){
            case Pet:
                return 0;
            case Attachment:
                return 1;
            default:
                return 2;
        }
    }

    public void setItemClass(purchasableClass itemClass) {
        this.itemClass = itemClass;
    }

    private void setClassByIndex(int index){
        switch(index){
            case 0:
                itemClass = purchasableClass.Pet;
                break;
            case 1:
                itemClass = purchasableClass.Attachment;
                break;
            case 2:
                itemClass = purchasableClass.Expendable;
                break;
            default:
                break;
        }
    }

    private void setSpriteByName(){
        switch (name){
            case EGG_NAME + CHILD_NAME_0:
                spriteDrawable = R.drawable.egg0;
                break;
            case EGG_NAME + CHILD_NAME_1:
                spriteDrawable = R.drawable.egg1;
                break;
            case EGG_NAME + CHILD_NAME_2:
                spriteDrawable = R.drawable.egg2;
                break;
            case EGG_NAME + CHILD_NAME_3:
                spriteDrawable = R.drawable.egg3;
                break;
            case EGG_NAME + CHILD_NAME_4:
                spriteDrawable = R.drawable.egg4;
                break;
            case EGG_NAME + CHILD_NAME_5:
                spriteDrawable = R.drawable.egg5;
                break;
            case EGG_NAME + CHILD_NAME_6:
                spriteDrawable = R.drawable.egg6;
                break;
            case EGG_NAME + CHILD_NAME_7:
                spriteDrawable = R.drawable.egg7;
                break;
            case CLEAN_BELT_NAME:
                spriteDrawable = R.drawable.clean_belt;
                break;
            case FOOD_BELT_NAME:
                spriteDrawable = R.drawable.hunger_belt;
                break;
            case EXP_BELT_NAME:
                spriteDrawable = R.drawable.exp_belt;
                break;
            case CLEAN_PILL_NAME:
                spriteDrawable = R.drawable.clean_pill;
                break;
            case FOOD_PILL_NAME:
                spriteDrawable = R.drawable.hunger_pill;
                break;
            case EXP_PILL_NAME:
                spriteDrawable = R.drawable.exp_pill;
                break;
            case EVO_ROCK_NAME:
                spriteDrawable = R.drawable.evo_rock;
                break;
            default:
                spriteDrawable = R.drawable.egg_base3;
                break;
        }
    }
    //endregion



}
