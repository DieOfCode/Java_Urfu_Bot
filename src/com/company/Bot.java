package com.company;

import java.util.ArrayList;

public class Bot {
    private User user;
    public Bot(Integer id){
        user = new User(id);
    }

    public String replay(String command){
        if (user.getDialogState() == DialogState.INITIAL){
            switch (command.toLowerCase()){
                case "привет", "/help"-> {return "Привет! Я бот, который сохранит информацию по вашим координатам. Мои команды: запиши, покажи";}
                case "запиши" -> {
                    user.setDialogState(DialogState.WAITING_FOR_MESSAGE);
                    return "Введите сообщение";
                }
                case "покажи"-> {return user.getInfo();}
                default -> {return "Напиши /help";}
            }
        }

        if (user.getDialogState() == DialogState.WAITING_FOR_MESSAGE){
            user.lastMessage = command;
            user.setDialogState(DialogState.WAITING_FOR_COORDINATES);
            return "Введите координаты";
        }

        if (user.getDialogState() == DialogState.WAITING_FOR_COORDINATES){
            var coors = command.split(" ");
            var coordinates = new Coordinates(Double.parseDouble(coors[0]), Double.parseDouble(coors[1]));
            user.setInfoByCoordinates(coordinates, user.lastMessage);
            user.setDialogState(DialogState.INITIAL);
            return "Сообщение записано";
        }
        return "Напиши /help";
    }
}