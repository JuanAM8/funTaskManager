package com.example.testingtfg.minigames.virtualPet;

import com.example.testingtfg.R;

import java.util.ArrayList;

/*Clase que gestiona la mascota virtual*/
public class VirtualPet {

    //region Enumeraciones y Constantes
    private enum creatureClass {Creature1, Creature2, Creature3, Creature4, Creature5, Creature6,
        Creature7, Creature8};
    private enum evoState {Egg, Child, Medium, Ultimate};
    private final int HATCHING_THRESHOLD = 60;
    private final int CHILD_EVOLUTION_THRESHOLD = 180;
    private final int MEDIUM_EVOLUTION_THRESHOLD = 250;
    private final int EXPERIENCE_UNIT = 10;
    private final int HUNGER_UNIT = 1;
    private final int DIRT_UNIT = 1;
    private final int MAX_HUNGER = 100;
    private final int MIN_HUNGER = 0;
    private final int MAX_DIRT = 100;
    private final int MIN_DIRT = 0;
    //endregion
    //region Parámetros
    private int id;//Base de datos
    private int idInList;//Lista
    private creatureClass creatureClass;
    private evoState currentEvoState;
    private String petName;
    private ArrayList<String> possibleNames;
    private int currentExperience;
    private ArrayList<Integer> possibleSprites;
    private int currentSprite;
    private int classIndex;
    private int dirt;
    private int hunger;
    private boolean readyForEvolve;
    private int indexEquippedItem;
    //endregion

    //region Constructores
    /*Crea una nueva mascota cuando se compra*/
    public VirtualPet(int classIndex, String name){
        setCreatureClassByIndex(classIndex);
        petName = name;
        currentExperience = 0;
        dirt = 0;
        hunger = 0;
        indexEquippedItem = -1;
        readyForEvolve = false;
        currentEvoState = evoState.Egg;
        currentSprite = possibleSprites.get(0);
    }

    /*Crea una mascota existente cargando su información desde la base de datos*/
    public VirtualPet(int classIndex, String name, int currentExperience, int evoIndex,
                      int hunger, int dirt, boolean readyForEvolve, int equipedIndex){
        setCreatureClassByIndex(classIndex);
        petName = name;
        this.currentExperience = currentExperience;
        setEvolutionStateByIndex(evoIndex);
        this.dirt = dirt;
        this.hunger = hunger;
        this.readyForEvolve = readyForEvolve;
        indexEquippedItem = equipedIndex;
    }
    //endregion

    //region Métodos
    /*Incrementa la experiencia del Tasky*/
    public void increaseExperience(){
        if (indexEquippedItem != 0){
            currentExperience += EXPERIENCE_UNIT;
        } else {
            currentExperience += EXPERIENCE_UNIT * 2;
        }

        if (currentExperience >= HATCHING_THRESHOLD && currentEvoState == evoState.Egg){
            currentExperience = HATCHING_THRESHOLD;
            readyForEvolve = true;
        } else if (currentExperience >= CHILD_EVOLUTION_THRESHOLD &&
                currentEvoState == evoState.Child){
            currentExperience = CHILD_EVOLUTION_THRESHOLD;
            readyForEvolve = true;
        } else if (currentExperience >= MEDIUM_EVOLUTION_THRESHOLD &&
                currentEvoState == evoState.Medium){
            currentExperience = MEDIUM_EVOLUTION_THRESHOLD;
            readyForEvolve = true;
        }
    }

    public void update(){
    }

    /*Evoluciona a la criatura cambiando su nombre y sprite*/
    public void evolve(){
        if (currentEvoState == evoState.Egg){
            currentEvoState = evoState.Child;
            currentSprite = possibleSprites.get(1);
            petName = possibleNames.get(0);
            readyForEvolve = false;
        } else if (currentEvoState == evoState.Child){
            currentEvoState = evoState.Medium;
            currentSprite = possibleSprites.get(2);
            petName = possibleNames.get(1);
            readyForEvolve = false;
        } else if (currentEvoState == evoState.Medium){
            currentEvoState = evoState.Ultimate;
            currentSprite = possibleSprites.get(3);
            petName = possibleNames.get(2);
            readyForEvolve = false;
        }
    }

