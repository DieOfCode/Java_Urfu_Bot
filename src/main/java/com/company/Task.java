package com.company;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Task {
    public String name;
    public String taskDescription;
    public Coordinates taskLocation;
    public Integer distanceForOffset;
    public ArrayList<String> taskAnswer;
    public Integer serialNumber;
    public Boolean complete=false;
    public ArrayList<Integer> tasksForAccess;


    public Task() {
        super();
    }

    public Task(String taskDescription, Coordinates taskLocation, Integer distanceForOffset, ArrayList<String> taskAnswer, Integer serialNumber,ArrayList<Integer> tasksForAccess) {
        this.taskDescription = taskDescription;
        this.taskLocation = taskLocation;
        this.distanceForOffset = distanceForOffset;
        this.taskAnswer = taskAnswer;
        this.serialNumber = serialNumber;
        this.tasksForAccess = tasksForAccess;
    }

    public Boolean checkAnswer(String answer){
        return taskAnswer.contains(answer);
    }
}