package com.example.testingtfg.minigames.mazeMinigame;

import android.graphics.Canvas;

import com.example.testingtfg.R;
import com.example.testingtfg.minigames.MinigamesEngine;
import com.example.testingtfg.minigames.GameObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*Clase que gestiona el funcionamiento único del minijuego 3*/
public class MazeGameManager extends GameObject {

    //region Constantes
    private int MARGIN_X = 10;
    private int MARGIN_Y = 10;
    //endregion
    //region Parámetros
    private MinigamesEngine gameEngine;
    private MazeLevel level;
    private List<MazeTile> tilePool = new ArrayList<MazeTile>();
    private List<MazeItem> itemPool = new ArrayList<MazeItem>();
    private List<MazeEnemy> enemyPool = new ArrayList<MazeEnemy>();
    private int drawableCharacter;
    private int drawableEnemy;
    private int drawableTile;
    private int drawableWall;
    private int drawableItem;
    private long currentTime;
    private int width;
    private int height;
    private int numberOfItems;
    //endregion
    //region Niveles
    //0 -> Tile vacío
    //1 -> Muro
    //2 -> Objeto
    //3 -> Enemigo Horizontal Derecha
    //4 -> Enemigo Horizontal Izquierda
    //5 -> Enemigo Vertical Abajo
    //6 -> Enemigo Vertical Arriba
    //7 -> Personaje

    private int[][] mapLevel1 = {
            {1,1,1,1,1,1,1,1,1,1},
            {1,2,1,0,7,0,0,1,2,1},
            {1,0,1,0,0,0,0,1,0,1},
            {1,0,1,0,0,0,0,1,0,1},
            {1,0,0,0,3,0,0,0,0,1},
            {1,1,1,1,1,0,0,0,0,1},
            {1,2,0,0,0,0,0,0,0,1},
            {1,0,0,0,6,0,0,0,0,1},
            {1,1,1,1,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,4,0,0,1},
            {1,0,0,0,0,0,0,1,0,1},
            {1,0,0,0,0,0,0,1,0,1},
            {1,0,0,0,0,0,0,1,2,1},
            {1,1,1,1,1,1,1,1,1,1},
    };

    private int[][] mapLevel2 = {
            {1,1,1,1,1,1,1,1,1,1},
            {1,0,0,0,0,0,0,0,0,1},
            {1,0,7,0,0,0,1,1,1,1},
            {1,0,0,0,0,0,1,2,1,1},
            {1,0,0,0,0,0,1,0,1,1},
            {1,0,6,0,0,0,1,0,1,1},
            {1,0,0,0,0,0,0,3,0,1},
            {1,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,1,1,1,1,1,1},
            {1,0,0,0,0,0,0,0,2,1},
            {1,0,1,1,1,1,1,1,1,1},
            {1,0,0,0,0,0,0,0,0,1},
            {1,1,0,1,0,1,0,1,0,1},
            {1,1,0,1,0,1,0,1,0,1},
            {1,1,2,1,6,1,2,1,6,1},
            {1,1,1,1,1,1,1,1,1,1},
    };

    private int[][] mapLevel3 = {
            {1,1,1,1,1,1,1,1,1,1},
            {1,0,0,0,0,0,0,0,0,1},
            {1,0,2,0,0,4,0,0,0,1},
            {1,1,0,1,1,1,1,1,0,1},
            {1,0,0,0,0,1,0,0,0,1},
            {1,0,0,0,0,1,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,1},
            {1,0,0,1,0,7,0,1,0,1},
            {1,0,0,1,1,1,1,1,0,1},
            {1,0,0,0,0,1,0,0,0,1},
            {1,0,0,0,0,1,2,0,0,1},
            {1,0,0,0,0,1,0,0,0,1},
            {1,0,1,1,1,1,0,0,0,1},
            {1,0,0,4,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,6,1},
            {1,1,1,1,1,1,1,1,1,1},
    };

    private int[][] mapLevel4 = {
            {1,1,1,1,1,1,1,1,1,1},
            {1,0,0,0,2,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,4,0,0,0,0,1},
            {1,0,5,1,0,1,6,0,0,1},
            {1,0,0,1,2,1,0,0,0,1},
            {1,4,0,1,1,1,0,3,0,1},
            {1,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,1},
            {1,0,4,0,0,0,0,0,0,1},
            {1,0,1,0,0,6,0,0,0,1},
            {1,0,1,0,0,0,0,0,0,1},
            {1,2,1,0,0,0,0,0,7,1},
            {1,1,1,1,1,1,1,1,1,1},
    };

    private int[][] mapLevel5 = {
            {1,1,1,1,1,1,1,1,1,1},
            {1,0,0,0,0,3,0,0,0,1},
            {1,0,1,0,1,0,1,1,1,1},
            {1,5,1,2,1,0,0,0,2,1},
            {1,0,1,1,1,1,1,1,1,1},
            {1,0,1,1,2,1,0,0,0,1},
            {1,0,0,1,0,1,0,0,0,1},
            {1,0,1,1,0,1,0,0,0,1},
            {1,0,1,1,0,1,0,0,0,1},
            {1,0,0,0,3,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,1},
            {1,7,0,0,0,0,0,0,0,1},
            {1,1,1,1,1,1,1,1,1,1},
    };

