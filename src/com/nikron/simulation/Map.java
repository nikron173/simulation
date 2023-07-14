package com.nikron.simulation;

import com.nikron.simulation.piece.dynamics.Herbivore;
import com.nikron.simulation.piece.dynamics.Predator;
import com.nikron.simulation.piece.statics.Coordinates;
import com.nikron.simulation.piece.statics.Entity;
import com.nikron.simulation.piece.statics.Grass;

import java.util.HashMap;

public class Map {
    private HashMap<Coordinates, Entity> entityMap = new HashMap<>();

    public HashMap<Coordinates, Entity> getEntityMap() {
        return entityMap;
    }

    public void setPositionEntity(Coordinates coordinates, Entity entity){
        entity.setCoordinates(coordinates);
        entityMap.put(coordinates, entity);
    }

    public void removeEntity(Coordinates coordinates){
        entityMap.remove(coordinates);
    }

    public Entity getEntity(Coordinates coordinates){
        return entityMap.get(coordinates);
    }

    public void makeMove(Coordinates start, Coordinates end){
        Entity entity = entityMap.remove(start);
        setPositionEntity(end, entity);
    }

    public <T> HashMap<Coordinates, T> getEntityOfType(Class<T> typeEntity){
        HashMap<Coordinates, T> res = new HashMap<>();
        for (var entry : getEntityMap().entrySet()){
            if (typeEntity.isInstance(entry.getValue())){
                res.put(entry.getKey(), (T) entry.getValue());
            }
        }
        return res;
    }

    public boolean isExistingOfHerbivores(){
        return entityMap.values().stream().anyMatch(Herbivore.class::isInstance);
    }

    public boolean isExistingOfPredators(){
        return entityMap.values().stream().anyMatch(Predator.class::isInstance);
    }

    public boolean isExistingOfGrass(){
        return entityMap.values().stream().anyMatch(Grass.class::isInstance);
    }

    public void addObject(Entity entity){
        entityMap.put(entity.getCoordinates(), entity);
    }
}
