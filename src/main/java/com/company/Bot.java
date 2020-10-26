package com.company;

import com.fasterxml.jackson.databind.JsonSerializer;
import jdk.jshell.spi.ExecutionControl;

import javax.swing.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Bot {
    private final User user;
    public Bot(){
        user = new User();
    }

    public String replay(BotMessage inputBotMessage) throws ExecutionControl.NotImplementedException {
        System.out.println(inputBotMessage.command);

            if (user.getDialogState() == DialogState.INITIAL) {
                switch (inputBotMessage.command.toLowerCase()) {
                    case "привет", "/help", "/start" -> {
                        return "Привет! Я бот, который сохранит информацию по вашим координатам. Мои команды: запиши, покажи";
                    }
                    case "запиши" -> {
                        user.setDialogState(DialogState.WAITING_FOR_MESSAGE);
                        return "Введите сообщение";
                    }
                    case "редактировать" -> {
                        if (user.broadcasting) {
                            user.setDialogState(DialogState.EDIT_BROADCAST_MESSAGE);
                            return "Измените сообщение трансляции";
                        }
                        return "Вы не начинали трансляцию";
                    }
                    case "трансляция" -> {
                        return String.format("%s", user.broadcastingMessage);
                    }
                    case "покажи" -> {
                        return user.getInfo();
                    }
                    case "прими" -> {
                        user.setDialogState(DialogState.RECEIVE_CUR_LOCATION);
                        return "готов";
                    }
                    default -> {
                        return " /help";
                    }
                }
            }
            if (user.getDialogState() == DialogState.WAITING_FOR_MESSAGE) {
                var preparedMessage = inputBotMessage.command;
                var pattern = Pattern.compile("(?<message>.+) (?<command>\\w+).*", Pattern.UNICODE_CHARACTER_CLASS);
                var m = pattern.matcher(preparedMessage);
                if (m.find()) {
                    if ("запиши".equals(m.group("command").toLowerCase())) {
                        user.setDialogState(DialogState.WAITING_FOR_COORDINATES);
                        user.lastMessage = m.group("message");
                    }
                    if ("транслировать".equals(m.group("command").toLowerCase())) {
                        user.setDialogState(DialogState.RECEIVE_CUR_LOCATION);
                        user.broadcasting = true;
                        user.broadcastingMessage = m.group("message");
                    } else {
                        return "Непонятно, что сделать с сообщением";
                    }
                }
                return "Отправьте геометку";
            }

            if (user.getDialogState() == DialogState.WAITING_FOR_COORDINATES) {
                var coordinates = inputBotMessage.coordinates;
                user.setInfoByCoordinates(coordinates, user.lastMessage);
                user.setDialogState(DialogState.INITIAL);
                return "Сообщение записано";
            }

            if (user.getDialogState() == DialogState.RECEIVE_CUR_LOCATION) {
                var currentCoors = inputBotMessage.coordinates;
                user.setDialogState(DialogState.INITIAL);
                return String.format("%s %.8f %.8f \r\n %s", user.broadcastingMessage, currentCoors.x, currentCoors.y, "Напиши " +
                        "редактировать, если хочешь изменить сообщение трансляции или созадть, чтобы записать сообщение для " +
                        "новой геометки ");

            }
            if (user.getDialogState() == DialogState.EDIT_BROADCAST_MESSAGE) {
                user.broadcastingMessage = inputBotMessage.command;
                user.setDialogState(DialogState.INITIAL);
            }
            return "Напиши /help";
        }
    }
