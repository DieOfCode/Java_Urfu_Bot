package com.company;

import org.telegram.telegrambots.meta.api.methods.send.SendLocation;

import javax.swing.*;
import java.nio.file.DirectoryNotEmptyException;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class User {
    private DialogState dialogState;
    public String lastMessage;
    public HashMap<Coordinates, String> infoByCoordinates = new HashMap<>();
    public String broadcastingMessage;
    public Boolean broadcasting ;
    public Quest currentQuest = Quest.questDeserializer();
    public Integer skippedTask = 0;
    public Integer answeredTask = 0;
    public Integer taskEnd = skippedTask+answeredTask;
    public Integer currentTaskNumber = 1;

    public User() {
        lastMessage = "";
        broadcastingMessage = "";
        dialogState = DialogState.INITIAL;
        broadcasting = false;

    }

    public Task getCurrentTask(){
        for (Task task:currentQuest.allTask){
            if (task.serialNumber.equals(currentTaskNumber)) {
                return task;
            }
        }
        return null;
    }

    public void setInfoByCoordinates(Coordinates coors, String info){
        infoByCoordinates.put(coors, info);
    }

    public String getInfo(){
        return infoByCoordinates.toString();
    }
    public Coordinates getLastCoordinate() {
        Coordinates broadcastCoordinate = null;
        for(Coordinates lastCoordinate:infoByCoordinates.keySet()){
            broadcastCoordinate = lastCoordinate;
        };
        return broadcastCoordinate;}
    public DialogState getDialogState(){
        return dialogState;
    }

    public void setDialogState(DialogState state){
        dialogState = state;
    }
    public void makeTranslation(Integer time,Long ID){
        var sendLocation =new SendLocation();
        sendLocation.setLatitude(getLastCoordinate().x.floatValue());
        sendLocation.setLongitude(getLastCoordinate().y.floatValue());
        sendLocation.setLivePeriod(time);
        sendLocation.setChatId(ID);


    }
}

enum DialogState{
    INITIAL,
    GIVE_ANSWER,
    TASK_START,
    CHOICE_ACTION
}