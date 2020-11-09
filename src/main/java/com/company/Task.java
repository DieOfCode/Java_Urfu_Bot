package com.company;

public class Task {
    public String taskDescription;
    public Coordinates taskLocation;
    public Integer distanceForOffset;
    public String taskAnswer;
    public Integer serialNumber;

    public Task() {
        super();
    }

    public Task(String taskDescription, Coordinates taskLocation, Integer distanceForOffset, String taskAnswer, Integer serialNumber) {
        this.taskDescription = taskDescription;
        this.taskLocation = taskLocation;
        this.distanceForOffset = distanceForOffset;
        this.taskAnswer = taskAnswer;
        this.serialNumber = serialNumber;

    }

}