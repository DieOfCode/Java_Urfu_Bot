package com.company;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public class Bot {
    public final User user;
    private static final Logger logger = Logger.getLogger(TeleBot.class.getName());

    public Bot(){
        user = new User();
    }

    public BotAnswer replay(BotMessage inputBotMessage){
        var messageList = new ArrayList<String>();
        logger.info(inputBotMessage.command);
        if (user.getDialogState() == DialogState.INITIAL){
            switch (inputBotMessage.command.toLowerCase()){
                case "привет", "/help", "/start"-> {
                    messageList.add("Привет! Я квестовый бот");
                    return new BotAnswer(messageList, false);
                }
                case "начать квест" -> {
                    user.setDialogState(DialogState.TASK_START);
                    messageList.add("Игра начинается");
                    messageList.add("Местоположение вашего задания");
                    return new BotAnswer(messageList, true);
                }
                case "информация о квесте" -> {
                    messageList.add(user.currentQuest.questDescription);
                    return new BotAnswer(messageList, false);
                }

                default -> {
                    messageList.add("Напиши /help");
                    return new BotAnswer(messageList, false);
                }
            }
        }

        if (user.getDialogState()== DialogState.TASK_START){
            if (inputBotMessage.command.toLowerCase().equals("инфо")){
                messageList.add(user.getCurrentTask().taskDescription);
                messageList.add("Местоположение задания");
                return new BotAnswer(messageList, true);
            }
            if (inputBotMessage.coordinates != null){
                if (user.currentQuest.allTask.get(user.taskEnd).distanceForOffset > Distance.getDistance(inputBotMessage.coordinates, user.currentQuest.allTask.get(user.taskEnd).taskLocation).intValue()){
                    var coordinates = inputBotMessage.coordinates;
                    user.setInfoByCoordinates(coordinates, user.lastMessage);
                    user.setDialogState(DialogState.CHOICE_ACTION);
                    messageList.add("Вы подошли к месту выполнения задания\n\rВы готовы дать ответ или пропустите ");
                    return new BotAnswer(messageList, false);
                }
                messageList.add(String.format("Расстояние до точки %d", Distance
                        .getDistance(inputBotMessage.coordinates, user.currentQuest.allTask.get(user.taskEnd).taskLocation)
                        .intValue()));
                return new BotAnswer(messageList, false);
            }
        }

        if (user.getDialogState() == DialogState.CHOICE_ACTION){
            switch (inputBotMessage.command.toLowerCase()){
                case "ответить"-> {
                    user.setDialogState(DialogState.GIVE_ANSWER);
                    messageList.add(String.format("Напомню вопрос %s", user.currentQuest.allTask.get(user.taskEnd).taskDescription));
                    return new BotAnswer(messageList, false);
                }
                case "пропустить" ->{
                    user.setDialogState(DialogState.TASK_START);
                    user.answeredTask+=1;
                    messageList.add("Не печалься, это был сложный вопрос");
                    messageList.add("Перейдем к след заданию");
                    return new BotAnswer(messageList, false);
                }
                default -> {
                    messageList.add("Не совсем ясно чего вы хотите");
                    return new BotAnswer(messageList, false);
                }
            }
        }

        if(user.getDialogState()==DialogState.GIVE_ANSWER){
            var curTask = user.getCurrentTask();
            if (curTask.checkAnswer(inputBotMessage.command)){
                user.answeredTask += 1;
                user.currentTaskNumber += 1;
                if (user.getCurrentTask() == null){
                    messageList.add("Квест закончен");
                    user.setDialogState(DialogState.INITIAL);
                    return new BotAnswer(messageList, false);
                }
                messageList.add("Перейдем к след заданию");
                user.setDialogState(DialogState.TASK_START);
                return new BotAnswer(messageList, false);
            }

            messageList.add("Попробуй еще раз");
            return new BotAnswer(messageList, false);
        }
        messageList.add("Напиши /help");
        return new BotAnswer(messageList, false);
    }

}

