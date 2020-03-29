package com.miquido.zadanie;

public class Element{

    private Integer id;
    private Integer x;
    private Integer y;
    private Integer points;
    private Integer area;
    private Direction direction;
    private Double profitability;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Double getProfitability() {
        return profitability;
    }

    public void setProfitability(Double profitability) {
        this.profitability = profitability;
    }

    public Element(Integer id, Integer x, Integer y, Integer points) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.area = x * y;
        this.points = points;
        this.direction = getDirection(x, y);
        this.profitability = points.doubleValue() / this.area.doubleValue();
    }

    private Direction getDirection(Integer x, Integer y) {
        if (x > y) {
            return Direction.HORIZONTAL;
        }

        if (x < y) {
            return Direction.VERTICAL;
        }
        return Direction.VERTICAL;
    }

    public void swap() {
        Integer tmp = x;
        this.x = this.y;
        this.y = tmp;
        this.direction = (this.direction == Direction.VERTICAL) ? Direction.HORIZONTAL : Direction.VERTICAL;
    }

    @Override
    public String toString() {
        return "{ Element with ID: " + this.id +  ", X: " + this.x + ", Y:" + this.y + ", Points: " + this.points +
        ", Direction: " + this.direction.toString() + ", Profitability: " + this.profitability + " }";
    }
}
