package com.company;
import java.util.HashMap;

public class User {
    private DialogState dialogState;
    public int userId;
    public String lastMessage;
    public HashMap<Coordinates, String> infoByCoordinates = new HashMap<>();
    public User(int id) {
        lastMessage = "";
        userId = id;
        dialogState = DialogState.INITIAL;
    }

    public void setInfoByCoordinates(Coordinates coors, String info){
        infoByCoordinates.put(coors, info);
    }

    public String getInfo(){
        return infoByCoordinates.toString();
    }
    public DialogState getDialogState(){
        return dialogState;
    }

    public void setDialogState(DialogState state){
        dialogState = state;
    }
}

enum DialogState{
    INITIAL,
    WAITING_FOR_MESSAGE,
    WAITING_FOR_COORDINATES
}