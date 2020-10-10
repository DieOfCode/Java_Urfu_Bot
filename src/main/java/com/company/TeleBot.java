package com.company;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.lang.reflect.Type;

public class TeleBot extends TelegramLongPollingBot {
    public BotsHandler botsHandler = new BotsHandler();
    public static void main(String[] args) {
        {ApiContextInitializer.init();}
        TelegramBotsApi botapi = new TelegramBotsApi();
        try {
            botapi.registerBot(new TeleBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void onUpdateReceived(Update update) {
        var sendMessage = new SendMessage();
        var bot = botsHandler.getBot(update.getMessage().getChatId());
        var inputMessage = new Message(update.getMessage());
        sendMessage.setChatId(update.getMessage().getChatId());
        sendMessage.setText(bot.replay(inputMessage));
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public String getBotUsername() {
        return "geo";
    }

    public String getBotToken() {
        return "1349695017:AAHcTSpiqvHsrdMMKiicvCjjIh_zm7U-x0c";
    }
}
