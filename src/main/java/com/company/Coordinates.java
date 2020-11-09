package com.company;

public class Coordinates {
    Double x;
    Double y;
    public Coordinates (Double x, Double y){
        this.x = x;
        this.y = y;
    }

    public Coordinates() {
        super();
    }

    public Double getX() {
        return x;
    }

    public Double getY() {
        return y;
    }

    public String toString(){
        return String.format("%f, %f", x, y);
    }

    @Override
    public int hashCode(){
        return (int)(x * Math.pow(2, 11) + y * Math.pow(2, 10));
    }
}
