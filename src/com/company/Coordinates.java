package com.company;

public class Coordinates {
    Double x;
    Double y;
    public Coordinates (Double x, Double y){
        this.x = x;
        this.y = y;
    }

    public String toString(){
        return String.format("(%f, %f)", x, y);
    }

    @Override
    public int hashCode(){
        return (int)(x * 1000 + y * 1000);
    }
}
