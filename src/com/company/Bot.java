package com.company;

import javax.swing.plaf.synth.SynthStyleFactory;
import java.util.Date;
import java.util.HashMap;

public class Bot {
    public User currentUser;
    private String BotAnswer;

    public Bot(Integer id){
        currentUser = new User(id);
        HashMap<Integer, User> users = new HashMap<>();
        users.put(id, currentUser);
    }

    public String replay(String command){
        switch (command.toLowerCase()){
            case "привет", "/help"-> {
                BotAnswer="Привет! Я бот, который сохранит информацию по вашим координатам";
            }
            case "запиши" -> {
                currentUser.setInfoByCoordinates(new Coordinates(1, 1), "first page");
                BotAnswer= new Date().toString();
            }
            case "покажи"-> {
                BotAnswer =  currentUser.getInfo();
            }
            default-> {
                BotAnswer= "Спроси: 'Какой день?' ";
            }
        }
        currentUser.UserState.listOfCommand.push(new HashMap<>(){{
            put(command.toLowerCase(),BotAnswer);
        }});
        System.out.println(currentUser.UserState.getLastCommand());
        return BotAnswer;

    }
}