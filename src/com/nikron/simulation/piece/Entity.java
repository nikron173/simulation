package com.nikron.simulation.piece;

import java.util.List;

public abstract class Entity {
    private Coordinates coordinates;

    public Entity(Coordinates coordinates){
        this.coordinates = coordinates;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    //находит по координатам x и y объект entity, если такого нет, то возвращает null
    public static Entity findMapEntity(List<Entity> entityList, int x, int y){
        if (entityList == null) return null;
        return entityList.stream().filter(obj -> obj.getX() == x && obj.getY()==y).findFirst().orElse(null);
    }

}
