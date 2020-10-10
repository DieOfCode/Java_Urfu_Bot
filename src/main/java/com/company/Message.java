package com.company;

import javax.validation.constraints.Null;

public class Message {
    public Coordinates coordinates = null;
    public String command = null;
    public Message(org.telegram.telegrambots.meta.api.objects.Message inputMessage){
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
