package com.company;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
    private DialogState dialogState;
    public HashMap<Coordinates, String> infoByCoordinates = new HashMap<>();
    private Integer skippedTask = 0;
    private Integer answeredTask = 0;
    public Integer currentTaskIndex = 0;
    public Integer a;
    public Task currentTask;
    public ArrayList<Task> availableTask = new ArrayList<>();
    

    public User() {
        dialogState = DialogState.INITIAL;
    }

    public void getAvailableTasks(Quest currentQuest){
        for (Task task:currentQuest.allTask){
            if (task.tasksForAccess.isEmpty() && !task.complete ) {
                System.out.println(task);
                availableTask.add(task);
            }
        }
    }

    public void updateTasksInfo(Boolean isSkipped, Quest currentQuest){
        if (isSkipped) {
            skippedTask += 1;
        } else {
            answeredTask += 1;

        }
        availableTask.clear();
        currentTask.complete = true;
        currentTask = null;
        currentQuest.deleteDependency(currentTaskIndex);
        currentTaskIndex += 1;
    }

    public String getQuestInfo(Quest currentQuest){
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
    CHOICE_ACTION,
    CHOICE_TASK,
    SHOW_AVAILABLE_TASK
}