package com.miquido.zadanie;

public class Sheet {
    private Integer x;
    private Integer y;

    public Sheet(Integer x, Integer y) {
        this.x = x;
        this.y = y;
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

    @Override
    public String toString() {
        return "Sheet{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
