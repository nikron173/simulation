package com.nikron.simulation;

import java.util.List;

public class Simulation {

    private Actions actions = new Actions(this);
    public static int width;
    public static int height;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    private List<Entity> entityList;
    private List<Creature> creatureList;

    public List<Creature> getCreatureList() {
        return creatureList;
    }

    public void setCreatureList(List<Creature> creatureList) {
        this.creatureList = creatureList;
    }

    public void setEntityList(List<Entity> entityList) {
        this.entityList = entityList;
    }

    public Simulation(int width, int height){
        Simulation.width = width;
        Simulation.height = height;
        actions.initActions();
    }

    //кол-во выполненых шагов симуляции
    private int countMove;

    //просимулировать и отрендерить один ход
    public void nextTurn(){
        try {
            Thread.sleep(1000);
            countMove++;
            actions.turnActions();
            render(entityList);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public List<Entity> getEntityList() {
        return entityList;
    }

    //запустить бесконечный цикл симуляции и рендеринга
    public void startSimulation(){
        render(entityList);
        while (true){
            System.out.println("Осталось хищников: " + entityList.stream().filter(x -> x instanceof Predator).count());
            System.out.println("Осталось травоядных: " + entityList.stream().filter(x -> x instanceof Herbivore).count());
            if (checkerWinPredators() && checkerWinHerbivores()){
                System.out.println("Ничья");
                System.out.println("Выполнено шагов: " + countMove);
                break;
            }
            if (checkerWinHerbivores()){
                System.out.println("Победа травоядных!");
                System.out.println("Выполнено шагов: " + countMove);
                break;
            }
            if (checkerWinPredators()){
                System.out.println("Победа хищников!");
                System.out.println("Выполнено шагов: " + countMove);
                break;
            }
            nextTurn();
        }
    }

    //приостановить бесконечный цикл симуляции и рендеринга
    public void pauseSimulation(){

    }

    //заполнение двумерного массива массива всеми элементами и после их отрисовка сразу
    public void render(List<Entity> entityList){
        char[][] matrix = new char[height][width];
        for (Entity entity : entityList){
            if (entity instanceof Grass) matrix[entity.getY()][entity.getX()] = 'G';
            if (entity instanceof Rock) matrix[entity.getY()][entity.getX()] = 'R';
            if (entity instanceof Tree) matrix[entity.getY()][entity.getX()] = 'T';
            if (entity instanceof Herbivore) matrix[entity.getY()][entity.getX()] = 'H';
            if (entity instanceof Predator) matrix[entity.getY()][entity.getX()] = 'P';
        }
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == '\u0000') matrix[i][j] = '.';
                System.out.print(matrix[i][j]);
            }
            System.out.println();
        }
        System.out.println("===========================================");
    }

    //проверка выиграша травоядных, если на поле не осталось хищников
    private boolean checkerWinHerbivores(){
        return entityList.stream().noneMatch(x -> x instanceof Predator);
    }

    //проверка выиграша хищников, если на поле не осталось травоядных
    private boolean checkerWinPredators(){
        return entityList.stream().noneMatch(x -> x instanceof Herbivore);
    }
}
