package com.example.testingtfg.minigames.virtualPet;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.testingtfg.MainActivity;
import com.example.testingtfg.database.DatabaseManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Stack;

/*Manager de la colección de mascotas virtuales*/
public class VirtualPetManager {

    //region Parámetros y Constantes
    public static final String HUNGER_TITLE = "¡Hambre!";
    public static final String DIRT_TITLE = "¡Sucio!";
    public static final String HUNGER_CONTENT = "Tus Taskys tienen hambre. ¡Aliméntalos con cápsulas de comida!";
    public static final String DIRT_CONTENT = "Tus Taskys están sucios. ¡Límpialos con cápsulas de limpieza!";
    private MainActivity activity;
    private ArrayList<VirtualPet> virtualPets;
    private boolean debugMode = false;
    private boolean notifyHunger;
    private boolean notifyDirt;
    //endregion

    //region SharedPreferences
    SharedPreferences preferences;
    public static final String MarketPreferences = "market_pref";
    public static final String ExpBeltBought = "exp_bought";
    public static final String CleanBeltBought = "clean_bought";
    public static final String FoodBeltBought = "food_bought";
    public static final String ExpBeltAvailable = "exp_available";
    public static final String CleanBeltAvailable = "clean_available";
    public static final String FoodBeltAvailable = "food_available";
    public static final String PlayTokens = "play_tokens";
    public static final String FoodTokens = "food_tokens";
    public static final String CleanTokens = "clean_tokens";
    public static final String ExpTokens = "exp_tokens";
    public static final String EvoRocks = "evo_rocks";
    public static final String LastHour = "last_hour";
    private int playPoints;
    private int cleanTokens;
    private int foodTokens;
    private int expTokens;
    private int evoTokens;
    private boolean expBeltAvailable;
    private boolean expBeltBought;
    private boolean foodBeltAvailable;
    private boolean foodBeltBought;
    private boolean cleanBeltAvailable;
    private boolean cleanBeltBought;
    private int lastHour;
    //endregion

    //region Constructor
    public VirtualPetManager(MainActivity a){
        activity = a;
        notifyHunger = false;
        notifyDirt = false;
        initObjectsFromDB();
        loadPreferences();
        if (debugMode){
            virtualPets = new ArrayList<>();
        }
        increasePetsHungerAndDirt();
    }

    private void initObjectsFromDB(){
        virtualPets = DatabaseManager.readDataVirtualPet(activity);
    }
    //endregion

    //region Gestión de Mascota Virtual
    public void addFood(){
        foodTokens++;
        savePreferences();
    }

    public void consumeFood(int id){
        VirtualPet virtualPet = virtualPets.get(id);
        if (foodTokens > 0){
            if (virtualPet.getHunger() > 0){
                foodTokens--;
                savePreferences();
                virtualPet.decreaseHunger();
                DatabaseManager.editDataVirtualPet(virtualPet, activity);
            } else {
                activity.toastMessage(virtualPet.getPetName() + " no tiene hambre");
            }
        } else {
            activity.toastMessage("No tienes cápsulas de comida");
        }
    }

    public void addCleaner(){
        cleanTokens++;
        savePreferences();
    }

    public void consumeCleaner(int id){
        VirtualPet virtualPet = virtualPets.get(id);
        if (cleanTokens > 0){
            if (virtualPet.getDirt() > 0){
                cleanTokens--;
                savePreferences();
                virtualPet.decreaseDirt();
                DatabaseManager.editDataVirtualPet(virtualPet, activity);
            } else {
                activity.toastMessage(virtualPet.getPetName() + " está limpio");
            }
        } else {
            activity.toastMessage("No tienes cápsulas de limpieza");
        }
    }

    public void addExperienceToken(){
        expTokens++;
        savePreferences();
    }

    public void consumeExpToken(int id){
        VirtualPet virtualPet = virtualPets.get(id);
        if (expTokens > 0){
            if (!virtualPet.isReadyForEvolve() && virtualPet.getEvolutionIndex() != 3){
                expTokens--;
                savePreferences();
                virtualPet.increaseExperience();
                DatabaseManager.editDataVirtualPet(virtualPet, activity);
            } else if (virtualPet.getEvolutionIndex() != 3){
                activity.toastMessage(virtualPet.getPetName() + " tiene que evolucionar para ganar más experiencia");
            } else {
                activity.toastMessage(virtualPet.getPetName() + " no puede ganar más experiencia");
            }
        } else {
            activity.toastMessage("Necesitas comprar cápsulas de experiencia");
        }
    }

