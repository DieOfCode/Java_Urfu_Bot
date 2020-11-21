package com.company;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
    private DialogState dialogState;
    public HashMap<Coordinates, String> infoByCoordinates = new HashMap<>();
    private Integer skippedTask = 0;
    private Integer answeredTask = 0;
    public Integer currentTaskIndex = 0;
    public Task currentTask;
    public ArrayList<Integer> completedTasks = new ArrayList<>();
    public ArrayList<Task> availableTasks = new ArrayList<>();
    public ArrayList<Task> allTasks;


    public User(Quest quest) {
        dialogState = DialogState.INITIAL;
        allTasks = new ArrayList<>(quest.allTasks);
    }

    public void getAbleTasks(){
        availableTasks.clear();
        for (Task task: allTasks){
            var currentListId = new ArrayList<>(task.tasksForAccess);
            for (Integer id: completedTasks){
                if (currentListId.contains(id)){
                    currentListId.remove(id);
                }
            }
            if (currentListId.isEmpty() && !completedTasks.contains(task.id)){
                availableTasks.add(task);
            }
        }
    }

    public void updateTasksInfo(Boolean isSkipped){
        if (isSkipped) {
            skippedTask += 1;
        } else {
            answeredTask += 1;

        }
        completedTasks.add(currentTask.id);
        if (currentTaskIndex != allTasks.size() - 1){
            currentTaskIndex += 1;
        }
        currentTask = allTasks.get(currentTaskIndex);
    }

    public String getQuestInfo(Quest currentQuest){
        return String.format("Пропущено: %d \nВыполнено: %d \nОсталось: %d",
                skippedTask, answeredTask, currentQuest.allTasks.size());
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