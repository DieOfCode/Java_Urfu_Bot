package com.company;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;

public class BotKeyboard extends ReplyKeyboardMarkup {
    ArrayList<KeyboardRow> keyboard = new ArrayList<>();
    KeyboardRow keyboardFirstRow = new KeyboardRow();

    public void getKeyboard(Message newMessage){

        this.setSelective(true);
        this.setResizeKeyboard(true);
        this.setOneTimeKeyboard(true);

        switch (newMessage.command.toLowerCase()){
            case "привет", "/help", "/start" -> {
                clearKeyboard();
                keyboardFirstRow.add("Запиши");
                keyboardFirstRow.add("Покажи");
                keyboard.add(keyboardFirstRow);
                this.setKeyboard(keyboard);

            }
        }
    }

    public void clearKeyboard(){
        keyboard.clear();
        keyboardFirstRow.clear();
    }
}

