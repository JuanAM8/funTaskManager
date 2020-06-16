package com.example.testingtfg.minigames;

import java.util.ArrayList;
import java.util.List;

/*Clase que gestiona las colisiones*/
public class Collision {

    private static List<Collision> collisions = new ArrayList<Collision>();
    public VisualGameObject objA;
    public VisualGameObject objB;

    public Collision(VisualGameObject objA, VisualGameObject objB){
        this.objA = objA;
        this.objB = objB;
    }

    public static Collision init(VisualGameObject objA, VisualGameObject objB){
        if (collisions.isEmpty()){
            return new Collision(objA, objB);
        }
        return collisions.remove(0);
    }

    public static void release(Collision c){
        collisions.add(c);
    }

    public boolean equals(Collision c){
        return (objA == c.objA && objB == c.objB) || (objA == c.objB && objB == c.objA);
    }
}
