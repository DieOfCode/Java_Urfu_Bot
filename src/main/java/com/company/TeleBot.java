package com.company;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;


public class TeleBot extends TelegramLongPollingBot {
    private static final Logger logger = Logger.getLogger(TeleBot.class.getName());
    public BotsHandler botsHandler = new BotsHandler();
    public static void main(String[] args) throws TelegramApiRequestException, IOException {
        LogManager.getLogManager().readConfiguration();
        ApiContextInitializer.init();
        TelegramBotsApi botapi = new TelegramBotsApi();
        botapi.registerBot(new TeleBot());
    }


    public void onUpdateReceived(Update update) {
        try {
            var sendMessage = new SendMessage();
            logger.info(update.toString());
            Bot bot;
            BotMessage inputMessage;
            Message currentMessage;
            if (update.getMessage() != null){
                currentMessage = update.getMessage();
            }
            else {
                currentMessage = update.getEditedMessage();
            }
            bot = botsHandler.getBot(currentMessage.getChatId());
            inputMessage = new BotMessage(currentMessage);
            inputMessage.isEdited = update.getMessage() == null;
            sendMessage.setChatId(currentMessage.getChatId());
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
        var env = System.getenv();
        return env.get("token");
    }
}
