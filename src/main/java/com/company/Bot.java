package com.company;

import java.util.ArrayList;
import java.util.logging.Logger;

public class Bot {
    public final User user;
    private static final Logger logger = Logger.getLogger(TeleBot.class.getName());

    public Bot(){
        user = new User();
    }

    public BotAnswer replay(BotMessage inputBotMessage){
        var messageList = new ArrayList<String>();
        logger.info(inputBotMessage.command + " " + user.getDialogState());
        if (user.getDialogState() == DialogState.INITIAL){
            switch (inputBotMessage.command.toLowerCase()){
                case "привет", "/help", "/start"-> {
                    messageList.add("Привет! Я квестовый бот. Мои команды: начать квест, информация о квесте");
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
            if (inputBotMessage.command.toLowerCase().equals("квест инфо")){
                messageList.add(user.getQuestInfo());
                return new BotAnswer(messageList, false);
            }

            if (inputBotMessage.coordinates != null){
                var curDistance = Distance.getDistance(inputBotMessage.coordinates,
                        user.currentQuest.allTask.get(user.currentTaskNumber).taskLocation).intValue();
                var offset = user.currentQuest.allTask.get(user.currentTaskNumber).distanceForOffset;
                if (offset > curDistance){
                    user.setDialogState(DialogState.CHOICE_ACTION);
                    messageList.add("Вы подошли к месту выполнения задания\nВы готовы дать ответ или пропустите?" +
                            "чтобы ответить напишите 'Ответить', чтобы пропустить - 'Пропустить'");
                    return new BotAnswer(messageList, false);
                }
                messageList.add(String.format("Расстояние до точки %d", curDistance));
                return new BotAnswer(messageList, false);
            }
            messageList.add("Для того чтобы узнать информацию о заданий напишите 'инфо', " +
                    "чтобы узнать расстояние до задания отправьте геопозицию");
            return new BotAnswer(messageList, false);
        }

        if (user.getDialogState() == DialogState.CHOICE_ACTION){
            switch (inputBotMessage.command.toLowerCase()){
                case "ответить"-> {
                    user.setDialogState(DialogState.GIVE_ANSWER);
                    messageList.add(String.format("Напомню вопрос %s",
                            user.currentQuest.allTask.get(user.currentTaskNumber).taskDescription));
                    return new BotAnswer(messageList, false);
                }
                case "пропустить" ->{
                    user.setDialogState(DialogState.TASK_START);
                    user.updateTasksInfo(true);
                    messageList.add("Не печалься, это был сложный вопрос");
                    messageList.add("Перейдем к следующему заданию");
                    return new BotAnswer(messageList, false);
                }
                default -> {
                    messageList.add("Я вас не понял :(");
                    return new BotAnswer(messageList, false);
                }
            }
        }

        if(user.getDialogState()==DialogState.GIVE_ANSWER){
            var curTask = user.getCurrentTask();
            if (curTask.checkAnswer(inputBotMessage.command)){
                user.updateTasksInfo(false);
                if (user.getCurrentTask() == null){
                    messageList.add("Квест закончен");
                    user.setDialogState(DialogState.INITIAL);
                    return new BotAnswer(messageList, false);
                }
                messageList.add("Перейдем к следующему заданию");
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

