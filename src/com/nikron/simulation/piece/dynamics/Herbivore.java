package com.nikron.simulation.piece.dynamics;

import com.nikron.simulation.piece.statics.Coordinates;

public class Herbivore extends Creature {

    public Herbivore(Coordinates coordinates, int speed, int health) {
        super(coordinates, speed, health);
    }

    //все аналогично хищнику, только по отношении травоядного к траве
//    public void makeMove(Map map, Class<?> obstacle) {
//        this.makeMove(map, obstacle);
//    }

    @Override
    public String toString() {
        return "\uD83D\uDC14";
    }
}
