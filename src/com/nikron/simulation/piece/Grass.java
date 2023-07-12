package com.nikron.simulation.piece;

public class Grass extends Entity {

    public Grass(Coordinates coordinates) {
        super(coordinates);
    }

    @Override
    public String toString() {
        return "\uD83C\uDF40";
    }
}
