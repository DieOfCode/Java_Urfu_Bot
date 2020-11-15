package com.company;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendLocation;
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
    public Quest quest = Quest.questDeserializer("ProEkaterinburg.json");
    public BotsHandler botsHandler = new BotsHandler(quest);
    public static void main(String[] args) throws TelegramApiRequestException, IOException {
        LogManager.getLogManager().readConfiguration();
        ApiContextInitializer.init();
        var botApi = new TelegramBotsApi();
        botApi.registerBot(new TeleBot());

   }


    public void onUpdateReceived(Update update) {
        try {
            var sendMessage = new SendMessage();
            var sendLocation = new SendLocation();
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
            sendMessage.setChatId(currentMessage.getChatId());
            sendLocation.setChatId(currentMessage.getChatId());
            var message = bot.replay(inputMessage);

            for (String msg:message.messages) {
                sendMessage.setText(msg);
                execute(sendMessage);
            }
            if (message.needLocation) {
                sendLocation.setLongitude(bot.user.currentTask.taskLocation.y.floatValue());
                sendLocation.setLatitude(bot.user.currentTask.taskLocation.x.floatValue());
                execute(sendLocation);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Exception", e);
        }
    }

    public String getBotUsername() {
        return "geo";
    }

    public String getBotToken() {
        var env = System.getenv();
        return "1371151448:AAEdOM0b9JSmte-Q-FA9ZUib2UlBb7OE-t8";
    }
}