    public void addEvoRock(){
        evoTokens++;
        savePreferences();
    }

    public void consumeEvoRock(int id){
        VirtualPet virtualPet = virtualPets.get(id);
        if (evoTokens > 0){
            if (virtualPet.isReadyForEvolve()){
                String nameBefore = virtualPet.getPetName();
                evoTokens--;
                savePreferences();
                virtualPet.evolve();
                DatabaseManager.editDataVirtualPet(virtualPet, activity);
                activity.getStatsTracker().increaseEvolutionCount();
                if (virtualPet.getEvolutionIndex() == 1){
                    activity.toastMessage(virtualPet.getPetName() + " ha nacido del huevo");
                } else {
                    activity.toastMessage(nameBefore + " evoluciona a " + virtualPet.getPetName());
                }
            } else if (virtualPet.getEvolutionIndex() != 3){
                activity.toastMessage(virtualPet.getPetName() + " tiene que ganar más experiencia");
            } else {
                activity.toastMessage(virtualPet.getPetName() + " no puede evolucionar más");
            }
        } else {
            activity.toastMessage("Necesitas comprar rocas de evolución");
        }
    }

    public void equipItem(int petIndex, int itemIndex){
        VirtualPet virtualPet = virtualPets.get(petIndex);
        switch(itemIndex){
            case 0:
                if (expBeltAvailable){
                    boolean equiped = virtualPet.equipItem(0);
                    if (equiped){
                        setExpBeltAvailable(false);
                        activity.toastMessage("Equipado cinturón de experiencia a " + virtualPet.getPetName());
                    } else {
                        activity.toastMessage(virtualPet.getPetName() + " ya tiene un objeto equipado");
                    }
                } else {
                    activity.toastMessage("No tienes este objeto");
                }
                break;
            case 1:
                if (cleanBeltAvailable){
                    boolean equiped = virtualPet.equipItem(1);
                    if (equiped){
                        setCleanBeltAvailable(false);
                        activity.toastMessage("Equipado cinturón antisuciedad a " + virtualPet.getPetName());
                    } else {
                        activity.toastMessage(virtualPet.getPetName() + " ya tiene un objeto equipado");
                    }
                } else {
                    activity.toastMessage("No tienes este objeto");
                }
                break;
            case 2:
                if (foodBeltAvailable){
                    boolean equiped = virtualPet.equipItem(2);
                    if (equiped){
                        setFoodBeltAvailable(false);
                        activity.toastMessage("Equipado cinturón antihambre a " + virtualPet.getPetName());
                    } else {
                        activity.toastMessage(virtualPet.getPetName() + " ya tiene un objeto equipado");
                    }
                } else {
                    activity.toastMessage("No tienes este objeto");
                }
                break;
            default:
                activity.toastMessage("ERROR: Should never happen");
                break;
        }
        DatabaseManager.editDataVirtualPet(virtualPet, activity);
    }

    public void unattachItem(int petIndex){
        VirtualPet virtualPet = virtualPets.get(petIndex);
        int equipedIndex = virtualPet.unattachItem();
        switch(equipedIndex){
            case 0:
                setExpBeltAvailable(true);
                activity.toastMessage("Quitado cinturón de experiencia");
                break;
            case 1:
                setCleanBeltAvailable(true);
                activity.toastMessage("Quitado cinturón antisuciedad");
                break;
            case 2:
                setFoodBeltAvailable(true);
                activity.toastMessage("Quitado cinturón antihambre");
                break;
            default:
                activity.toastMessage(virtualPet.getPetName() + " no tiene nada equipado");
                break;
        }
        DatabaseManager.editDataVirtualPet(virtualPet, activity);
    }

    public void addVirtualPet(VirtualPet virtualPet){
        int id = DatabaseManager.saveDataVirtualPet(virtualPet, activity);
        virtualPet.setId(id);
        virtualPet.setIdInList(virtualPets.size());
        virtualPets.add(virtualPet);
    }

    public ArrayList<VirtualPet> getVirtualPetList(){
        return virtualPets;
    }

    public void setVirtualPetList(ArrayList<VirtualPet> vpl){
        virtualPets = vpl;
    }
    //endregion

