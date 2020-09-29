package com.company;
import java.util.HashMap;

public class User {
    public int userId;
    public HashMap<Coordinates, String> infoByCoordinates = new HashMap<>();
    public User(int id) {
        userId = id;
    }

    public void setInfoByCoordinates(Coordinates coors, String info){
        infoByCoordinates.put(coors, info);
    }

    public String getInfo(){
        return infoByCoordinates.toString();
    }

}
