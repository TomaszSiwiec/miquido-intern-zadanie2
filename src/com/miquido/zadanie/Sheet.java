package com.miquido.zadanie;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sheet sheet = (Sheet) o;
        return Objects.equals(getX(), sheet.getX()) &&
                Objects.equals(getY(), sheet.getY());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY());
    }
}
