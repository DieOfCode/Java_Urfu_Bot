package com.company;

import java.util.HashMap;

public class BotsHandler {
    public HashMap<Long, Bot> bots = new HashMap<>();
    public  Quest quest;
    public BotsHandler(Quest quest){
        this.quest = quest;
    }
    public Bot getBot(long id){
        if (bots.containsKey(id))
            return bots.get(id);
        bots.put(id, new Bot(quest));
        return bots.get(id);
    }
}
