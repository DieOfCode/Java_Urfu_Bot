package com.company;
import org.telegram.telegrambots.meta.api.objects.Message;

public class BotMessage {
    public Coordinates coordinates = null;
    public String command = null;
    public BotMessage(Message inputMessage){
        if (inputMessage.getLocation() != null){
            coordinates = new Coordinates(inputMessage.getLocation().getLatitude().doubleValue(),
                                          inputMessage.getLocation().getLongitude().doubleValue()
            );
        }
        else {
           command = inputMessage.getText();
        }
    }
}
