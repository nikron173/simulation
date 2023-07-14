package com.nikron.simulation;

import com.nikron.simulation.piece.statics.Coordinates;

public class RenderMap {
    public void renderMap(Map map){
        for (int i = 0; i < Simulation.height; i++) {
            for (int j = 0; j < Simulation.width; j++) {
                Coordinates temp = new Coordinates(j,i);
                if (map.getEntityMap().containsKey(temp)) {
                    System.out.print(map.getEntityMap().get(temp));
                } else {
                    System.out.print("\uD83D\uDFEB");
                }
            }
            System.out.println();
        }
    }
}
