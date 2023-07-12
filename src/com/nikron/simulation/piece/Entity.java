package com.nikron.simulation.piece;

import java.util.List;

public abstract class Entity {
    private int x;
    private int y;

    public Entity(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    //находит по координатам x и y объект entity, если такого нет, то возвращает null
    public static Entity findMapEntity(List<Entity> entityList, int x, int y){
        if (entityList == null) return null;
        return entityList.stream().filter(obj -> obj.getX() == x && obj.getY()==y).findFirst().orElse(null);
    }

}
