package com.nikron.simulation.piece.dynamics;

import com.nikron.simulation.Map;
import com.nikron.simulation.findentitys.Node;
import com.nikron.simulation.piece.statics.Coordinates;
import com.nikron.simulation.piece.statics.Entity;

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

    public Creature(Coordinates coordinates, int speed, int health) {
        super(coordinates);
        this.speed = speed;
        this.health = health;
    }

    public void makeMove(Map map, Class<?> obstacle){
        this.setHealth(this.health-1);
        List<Coordinates> path = findPath(map, obstacle);
        if (path == null || path.isEmpty()) {
            if (this.health == 0){
                map.removeEntity(this.getCoordinates());
            }
            return;
        }
        for (int i = 0; i < this.speed; i++) {
            if (path.size() - 1 == i) {
                if (this instanceof Predator predator){
                    Creature entityHerbivore = (Creature) map.getEntity(path.get(0));
                    entityHerbivore.setHealth(entityHerbivore.getHealth() - predator.getAttackPower());
                    if (entityHerbivore.health <= 0){
                        map.makeMove(this.getCoordinates(), path.get(i));
                        this.setHealth(this.health + 1);
                    }
                } else {
                    map.makeMove(this.getCoordinates(), path.get(i));
                    this.setHealth(this.health + 1);
                }
            } else {
                if (this.health == 0){
                    map.removeEntity(this.getCoordinates());
                } else {
                    map.makeMove(this.getCoordinates(), path.get(path.size() - 1));
                }
            }
        }
    }


    //находим самый короткий путь до поедания (если это травоядный, то это самый короткий путь до травы,
    //  если это хищник, то это самый короткий путь до травоядного)
    private List<Coordinates> findPath(Map map, Class<?> obstacle){
        List<Coordinates> path = null;
        List<Coordinates> tmp;
        for (var entity : map.getEntityMap().entrySet()){
            if (!obstacle.isInstance(entity.getValue())) continue;
            if (path == null) {
                path = Node.aStar(this.getCoordinates(), entity.getKey(), map.getEntityMap(), obstacle);
                continue;
            }
            tmp = Node.aStar(this.getCoordinates(), entity.getKey(), map.getEntityMap(), obstacle);
            if (tmp != null && tmp.size() < path.size()) path = tmp;
        }
        return path;
    }
}
