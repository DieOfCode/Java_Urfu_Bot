package com.company;

public class Distance {
    public static Double radius(Double x){
        return x * Math.PI / 180;
    }

    public  static Double getDistance(Coordinates firstPoint,Coordinates secondPoint){
        var R = 6378137; // Earth’s mean radius in meter
        var dLat = radius(secondPoint.y - firstPoint.y);
        var dLong = radius(secondPoint.x - firstPoint.x);
        var a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(radius(firstPoint.x)) * Math.cos(radius(secondPoint.x)) *
                        Math.sin(dLong / 2) * Math.sin(dLong / 2);
        var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c; // returns the distance in meter
    }

}
