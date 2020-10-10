package com.company;

import java.util.ArrayList;

public class Bot {
    private User user;
    public Bot(){
        user = new User();
    }

    public String replay(Message inputMessage){
        if (user.getDialogState() == DialogState.INITIAL){
            switch (inputMessage.command.toLowerCase()){
                case "привет", "/help", "/start"-> {
                    return "Привет! Я бот, который сохранит информацию по вашим координатам. Мои команды: запиши, покажи";
                }
                case "запиши" -> {
                    user.setDialogState(DialogState.WAITING_FOR_MESSAGE);
                    return "Введите сообщение";
                }
                case "покажи"-> {return user.getInfo();}
                default -> {return "Напиши /help";}
            }
        }

        if (user.getDialogState() == DialogState.WAITING_FOR_MESSAGE){
            user.lastMessage = inputMessage.command;
            user.setDialogState(DialogState.WAITING_FOR_COORDINATES);
            return "Отправьте геометку";
        }

        if (user.getDialogState() == DialogState.WAITING_FOR_COORDINATES){
            var coordinates = inputMessage.coordinates;
            user.setInfoByCoordinates(coordinates, user.lastMessage);
            user.setDialogState(DialogState.INITIAL);
            return "Сообщение записано";
        }
        return "Напиши /help";
    }
}