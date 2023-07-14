package com.nikron.simulation.actions;

import com.nikron.simulation.Map;
import com.nikron.simulation.Simulation;
import com.nikron.simulation.piece.dynamics.Herbivore;
import com.nikron.simulation.piece.dynamics.Predator;
import com.nikron.simulation.piece.statics.*;

import java.util.Random;

public class InitActions extends Actions{

    private Random random = new Random();

    @Override
    public void perfActions(Map map) {
        Coordinates coordinates;
        for (int i = 0; i < 5; i++) {
            coordinates = freeCoordinatesMap(map);
            map.getEntityMap().put(coordinates, createOneEntity(Grass.class, coordinates));
        }
        for (int i = 0; i < 5; i++) {
            coordinates = freeCoordinatesMap(map);
            map.getEntityMap().put(coordinates, createOneEntity(Tree.class, coordinates));
        }
        for (int i = 0; i < 5; i++) {
            coordinates = freeCoordinatesMap(map);
            map.getEntityMap().put(coordinates, createOneEntity(Rock.class, coordinates));
        }
        for (int i = 0; i < 5; i++) {
            coordinates = freeCoordinatesMap(map);
            map.getEntityMap().put(coordinates, createOneEntity(Herbivore.class, coordinates));
        }
        for (int i = 0; i < 3; i++) {
            coordinates = freeCoordinatesMap(map);
            map.getEntityMap().put(coordinates, createOneEntity(Predator.class, coordinates));
        }
    }

    public Entity createOneEntity(Class<?> clazz, Coordinates coordinates){
        switch (clazz.getSimpleName()) {
            case "Grass" -> {
                return new Grass(coordinates);
            }
            case "Tree" -> {
                return new Tree(coordinates);
            }
            case "Rock" -> {
                return new Rock(coordinates);
            }
            case "Herbivore" -> {
                return new Herbivore(coordinates, 1, 7);
            }
            case "Predator" -> {
                return new Predator(coordinates, 1, 5, 1);
            }
            default -> {
                return null;
            }
        }
    }

    public Coordinates freeCoordinatesMap(Map map){
        Coordinates coordinates;
        while (true) {
            int height = random.nextInt(Simulation.height);
            int width = random.nextInt(Simulation.width);
            coordinates = new Coordinates(width, height);
            if (!map.getEntityMap().containsKey(coordinates)) return coordinates;
        }
    }
}
