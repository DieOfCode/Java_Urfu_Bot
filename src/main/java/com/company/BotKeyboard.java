package com.company;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;

public class BotKeyboard {
    ReplyKeyboardMarkup keyboard;
    Bot bot;
    ArrayList<KeyboardRow> allRow= new ArrayList<>();
    KeyboardRow firstRow =new KeyboardRow();
    KeyboardRow secondRow = new KeyboardRow();

    BotKeyboard(ReplyKeyboardMarkup keyboard,Bot bot){
        this.keyboard =keyboard;
        this.bot= bot;
        keyboard.setResizeKeyboard(true);
        keyboard.setSelective(true);
    }

    public void getKeyboard(){
        switch (bot.user.getDialogState()){
            case INITIAL -> initializeKeyboard();
            case TASK_START-> startKeyboard();
            case CHOICE_TASK -> choiceTaskKeyboard();
            case SHOW_AVAILABLE_TASK ->showKeyboard();
            case GIVE_ANSWER -> getAnswerKeyboard();
            case CHOICE_ACTION ->  getChoiceActionKeyboard();
        };
    }

    public void initializeKeyboard(){
        firstRow.add("Квест");
        firstRow.add("Информация о квесте");
        allRow.add(firstRow);
        keyboard.setKeyboard(allRow);
    }

    public void startKeyboard(){
        firstRow.add("Информация о квесте");
        firstRow.add("Текущий прогресс");
        secondRow.add("Изменить задание");
        allRow.add(firstRow);
        allRow.add(secondRow);
        keyboard.setKeyboard(allRow);

    }
    public void choiceTaskKeyboard(){
        for (Task task : bot.user.availableTasks) {
            firstRow.add(String.format("%d", task.id));
        }
        allRow.add(firstRow);
        keyboard.setKeyboard(allRow);

    }
    public void showKeyboard(){
        throw new UnsupportedOperationException();

    }
    public void getAnswerKeyboard(){
        throw new UnsupportedOperationException();
    }
    public void getChoiceActionKeyboard(){
        firstRow.add("Ответить");
        firstRow.add("Пропустить");
        allRow.add(firstRow);
        keyboard.setKeyboard(allRow);

   }
}
