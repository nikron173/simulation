package com.nikron.simulation;

import com.nikron.simulation.actions.Actions;
import com.nikron.simulation.actions.InitActions;
import com.nikron.simulation.actions.MoveActions;
import com.nikron.simulation.piece.statics.Grass;

public class Simulation {
    public static int width;
    public static int height;

    private RenderMap render = new RenderMap();
    private InitActions initActions = new InitActions();
    private Actions moveActions = new MoveActions();
    private Map map = new Map();

    public Simulation(int width, int height){
        Simulation.width = width;
        Simulation.height = height;
        initActions.perfActions(map);
    }

    //кол-во выполненных шагов симуляции
    private int countMove;

    //просимулировать и отрендерить один ход
    private void nextTurn(){
        try {
            countMove++;
            moveActions.perfActions(map);
            render.renderMap(map);
            Thread.sleep(2000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    //запустить бесконечный цикл симуляции и рендеринга
    public void startSimulation(){
        render.renderMap(map);
        System.out.println();
        while (map.isExistingOfHerbivores() && map.isExistingOfPredators()){
            this.nextTurn();
            System.out.println(countMove);
            if (!map.isExistingOfGrass()){
                map.addObject(initActions.createOneEntity(Grass.class, initActions.freeCoordinatesMap(map)));
                map.addObject(initActions.createOneEntity(Grass.class, initActions.freeCoordinatesMap(map)));
                map.addObject(initActions.createOneEntity(Grass.class, initActions.freeCoordinatesMap(map)));
            }
        }
        if (map.isExistingOfHerbivores()) {
            System.out.println("Победа травоядных");
        } else {
            System.out.println("Победа хищников");
        }
    }

    //приостановить бесконечный цикл симуляции и рендеринга
    public void pauseSimulation(){

    }
}