    //region SharedPreferences methods
    public void loadPreferences(){
        preferences = activity.getSharedPreferences(MarketPreferences, Context.MODE_PRIVATE);
        playPoints = preferences.getInt(PlayTokens, 0);
        foodTokens = preferences.getInt(FoodTokens, 0);
        cleanTokens = preferences.getInt(CleanTokens, 0);
        expTokens = preferences.getInt(ExpTokens, 0);
        evoTokens = preferences.getInt(EvoRocks, 0);
        expBeltBought = preferences.getBoolean(ExpBeltBought, false);
        cleanBeltBought = preferences.getBoolean(CleanBeltBought, false);
        foodBeltBought = preferences.getBoolean(FoodBeltBought, false);
        expBeltAvailable = preferences.getBoolean(ExpBeltAvailable, false);
        cleanBeltAvailable = preferences.getBoolean(CleanBeltAvailable, false);
        foodBeltAvailable = preferences.getBoolean(FoodBeltAvailable, false);
        lastHour = preferences.getInt(LastHour, 0);
    }

    private void savePreferences(){
        SharedPreferences.Editor spEditor = preferences.edit();
        spEditor.putInt(PlayTokens, playPoints);
        spEditor.putInt(CleanTokens, cleanTokens);
        spEditor.putInt(FoodTokens, foodTokens);
        spEditor.putInt(ExpTokens, expTokens);
        spEditor.putInt(EvoRocks, evoTokens);
        spEditor.putBoolean(ExpBeltBought, expBeltBought);
        spEditor.putBoolean(CleanBeltBought, cleanBeltBought);
        spEditor.putBoolean(FoodBeltBought, foodBeltBought);
        spEditor.putBoolean(ExpBeltAvailable, expBeltAvailable);
        spEditor.putBoolean(CleanBeltAvailable, cleanBeltAvailable);
        spEditor.putBoolean(FoodBeltAvailable, foodBeltAvailable);
        spEditor.putInt(LastHour, lastHour);
        spEditor.commit();
    }
    //endregion

    //region getters y setters
    public int getPlayPoints() {
        return playPoints;
    }

    public void setPlayPoints(int playPoints) {
        this.playPoints = playPoints;
    }

    public void increasePlayPoints(int playPoints){
        this.playPoints += playPoints;
        savePreferences();
    }

    public int getCleanTokens() {
        return cleanTokens;
    }

    public void setCleanTokens(int cleanTokens) {
        this.cleanTokens = cleanTokens;
    }

    public int getFoodTokens() {
        return foodTokens;
    }

    public void setFoodTokens(int foodTokens) {
        this.foodTokens = foodTokens;
    }

    public int getExpTokens() {
        return expTokens;
    }

    public void setExpTokens(int expTokens) {
        this.expTokens = expTokens;
    }

    public int getEvoTokens() {
        return evoTokens;
    }

    public void setEvoTokens(int evoTokens) {
        this.evoTokens = evoTokens;
    }

    public boolean isExpBeltAvailable() {
        return expBeltAvailable;
    }

    public void setExpBeltAvailable(boolean expBeltAvailable) {
        this.expBeltAvailable = expBeltAvailable;
        savePreferences();
    }

    public boolean isExpBeltBought() {
        return expBeltBought;
    }

    public void setExpBeltBought(boolean expBeltBought) {
        this.expBeltBought = expBeltBought;
        savePreferences();
    }

    public boolean isFoodBeltAvailable() {
        return foodBeltAvailable;
    }

    public void setFoodBeltAvailable(boolean foodBeltAvailable) {
        this.foodBeltAvailable = foodBeltAvailable;
        savePreferences();
    }

    public boolean isFoodBeltBought() {
        return foodBeltBought;
    }

    public void setFoodBeltBought(boolean foodBeltBought) {
        this.foodBeltBought = foodBeltBought;
        savePreferences();
    }

    public boolean isCleanBeltAvailable() {
        return cleanBeltAvailable;
    }

    public void setCleanBeltAvailable(boolean cleanBeltAvailable) {
        this.cleanBeltAvailable = cleanBeltAvailable;
        savePreferences();
    }

    public boolean isCleanBeltBought() {
        return cleanBeltBought;
    }

    public void setCleanBeltBought(boolean cleanBeltBought) {
        this.cleanBeltBought = cleanBeltBought;
        savePreferences();
    }

    public void increasePetsHungerAndDirt(){
        int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        if (currentHour != lastHour && currentHour % 2 == 0){
            lastHour = currentHour;
            for (VirtualPet pet : virtualPets){
                pet.increaseDirt();
                pet.increaseHunger();
                DatabaseManager.editDataVirtualPet(pet, activity);
                if (pet.getDirt() > 50){
                    notifyDirt = true;
                }
                if (pet.getHunger() > 50){
                    notifyHunger = true;
                }
            }
        }

    }

    public boolean isNotifyHunger(){
        return notifyHunger;
    }

    public boolean isNotifyDirt(){
        return notifyDirt;
    }
    //endregion
}
