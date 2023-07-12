package com.nikron.simulation.piece;

import com.nikron.simulation.findentitys.Node;

import java.util.List;

public class Herbivore extends Creature {

    public Herbivore(int x, int y, int speed, int health) {
        super(x, y, speed, health);
    }

    //все аналогично хищнику, только по отношении травоядного к траве
    public void makeMove(List<Entity> entities) {
        List<Node> path = this.findPath(entities, Grass.class);
        this.setHealth(getHealth() - 1);
        if (path == null || path.size() == 0) {
        } else if (path.size() == 1){
            Entity entity = findMapEntity(entities, path.get(0).getX(), path.get(0).getY());
            entities.remove(entity);
            this.setHealth(getHealth() + 2);
            this.setX(entity.getX());
            this.setY(entity.getY());
        } else {
            if (this.getHealth() == 0) {
                entities.remove(this);
            } else {
                this.setX(path.get(path.size() - 1).getX());
                this.setY(path.get(path.size() - 1).getY());
            }
        }
    }
}
