package com.company;

import java.util.List;

public class BotAnswer {
    public List<String> messages = null;
    public boolean needLocation = false;

    public BotAnswer(List<String> messages, boolean needLocation){
        this.messages = messages;
        this.needLocation = needLocation;
    }
}