    /*Establece la clase de criatura en función al índice introducido como parámetro*/
    private void setCreatureClassByIndex(int index){
        possibleSprites = new ArrayList<>();
        possibleNames = new ArrayList<>();
        classIndex = index;
        switch(index){
            case 0:
                creatureClass = creatureClass.Creature1;
                possibleSprites.add(R.drawable.egg0);
                possibleSprites.add(R.drawable.child0);
                possibleSprites.add(R.drawable.medium0);
                possibleSprites.add(R.drawable.ultimate0);
                possibleNames.add("Iki");
                possibleNames.add("Ikey");
                possibleNames.add("Ikayu");
                break;
            case 1:
                creatureClass = creatureClass.Creature2;
                possibleSprites.add(R.drawable.egg1);
                possibleSprites.add(R.drawable.child1);
                possibleSprites.add(R.drawable.medium1);
                possibleSprites.add(R.drawable.ultimate1);
                possibleNames.add("Rillu");
                possibleNames.add("Rilluar");
                possibleNames.add("Rilluyok");
                break;
            case 2:
                creatureClass = creatureClass.Creature3;
                possibleSprites.add(R.drawable.egg2);
                possibleSprites.add(R.drawable.child2);
                possibleSprites.add(R.drawable.medium2);
                possibleSprites.add(R.drawable.ultimate2);
                possibleNames.add("Gmui");
                possibleNames.add("Gmupra");
                possibleNames.add("Gmupraex");
                break;
            case 3:
                creatureClass = creatureClass.Creature4;
                possibleSprites.add(R.drawable.egg3);
                possibleSprites.add(R.drawable.child3);
                possibleSprites.add(R.drawable.medium3);
                possibleSprites.add(R.drawable.ultimate3);
                possibleNames.add("Kralpin");
                possibleNames.add("Kralper");
                possibleNames.add("Kralpblast");
                break;
            case 4:
                creatureClass = creatureClass.Creature5;
                possibleSprites.add(R.drawable.egg4);
                possibleSprites.add(R.drawable.child4);
                possibleSprites.add(R.drawable.medium4);
                possibleSprites.add(R.drawable.ultimate4);
                possibleNames.add("Gnovarr");
                possibleNames.add("Gnavorr");
                possibleNames.add("Gnaviross");
                break;
            case 5:
                creatureClass = creatureClass.Creature6;
                possibleSprites.add(R.drawable.egg5);
                possibleSprites.add(R.drawable.child5);
                possibleSprites.add(R.drawable.medium5);
                possibleSprites.add(R.drawable.ultimate5);
                possibleNames.add("Whoola");
                possibleNames.add("Whaazli");
                possibleNames.add("Whaazsline");
                break;
            case 6:
                creatureClass = creatureClass.Creature7;
                possibleSprites.add(R.drawable.egg6);
                possibleSprites.add(R.drawable.child6);
                possibleSprites.add(R.drawable.medium6);
                possibleSprites.add(R.drawable.ultimate6);
                possibleNames.add("Feathy");
                possibleNames.add("Feather");
                possibleNames.add("Feathfull");
                break;
            case 7:
                creatureClass = creatureClass.Creature8;
                possibleSprites.add(R.drawable.egg7);
                possibleSprites.add(R.drawable.child7);
                possibleSprites.add(R.drawable.medium7);
                possibleSprites.add(R.drawable.ultimate7);
                possibleNames.add("Astrik");
                possibleNames.add("Astrilak");
                possibleNames.add("Astribald");
                break;
            default:
                break;
        }
    }

    /*Establece el estado de evolución de la criatura desde el índice introducido por parámetro*/
    private void setEvolutionStateByIndex(int index){
        switch(index){
            case 0:
                currentEvoState = evoState.Egg;
                currentSprite = possibleSprites.get(0);
                break;
            case 1:
                currentEvoState = evoState.Child;
                currentSprite = possibleSprites.get(1);
                break;
            case 2:
                currentEvoState = evoState.Medium;
                currentSprite = possibleSprites.get(2);
                break;
            case 3:
                currentEvoState = evoState.Ultimate;
                currentSprite = possibleSprites.get(3);
                break;
            default:
                break;
        }
    }

    public int getEvolutionIndex(){
        int indexToReturn = 0;
        switch(currentEvoState){
            case Egg:
                indexToReturn = 0;
                break;
            case Child:
                indexToReturn = 1;
                break;
            case Medium:
                indexToReturn = 2;
                break;
            default:
                indexToReturn = 3;
                break;
        }
        return indexToReturn;
    }

    //Equipa un objeto en concreto
    public boolean equipItem(int itemId){
        if (indexEquippedItem == -1){
            indexEquippedItem = itemId;
            return true;
        } else {
            return false;
        }
    }

    public int unattachItem(){
        int indexOfItem = indexEquippedItem;
        indexEquippedItem = -1;
        return indexOfItem;
    }

    public int getIndexEquippedItem(){
        return indexEquippedItem;
    }

    public String getEquipedItemAsString(){
        String equipment = "";
        switch (indexEquippedItem){
            case 0:
                equipment = "Cinturón de experiencia";
                break;
            case 1:
                equipment = "Cinturón antisuciedad";
                break;
            case 2:
                equipment = "Cinturón antihambre";
                break;
            default:
                equipment = "Nada";
                break;
        }
        return equipment;
    }
    //endregion

    //region getters and setters
    public String getPetName(){
        return petName;
    }

    public int getPetSprite(){
        return currentSprite;
    }

    public int getExperience(){
        return currentExperience;
    }

    public int getClassIndex(){
        return classIndex;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getDirt() {
        return dirt;
    }

    public void setDirt(int dirt) {
        this.dirt = dirt;
    }

    public void increaseDirt(){
        if (indexEquippedItem != 1){
            dirt += DIRT_UNIT*5;
            if (dirt > MAX_DIRT){
                dirt = MAX_DIRT;
            }
        }
    }

    public void decreaseDirt(){
        dirt -= DIRT_UNIT*10;
        if (dirt < MIN_DIRT){
            dirt = MIN_DIRT;
        }
    }

    public int getHunger() {
        return hunger;
    }

    public void setHunger(int hunger) {
        this.hunger = hunger;
    }

    public void increaseHunger(){
        if (indexEquippedItem != 2){
            hunger+= HUNGER_UNIT*5;
            if (hunger > MAX_HUNGER){
                hunger = MAX_HUNGER;
            }
        }
    }

    public void decreaseHunger(){
        hunger -= HUNGER_UNIT*10;
        if (hunger < MIN_HUNGER){
            hunger = MIN_HUNGER;
        }
    }

    public boolean isReadyForEvolve(){
        return readyForEvolve;
    }

    public int getIdInList() {
        return idInList;
    }

    public void setIdInList(int idInList) {
        this.idInList = idInList;
    }
    //endregion
}
