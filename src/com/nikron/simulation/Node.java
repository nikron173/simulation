package com.nikron.simulation;

import java.util.*;

public class Node {
    private int x; //Координата x узла на карте
    private int y; //Координата y узла на карте
    private double g; //Расстояние от начального узла до текущего узла
    private double h; //Примерное расстояние от текущего узла до конечного узла
    private double f; //Сумма g и h
    private Node parent; //Родительский узел, используется для восстановления пути

    public Node(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static Comparator<Node> idComparator = Comparator.comparingDouble(node1 -> node1.f);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return x == node.x && y == node.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public double getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public double getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public double getF() {
        return f;
    }

    public void setF(int f) {
        this.f = f;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node node) {
        this.parent = node;
    }

    //реализация алгоритма a*
    //T start - точка, откуда будет происходить движение
    //T end - конечная точка
    //List<Entity> entities - список всех объектов
    //Class<?> obstacle - имя класса, которое не является препятствием
    public static <T> List<Node> aStar(T start, T end, List<com.nikron.simulation.Entity> entities, Class<?> obstacle){
        Node startNode = null;
        Node endNode = null;

        if (start instanceof com.nikron.simulation.Entity
                && end instanceof com.nikron.simulation.Entity) {
            com.nikron.simulation.Entity st =  (com.nikron.simulation.Entity) start;
            startNode = new Node(st.getX(), st.getY());
            com.nikron.simulation.Entity ed = (com.nikron.simulation.Entity) end;
            endNode = new Node(ed.getX(), ed.getY());
        }

        Queue<Node> queue = new PriorityQueue<>(idComparator);
        queue.add(startNode);
        Set<Node> closedNode = new HashSet<>();

        while (!queue.isEmpty()){
            Node currentNode = queue.poll();

            if (currentNode.equals(endNode)) {
                List<Node> path = new ArrayList<>();
                while (currentNode != null) {
                    path.add(currentNode);
                    currentNode = currentNode.parent;
                }
                path.remove(path.size()-1);
                return path;
            }

            closedNode.add(currentNode);
            List<Node> neighbors = new ArrayList<>();
            for (int dx = -1; dx < 2; dx++) {
                for (int dy = -1; dy < 2; dy++) {
                    if (dx == 0 && dy == 0) continue;
                    int x = currentNode.x + dx;
                    int y = currentNode.y + dy;

                    if (x < 0 || x >= Simulation.width || y < 0 || y >= Simulation.height) continue;
                    com.nikron.simulation.Entity tmp;
                    //все является препятствием, кто переданного класса
                    if ((tmp = com.nikron.simulation.Entity.findMapEntity(entities, x, y)) != null && !(obstacle.isInstance(tmp))) continue;
                    //if ((com.nikron.simulation.Entity.findMapEntity(entities, x, y)) != null) continue;
                    neighbors.add(new Node(x, y));
                }
            }

            for(Node neighbor : neighbors){
                if (closedNode.contains(neighbor)) continue;

                double new_g = currentNode.g + Math.sqrt(Math.pow((neighbor.x - currentNode.x), 2) + Math.pow((neighbor.y - currentNode.y), 2));

                if (queue.contains(neighbor)){
                    if (new_g < neighbor.g){
                        neighbor.g = new_g;
                        neighbor.h = Math.sqrt(Math.pow((endNode.x - neighbor.x), 2) + Math.pow((endNode.y - neighbor.y), 2));
                        neighbor.f = neighbor.g + neighbor.h;
                        neighbor.parent = currentNode;
                    }
                } else {
                    neighbor.g = new_g;
                    neighbor.h = Math.sqrt(Math.pow((endNode.x - neighbor.x), 2) + Math.pow((endNode.y - neighbor.y), 2));
                    neighbor.f = neighbor.g + neighbor.h;
                    neighbor.parent = currentNode;
                    queue.add(neighbor);
                }
            }
        }
        return null;
    }
}
