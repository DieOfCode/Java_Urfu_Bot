package com.company;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;


public class TeleBot extends TelegramLongPollingBot {
    private static Logger logger = Logger.getLogger(TeleBot.class.getName());
    public BotsHandler botsHandler = new BotsHandler();
    public static void main(String[] args) throws TelegramApiRequestException, IOException {
        LogManager.getLogManager().readConfiguration();
        ApiContextInitializer.init();
        TelegramBotsApi botapi = new TelegramBotsApi();
        botapi.registerBot(new TeleBot());
    }

    public void a(){throw new NullPointerException();}

    public void onUpdateReceived(Update update) {
        try {
            a();
            var sendMessage = new SendMessage();
            var bot = botsHandler.getBot(update.getMessage().getChatId());
            var inputMessage = new BotMessage(update.getMessage());
            sendMessage.setChatId(update.getMessage().getChatId());
            sendMessage.setText(bot.replay(inputMessage));
            execute(sendMessage);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Exception", e);
        }
    }

    public String getBotUsername() {
        return "geo";
    }

    public String getBotToken() {
        return "";
    }
}
