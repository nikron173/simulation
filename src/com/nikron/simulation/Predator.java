package com.nikron.simulation;

import java.util.List;

public class Predator extends Creature {
    private int attackPower;

    public Predator(int x, int y, int speed, int health, int attackPower) {
        super(x, y, speed, health);
        this.attackPower = attackPower;
    }

    @Override
    public void makeMove(List<Entity> entities) {
        List<Node> path = findPath(entities, Herbivore.class);
        this.setHealth(getHealth() - 1);
        //если путь возвращает null или длину равную 0, то на поле закончились травоядные и игра может быть закончена
        //либо травоядные недостяжимы для хищника
        if (path == null || path.size() == 0) {
            return;
        } else if (path.size() == 1){
            //если путь равен единице, значит хищник достиг травоядного и происходит съедение (удаление) травоядного и передвижение хищника на место травоядного,если здоровье меньше, либо равно нулю
            //иначе происходит удар по травоядному
            Creature entity = (Creature)findMapEntity(entities, path.get(0).getX(), path.get(0).getY());
            entity.setHealth(entity.getHealth() - this.attackPower);
            if (entity.getHealth() <= 0){
                entities.remove(entity);
            }
            this.setHealth(getHealth() + 2);
            this.setX(entity.getX());
            this.setY(entity.getY());
        } else {
            //если здоровье хищника опускается до нуля, то хищник умирает, либо передвигается на шаг к травоядному
            if (this.getHealth() == 0) {
                entities.remove(this);
            } else {
                this.setX(path.get(path.size() - 1).getX());
                this.setY(path.get(path.size() - 1).getY());
            }
        }
    }

}
