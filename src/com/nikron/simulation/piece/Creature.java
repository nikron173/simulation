package com.nikron.simulation.piece;

import com.nikron.simulation.findentitys.Node;

import java.util.List;

public abstract class Creature extends Entity {
    private int speed;
    private int health;

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Creature(int x, int y, int speed, int health) {
        super(x, y);
        this.speed = speed;
        this.health = health;
    }

    public abstract void makeMove(List<Entity> entities);


    //находим самый короткий путь до поедания (если это травоядный, то это самый короткий путь до травы,
    //  если это хищник, то это самый короткий путь до травоядного)
    protected List<Node> findPath(List<Entity> entities, Class<?> obstacle){
        List<Node> path = null;
        List<Node> tmp;
        for (Entity entity : entities.stream().filter(obstacle::isInstance).toList()){
            if (path == null) {
                path = Node.aStar(this, entity, entities, obstacle);
                continue;
            }
            tmp = Node.aStar(this, entity, entities, obstacle);
            if (tmp != null && tmp.size() < path.size()) path = tmp;
        }
        return path;
    }
}