    private int[][] mapLevel6 = {
            {1,1,1,1,1,1,1,1,1,1},
            {1,0,0,0,0,0,0,0,7,1},
            {1,0,1,1,1,1,1,1,1,1},
            {1,0,0,0,0,0,3,0,0,1},
            {1,1,1,2,1,1,1,1,0,1},
            {1,0,0,1,0,0,0,0,0,1},
            {1,3,0,0,0,0,1,1,1,1},
            {1,0,1,0,1,0,0,0,2,1},
            {1,0,1,2,1,1,1,1,1,1},
            {1,0,0,1,1,1,1,1,1,1},
            {1,1,0,0,0,0,0,3,0,1},
            {1,1,1,1,1,2,1,1,0,1},
            {1,1,2,1,0,1,1,1,0,1},
            {1,0,0,0,3,0,0,0,0,1},
            {1,1,1,1,1,1,1,0,1,1},
            {1,1,1,1,1,1,1,1,1,1},
    };

    private int[][][] mapList = {mapLevel1, mapLevel2, mapLevel3, mapLevel4, mapLevel5, mapLevel6};
    //endregion

    //Constructor
    public MazeGameManager(MinigamesEngine gameEngine){
        drawableCharacter = R.drawable.sprite_mg3_character_v3;
        drawableWall = R.drawable.sprite_mg3_wall_v3;
        drawableItem = R.drawable.sprite_mg3_item;
        drawableEnemy = R.drawable.sprite_mg3_enemy_v3;

        width = gameEngine.width;
        height = gameEngine.height;
        this.gameEngine = gameEngine;

        level = new MazeLevel(selectRandomLevel());
        MARGIN_Y = height / (level.map.length);
        MARGIN_X = width / (level.map[0].length);
    }

    //Inicializa los objetos y el mundo
    @Override
    public void start(){
        initNumberOfItems();
        initWorld();
    }

    @Override
    public void update(long elapsedMillis, MinigamesEngine gameEngine){
        currentTime += elapsedMillis;
    }

    @Override
    public void draw(Canvas canvas){}

    public void returnToPool(MazeEnemy enemy){
        enemyPool.add(enemy);
    }

    //Establece cuántos objetos hay en un nivel para determinar cuántos deben recogerse
    private void initNumberOfItems() {
        numberOfItems = 0;
        for (int y = 0; y < level.map.length; y++) {
            for (int x = 0; x < level.map[y].length; x++) {
                if (level.map[y][x] == 2) {
                    numberOfItems++;
                }
            }
        }
    }

    //Selecciona un nivel al azar
    private int[][] selectRandomLevel(){
        int rng = new Random().nextInt(mapList.length);
        return mapList[rng];
    }

    //Inicializa el nivel en función de un array
    private void initWorld() {
        for (int y = 0; y < level.map.length; y++) {
            for (int x = 0; x < level.map[y].length; x++) {
                switch (level.map[y][x]) {
                        case 1:
                            //Add a wall
                            MazeTile wallTile = new MazeTile(gameEngine, true, drawableWall);
                            wallTile.init(gameEngine, x * MARGIN_X, y * MARGIN_Y);
                            gameEngine.addGameObject(wallTile);
                            break;
                        case 2:
                            //Spawn an item
                            MazeItem item = new MazeItem(gameEngine, drawableItem);
                            item.init(x * MARGIN_X, y * MARGIN_Y);
                            gameEngine.addGameObject(item);
                            break;
                        case 3:
                            //Spawn an enemy Horizontal Right
                            MazeEnemy enemyHR = new MazeEnemy(this, gameEngine, drawableEnemy,
                                    true, false);
                            enemyHR.init(gameEngine, x * MARGIN_X, y * MARGIN_Y);
                            gameEngine.addGameObject(enemyHR);
                            break;
                        case 4:
                            //Spawn an enemy Horizontal Left
                            MazeEnemy enemyHL = new MazeEnemy(this, gameEngine, drawableEnemy,
                                    true, true);
                            enemyHL.init(gameEngine, x * MARGIN_X, y * MARGIN_Y);
                            gameEngine.addGameObject(enemyHL);
                            break;
                        case 5:
                            //Spawn an enemy Vertical Down
                            MazeEnemy enemyVR = new MazeEnemy(this, gameEngine, drawableEnemy,
                                    false, false);
                            enemyVR.init(gameEngine, x * MARGIN_X, y * MARGIN_Y);
                            gameEngine.addGameObject(enemyVR);
                            break;
                        case 6:
                            //Spawn an enemy Vertical Up
                            MazeEnemy enemyVL = new MazeEnemy(this, gameEngine, drawableEnemy,
                                    false, true);
                            enemyVL.init(gameEngine, x * MARGIN_X, y * MARGIN_Y);
                            gameEngine.addGameObject(enemyVL);
                            break;
                        case 7:
                            //Spawn main character
                            MazeCharacter character = new MazeCharacter(gameEngine, drawableCharacter, numberOfItems);
                            character.init(x * MARGIN_X, y * MARGIN_Y);
                            gameEngine.addGameObject(character);
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }
