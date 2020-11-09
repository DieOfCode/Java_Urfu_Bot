package com.company;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
    private DialogState dialogState;
    public HashMap<Coordinates, String> infoByCoordinates = new HashMap<>();
    public Quest currentQuest = Quest.questDeserializer();
    private Integer skippedTask = 0;
    private Integer answeredTask = 0;
    private Integer taskEnd = 0;
    public Integer currentTaskNumber = 1;
    public ArrayList<Task> doneTasks = new ArrayList<>();

    public User() {
        dialogState = DialogState.INITIAL;
    }

    public Task getCurrentTask(){
        for (Task task:currentQuest.allTask){
            if (task.serialNumber.equals(currentTaskNumber)) {
                return task;
            }
        }
        return null;
    }

    public void updateTasksInfo(Boolean isSkipped){
        if (isSkipped) {
            skippedTask += 1;
        } else {
            answeredTask += 1;
        }
        doneTasks.add(getCurrentTask());
        taskEnd += 1;
        currentTaskNumber += 1;
    }

    public String getQuestInfo(){
        return String.format("Пропущено: %d \nВыполнено: %d \nОсталось: %d",
                skippedTask, answeredTask, currentQuest.allTask.size());
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
    GIVE_ANSWER,
    TASK_START,
    CHOICE_ACTION
}