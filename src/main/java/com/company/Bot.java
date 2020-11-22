package com.company;

import java.util.ArrayList;
import java.util.logging.Logger;

import static com.company.DialogState.*;

public class Bot {
    public Quest quest;
    public final User user;
    private static final Logger logger = Logger.getLogger(TeleBot.class.getName());

    public Bot(Quest quest){
        this.quest = quest;
        user = new User(quest);
    }

    public BotAnswer replay(BotMessage inputBotMessage){
        var messageList = new ArrayList<String>();
        logger.info(inputBotMessage.command + " " + user.getDialogState());
        return switch (user.getDialogState()){
            case INITIAL -> initializeBot(inputBotMessage, messageList);
            case TASK_START-> startTask(inputBotMessage, messageList);
            case CHOICE_TASK -> choiceTask(inputBotMessage, messageList);
            case SHOW_AVAILABLE_TASK ->showAvailableTask(inputBotMessage, messageList);
            case GIVE_ANSWER -> getAnswer(inputBotMessage, messageList);
            case CHOICE_ACTION ->  getChoiceAction(inputBotMessage, messageList);
        };
    }

    private BotAnswer showAvailableTask(BotMessage inputBotMessage, ArrayList<String> messageList){
        user.getAbleTasks();
        if(user.availableTasks.size() ==1){
            user.currentTask= user.allTasks.get(user.currentTaskIndex);
            messageList.add("Задание начинается");
            messageList.add("Местоположение вашего задания");
            user.setDialogState(TASK_START);
            return new BotAnswer(messageList, true);
        }
        else if (user.availableTasks.size()>1){
            messageList.add("Выберете задание");
            for(Task task: user.availableTasks){
                messageList.add(String.format("Название: %s Номер: %d \n",
                        task.name, task.id));
            }
            user.setDialogState(CHOICE_TASK);
        }
        return new BotAnswer(messageList, false);
    }
    private BotAnswer choiceTask(BotMessage inputBotMessage, ArrayList<String> messageList){
        logger.info(inputBotMessage.command);
        for(Task task:user.availableTasks){
            if(task.id == Integer.parseInt(inputBotMessage.command)){
                logger.info(task.id.toString());
                logger.info(task.name);
                user.currentTaskIndex = task.id - 1;
                user.currentTask = task;
                messageList.add("Задание начинается");
                user.setDialogState(TASK_START);
                messageList.add("Местоположение вашего задания");
                return new BotAnswer(messageList, true);
            }
        }
        messageList.add("Что-то пошло не так");
        return new BotAnswer(messageList, false);
    }

    private BotAnswer getAnswer(BotMessage inputBotMessage, ArrayList<String> messageList) {
        var curTask = user.currentTask;
        if (curTask.checkAnswer(inputBotMessage.command)){
            user.updateTasksInfo(false);
            if (user.completedTasks.size() == user.allTasks.size()){
                messageList.add("Квест закончен");
                user.setDialogState(INITIAL);
                return new BotAnswer(messageList, false);
            }
            messageList.add("Перейдем к следующему заданию");
            user.setDialogState(TASK_START);
            return new BotAnswer(messageList, false);
        }

        messageList.add("Попробуй еще раз");
        return new BotAnswer(messageList, false);
    }

    private BotAnswer getChoiceAction(BotMessage inputBotMessage, ArrayList<String> messageList) {
        switch (inputBotMessage.command.toLowerCase()){
            case "ответить"-> {
                user.setDialogState(GIVE_ANSWER);
                messageList.add(String.format("Внимание, вопрос: %s",
                        quest.allTasks.get(user.currentTaskIndex).taskDescription));
                return new BotAnswer(messageList, false);
            }
            case "пропустить" ->{
                user.setDialogState(TASK_START);
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

    private BotAnswer startTask(BotMessage inputBotMessage, ArrayList<String> messageList) {
        if (inputBotMessage.command.toLowerCase().equals("информация о квесте")){
            messageList.add(user.currentTask.taskDescription);
            messageList.add("Местоположение задания");
            return new BotAnswer(messageList, true);
        }
        if (inputBotMessage.command.toLowerCase().equals("текущий прогресс")){
            messageList.add(user.getQuestInfo(quest));
            return new BotAnswer(messageList, false);
        }

        if (inputBotMessage.command.toLowerCase().equals("изменить задание")){
            return showAvailableTask(inputBotMessage, messageList);
        }

        if (inputBotMessage.coordinates != null){
            var currentDistance = Distance.getDistance(inputBotMessage.coordinates,
                    quest.allTasks.get(user.currentTaskIndex).taskLocation).intValue();
            var offset = quest.allTasks.get(user.currentTaskIndex).distanceForOffset;
            if (offset > currentDistance){
                user.setDialogState(CHOICE_ACTION);
                messageList.add("Вы подошли к месту выполнения задания\nВы готовы дать ответ или пропустите?" +
                        "чтобы ответить напишите 'Ответить', чтобы пропустить - 'Пропустить'");
                return new BotAnswer(messageList, false);
            }
            messageList.add(String.format("Расстояние до точки %d", currentDistance));
            return new BotAnswer(messageList, false);
        }
        messageList.add("Для того чтобы узнать информацию о заданий напишите 'инфо', " +
                "чтобы узнать расстояние до задания отправьте геопозицию, чтобы выбрать задание напишите: 'выбор'");
        return new BotAnswer(messageList, false);
    }

    private BotAnswer initializeBot(BotMessage inputBotMessage, ArrayList<String> messageList) {
        switch (inputBotMessage.command.toLowerCase()){
            case "привет", "/help", "/start"-> {
                messageList.add("Привет! Я квестовый бот. Мои команды: начать квест, информация о квесте");
                return new BotAnswer(messageList, false);
            }
            case "квест" -> {
                messageList.add("Введите старт, чтобы подтвердить свои действия");
                user.setDialogState(SHOW_AVAILABLE_TASK);
                return new BotAnswer(messageList, false);
            }
            case "информация о квесте" -> {
                messageList.add(quest.questDescription);
                return new BotAnswer(messageList, false);
            }

            default -> {
                messageList.add("Напиши /help");
                return new BotAnswer(messageList, false);
            }
        }
    }
}