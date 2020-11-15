package com.company;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Quest {
    private static final Logger logger = Logger.getLogger(TeleBot.class.getName());
    public List<Task> allTask;
    public String name;

    public String questDescription;

    public Quest(){
        super();
    }

    public Quest(List<Task> allTask,String questDescription,String name){
        this.allTask = allTask;
        this.questDescription = questDescription;
        this.name = name;
    }

    public static Quest questDeserializer(String filename) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(new File(filename), Quest.class);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Exception", e);
        }
        return null;
    }
    public static void questSerializer(Quest quest,String filename){
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            mapper.writeValue(new File(filename),quest );
        } catch(IOException e) {
            logger.log(Level.SEVERE, "Exception", e);
        }
    }
    public void deleteDependency(Integer taskIndex){
        for(Task task:this.allTask){
            if(task.tasksForAccess.contains(taskIndex)){
                task.tasksForAccess.remove(task.tasksForAccess.indexOf(taskIndex));
            }
        }
    }
}
