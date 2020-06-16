package com.example.testingtfg.minigames;

import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

/*Clase que gestiona las colisiones a nivel global*/
public class CollisionManager {

    private static final int MAX_COLLISION_TREES = 12;
    private static int MAX_OBJECTS_TO_CHECK = 300;

    private List<VisualGameObject> gameObjects = new ArrayList<VisualGameObject>();
    private Rect area = new Rect();

    private Rect tmpRect = new Rect();

    private CollisionManager[] children = new CollisionManager[4];

    private static List<CollisionManager> collisions = new ArrayList<CollisionManager>();

    public static void init() {
        collisions.clear();
        for (int i = 0; i < MAX_COLLISION_TREES; i++) {
            collisions.add(new CollisionManager());
        }
    }

    public void setArea(Rect area) {
        area.set(area);
    }

    public void checkObjects(List<VisualGameObject> gameObjects) {
        gameObjects.clear();
        int numObjects = gameObjects.size();
        for (int i = 0; i < numObjects; i++) {
            VisualGameObject current = gameObjects.get(i);
            Rect boundingRect = current.mBoundingRect;
            if (Rect.intersects(boundingRect, area)) {gameObjects.add(current);}
        }
    }

    public void checkCollisions(MinigamesEngine gameEngine, List<Collision> detectedCollisions) {
        int numObjects = gameObjects.size();
        if (numObjects > MAX_OBJECTS_TO_CHECK && collisions.size() >= 4) {
            divideArea(gameEngine, detectedCollisions);
        }
        else {
            for (int i = 0; i < numObjects; i++) {
                VisualGameObject objectA = gameObjects.get(i);
                for (int j = i + 1; j < numObjects; j++) {
                    VisualGameObject objectB = gameObjects.get(j);
                    if (objectA.checkCollision(objectB)) {
                        Collision c = Collision.init(objectA, objectB);
                        if (!hasBeenDetected(detectedCollisions, c)) {
                            detectedCollisions.add(c);
                            objectA.onCollision(gameEngine, objectB);
                            objectB.onCollision(gameEngine, objectA);
                        }
                    }
                }
            }
        }
    }

    private boolean hasBeenDetected(List<Collision> detectedCollisions, Collision c) {
        int numCollisions = detectedCollisions.size();
        for (int i=0; i<numCollisions; i++) {
            if (detectedCollisions.get(i).equals(c)) {return true;}
        }
        return false;
    }

    private void divideArea(MinigamesEngine gameEngine, List<Collision> detectedCollisions) {
        for (int i=0 ; i<4; i++) {children[i] = collisions.remove(0);}
        for (int i=0 ; i<4; i++) {
            children[i].setArea(getArea(i));
            children[i].checkObjects(gameObjects);
            children[i].checkCollisions(gameEngine, detectedCollisions);
            children[i].gameObjects.clear();
            collisions.add(children[i]);
        }
    }

    private Rect getArea(int quadrant) {
        int startX = area.left;
        int startY = area.top;
        int width = area.width();
        int height = area.height();
        switch (quadrant) {
            case 0:
                tmpRect.set(startX, startY, startX + width / 2, startY + height / 2);
                break;
            case 1:
                tmpRect.set(startX + width / 2, startY, startX + width, startY + height / 2);
                break;
            case 2:
                tmpRect.set(startX, startY + height / 2, startX + width / 2, startY + height);
                break;
            case 3:
                tmpRect.set(startX + width / 2, startY + height / 2, startX + width, startY + height);
                break;
        }
        return tmpRect;
    }

    public void addGameObject(VisualGameObject sgo) {
        gameObjects.add(sgo);
    }

    public void removeGameObject(VisualGameObject object) {gameObjects.remove(object);}
}
