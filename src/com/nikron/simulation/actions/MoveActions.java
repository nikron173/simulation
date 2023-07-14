package com.nikron.simulation.actions;

import com.nikron.simulation.Map;
import com.nikron.simulation.RenderMap;
import com.nikron.simulation.piece.dynamics.Creature;
import com.nikron.simulation.piece.dynamics.Herbivore;
import com.nikron.simulation.piece.statics.Grass;

public class MoveActions extends Actions {

    @Override
    public void perfActions(Map map) {
        for (var entity : map.getEntityOfType(Creature.class).entrySet()){
            entity.getValue().makeMove(map, (entity.getValue() instanceof Herbivore) ? Grass.class : Herbivore.class);
            System.out.println("Health " + entity.getValue().getClass().getSimpleName() + ": " +entity.getValue().getHealth());
        }
    }
}
