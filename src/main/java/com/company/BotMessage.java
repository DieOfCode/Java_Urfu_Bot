package com.company;
import org.telegram.telegrambots.meta.api.objects.Message;

public class BotMessage {
    public Coordinates coordinates = null;
    public Boolean isEdited = false;
    public String command = null;
    public Long chatID;
    public Integer messageID;
    public BotMessage(Message inputMessage){
        messageID = inputMessage.getMessageId();
        chatID = inputMessage.getChatId();
        if (inputMessage.getLocation() != null){
            coordinates = new Coordinates(inputMessage.getLocation().getLatitude().doubleValue(),
                    inputMessage.getLocation().getLongitude().doubleValue()
            );
            command ="";
        }
        else {
            command = inputMessage.getText();
        }
    }
}
