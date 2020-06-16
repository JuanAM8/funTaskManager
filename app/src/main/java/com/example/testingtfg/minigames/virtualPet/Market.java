package com.example.testingtfg.minigames.virtualPet;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.testingtfg.MainActivity;
import com.example.testingtfg.R;
import com.example.testingtfg.database.DatabaseManager;

import java.util.ArrayList;

/*Clase que gestiona cómo funciona el mercado*/
public class Market {

    //region Parámetros
    private ArrayList<PurchasableItem> purchasableItems;
    private VirtualPetManager virtualPetManager;
    private Activity activity;
    private boolean debugMode = false;
    //endregion

    //region Constructor
    public Market(VirtualPetManager virtualPetManager, Activity a){
        activity = a;
        if (debugMode){
            DatabaseManager.clearTargetTable(a, 2);
        }
        purchasableItems = DatabaseManager.readDataMarket(a);
        if (purchasableItems.isEmpty()){
            initList();
        }
        this.virtualPetManager = virtualPetManager;
    }
    //endregion

    //region Gestión de Mercado
    //Devuelve el objeto de la lista con el índice introducido como parámetro
    public PurchasableItem getItem(int itemIndex){
        return purchasableItems.get(itemIndex);
    }

    //Compra de un objeto en base al índice que reciba por parámetro
    public void purchase(int itemIndex){
        PurchasableItem targetItem = purchasableItems.get(itemIndex);
        if (targetItem.getItemClass() == PurchasableItem.purchasableClass.Pet){
            targetItem.setAvailable(false);
            VirtualPet virtualPet = new VirtualPet(itemIndex, targetItem.getName());
            virtualPetManager.addVirtualPet(virtualPet);
            ((MainActivity)activity).getStatsTracker().increaseEggCount();
        } else if (targetItem.getItemClass() == PurchasableItem.purchasableClass.Attachment){
            targetItem.setAvailable(false);
            if (targetItem.getName().equals(activity.getResources().getString(R.string.exp_belt_name))){
                virtualPetManager.setExpBeltBought(true);
                virtualPetManager.setExpBeltAvailable(true);
            } else if (targetItem.getName().equals(activity.getResources().getString(R.string.food_belt_name))){
                virtualPetManager.setFoodBeltBought(true);
                virtualPetManager.setFoodBeltAvailable(true);
            } else if (targetItem.getName().equals(activity.getResources().getString(R.string.clean_belt_name))){
                virtualPetManager.setCleanBeltBought(true);
                virtualPetManager.setCleanBeltAvailable(true);
            }
            ((MainActivity)activity).getStatsTracker().increaseItemCount();
        } else if (targetItem.getItemClass() == PurchasableItem.purchasableClass.Expendable){
            if (targetItem.getName().equals(activity.getResources().getString(R.string.food_pill_name))){
                virtualPetManager.addFood();
            } else if (targetItem.getName().equals(activity.getResources().getString(R.string.clean_pill_name))){
                virtualPetManager.addCleaner();
            } else if (targetItem.getName().equals(activity.getResources().getString(R.string.exp_pill_name))){
                virtualPetManager.addExperienceToken();
            } else if (targetItem.getName().equals(activity.getResources().getString(R.string.evolution_stone))){
                virtualPetManager.addEvoRock();
            }
            ((MainActivity)activity).getStatsTracker().increaseExpendablesCount();
        }
        editItem(targetItem);
    }

    //Devuelve la lista de objetos
    public ArrayList<PurchasableItem> getPurchasableItems(){
        return purchasableItems;
    }

    //Modifica los parámetros de un objeto concreto en la base de datos
    private void editItem(PurchasableItem purchasableItem){
        DatabaseManager.editDataMarket(purchasableItem, activity);
    }

