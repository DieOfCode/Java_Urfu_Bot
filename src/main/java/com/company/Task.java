package com.company;

import java.util.ArrayList;

public class Task {
    public String name;
    public String taskDescription;
    public Coordinates taskLocation;
    public Integer distanceForOffset;
    public ArrayList<String> taskAnswer;
    public Integer id;

    public ArrayList<Integer> tasksForAccess;


    public Task() {
        super();
    }

    public Task(String taskDescription, Coordinates taskLocation, Integer distanceForOffset, ArrayList<String> taskAnswer, Integer id, ArrayList<Integer> tasksForAccess,String name) {
        this.taskDescription = taskDescription;
        this.taskLocation = taskLocation;
        this.distanceForOffset = distanceForOffset;
        this.taskAnswer = taskAnswer;
        this.id = id;
        this.tasksForAccess = tasksForAccess;
        this.name = name;
    }

    public Boolean checkAnswer(String answer){
        return taskAnswer.contains(answer);
    }
}