package com.nikron.simulation.actions;

import com.nikron.simulation.*;
import com.nikron.simulation.piece.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Actions {
    private Simulation simulation;
    private Random random = new Random();

    public Actions(Simulation simulation){
        this.simulation = simulation;
    }

    // действия, совершаемые перед стартом симуляции. Пример - расставить объекты и существ на карте
    // в данном случае происходит инициализация всех entities объектов, которые будут учавствовать в Симуляции
    public void initActions() {
        List<Entity> grasses = createdGrasses(3);
        List<Entity> rocks = createdRocks(5);
        List<Entity> trees = createdTrees(5);
        List<Entity> herbivores = createdHerbivires(5);
        List<Entity> predators = createdPredators(1);
        List<Entity> entityList = new ArrayList<>();
        entityList.addAll(grasses);
        entityList.addAll(rocks);
        entityList.addAll(trees);
        entityList.addAll(herbivores);
        entityList.addAll(predators);
        simulation.setEntityList(entityList);
        simulation.setCreatureList(entityList.stream().filter(x -> x instanceof Creature).map(x -> (Creature)x).toList());
    }

    // действия, совершаемые каждый ход. Примеры - передвижение существ,
    // добавить травы или травоядных, если их осталось слишком мало
    public void turnActions(){
        makeMove(simulation.getCreatureList());
    }

    // Вызов у каждого движущего объекта метода makeMove()
    public void makeMove(List<Creature> creatures){
        creatures.forEach(x -> {
            for (int i = 0; i < x.getSpeed(); i++) {
                checkerGrasses(simulation.getEntityList()); //происходит проверка, а не закончилась ли трава, если закончилась, то создается и добавляется в общий пул entries
                x.makeMove(simulation.getEntityList());
            }
        });
    }

    //создание травы
    private List<Entity> createdGrasses(int initedGrassesCount){
        List<Entity> grasses = new ArrayList<>();
        for (int i = 0; i < initedGrassesCount; i++) {
            while (true){
                int height = random.nextInt(simulation.getHeight());
                int width = random.nextInt(simulation.getWidth());
                if (Entity.findMapEntity(simulation.getEntityList(), width, height) == null){
                    grasses.add(new Grass(width, height));
                    break;
                }
            }
        }
        return grasses;
    }

    //создание камня
    private List<Entity> createdRocks(int initedGrassesCount){
        List<Entity> rocks = new ArrayList<>();
        for (int i = 0; i < initedGrassesCount; i++) {
            while (true){
                int height = random.nextInt(simulation.getHeight());
                int width = random.nextInt(simulation.getWidth());
                if (Entity.findMapEntity(simulation.getEntityList(), width, height) == null){
                    rocks.add(new Rock(width, height));
                    break;
                }
            }
        }
        return rocks;
    }

    //создание дерева
    private List<Entity> createdTrees(int initedGrassesCount){
        List<Entity> trees = new ArrayList<>();
        for (int i = 0; i < initedGrassesCount; i++) {
            while (true){
                int height = random.nextInt(simulation.getHeight());
                int width = random.nextInt(simulation.getWidth());
                if (Entity.findMapEntity(simulation.getEntityList(), width, height) == null){
                    trees.add(new Tree(width, height));
                    break;
                }
            }
        }
        return trees;
    }

    //создание травоядных
    private List<Entity> createdHerbivires(int initedGrassesCount){
        List<Entity> herbivores = new ArrayList<>();
        for (int i = 0; i < initedGrassesCount; i++) {
            while (true){
                int height = random.nextInt(simulation.getHeight());
                int width = random.nextInt(simulation.getWidth());
                if (Entity.findMapEntity(simulation.getEntityList(), width, height) == null){
                    herbivores.add(new Herbivore(width, height, 1, 3));
                    break;
                }
            }
        }
        return herbivores;
    }

    //создание хищников
    private List<Entity> createdPredators(int initedGrassesCount){
        List<Entity> predators = new ArrayList<>();
        for (int i = 0; i < initedGrassesCount; i++) {
            while (true){
                int height = random.nextInt(simulation.getHeight());
                int width = random.nextInt(simulation.getWidth());
                if (Entity.findMapEntity(simulation.getEntityList(), width, height) == null){
                    predators.add(new Predator(width, height, 1, 3, 1));
                    break;
                }
            }
        }
        return predators;
    }

    //проверка травы, если она закончилась, то создать еще для травоядных
    private void checkerGrasses(List<Entity> entities){
        if (entities.stream().noneMatch(x -> x instanceof Grass)){
            entities.addAll(createdGrasses(3));
        }
    }
}
