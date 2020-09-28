package com.company;

public class Coordinates {
    Integer x;
    Integer y;
    public Coordinates (Integer x, Integer y){
        this.x = x;
        this.y = y;
    }

    public String toString(){
        return String.format("(%d, %d)", x, y);
    }
}
