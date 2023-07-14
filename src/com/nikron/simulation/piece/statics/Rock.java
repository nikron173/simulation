package com.nikron.simulation.piece.statics;

public class Rock extends Entity {
    public Rock(Coordinates coordinates) {
        super(coordinates);
    }

    @Override
    public String toString() {
        return "\uD83E\uDEA8";
    }
}