    //Inicializa la lista la primera vez que se accede a esta pantalla
    private void initList(){
        String eggDesc = "Es un misterioso huevo del que nacerá un Tasky.";
        String expDesc = "El tasky equipado con este cinturón obtendrá el doble de experiencia";
        String fullDesc = "El tasky equipado con esta diadema nunca tendrá hambre";
        String cleanestDesc = "El tasky equipado no se ensuciará.";
        String foodDesc = "Reduce el hambre de los taskys";
        String cleanDesc = "Reduce la suciedad de los taskys";
        String expCapDesc = "Aumenta la experiencia de los taskys.";
        String evoRockDesc = "Evoluciona a un tasky que ha alcanzado su máxima experiencia";
        PurchasableItem egg1 = new PurchasableItem(0, activity.getResources().getString(R.string.egg_name) + " " + activity.getResources().getString(R.string.creature0_name), eggDesc, 15, true);
        PurchasableItem egg2 = new PurchasableItem(0, activity.getResources().getString(R.string.egg_name) + " " + activity.getResources().getString(R.string.creature1_name), eggDesc, 15, true);
        PurchasableItem egg3 = new PurchasableItem(0, activity.getResources().getString(R.string.egg_name) + " " + activity.getResources().getString(R.string.creature2_name), eggDesc, 15, true);
        PurchasableItem egg4 = new PurchasableItem(0, activity.getResources().getString(R.string.egg_name) + " " + activity.getResources().getString(R.string.creature3_name), eggDesc, 15, true);
        PurchasableItem egg5 = new PurchasableItem(0, activity.getResources().getString(R.string.egg_name) + " " + activity.getResources().getString(R.string.creature4_name), eggDesc, 15, true);
        PurchasableItem egg6 = new PurchasableItem(0, activity.getResources().getString(R.string.egg_name) + " " + activity.getResources().getString(R.string.creature5_name), eggDesc, 15, true);
        PurchasableItem egg7 = new PurchasableItem(0, activity.getResources().getString(R.string.egg_name) + " " + activity.getResources().getString(R.string.creature6_name), eggDesc, 15, true);
        PurchasableItem egg8 = new PurchasableItem(0, activity.getResources().getString(R.string.egg_name) + " " + activity.getResources().getString(R.string.creature7_name), eggDesc, 15, true);
        PurchasableItem att1 = new PurchasableItem(1, activity.getResources().getString(R.string.exp_belt_name), expDesc, 100, true);
        PurchasableItem att2 = new PurchasableItem(1, activity.getResources().getString(R.string.food_belt_name), fullDesc, 100, true);
        PurchasableItem att3 = new PurchasableItem(1, activity.getResources().getString(R.string.clean_belt_name), cleanestDesc, 100, true);
        PurchasableItem exp1 = new PurchasableItem(2, activity.getResources().getString(R.string.food_pill_name), foodDesc, 5, true);
        PurchasableItem exp2 = new PurchasableItem(2, activity.getResources().getString(R.string.clean_pill_name), cleanDesc, 5, true);
        PurchasableItem exp3 = new PurchasableItem(2, activity.getResources().getString(R.string.exp_pill_name), expCapDesc, 10, true);
        PurchasableItem exp4 = new PurchasableItem(2, activity.getResources().getString(R.string.evolution_stone), evoRockDesc, 25, true);
        purchasableItems = new ArrayList<PurchasableItem>();
        purchasableItems.add(egg1);
        purchasableItems.add(egg2);
        purchasableItems.add(egg3);
        purchasableItems.add(egg4);
        purchasableItems.add(egg5);
        purchasableItems.add(egg6);
        purchasableItems.add(egg7);
        purchasableItems.add(egg8);
        purchasableItems.add(att1);
        purchasableItems.add(att2);
        purchasableItems.add(att3);
        purchasableItems.add(exp1);
        purchasableItems.add(exp2);
        purchasableItems.add(exp3);
        purchasableItems.add(exp4);

        for (PurchasableItem item : purchasableItems){
            int id = DatabaseManager.saveDataMarket(item, activity);
            item.setId(id);
        }

    }
    //endregion
}
