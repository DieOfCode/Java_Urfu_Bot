package com.company;

import java.util.HashMap;

public class BotsHandler {
    public HashMap<Long, Bot> bots = new HashMap<>();
    public Bot getBot(long id){
        if (bots.containsKey(id))
            return bots.get(id);
        bots.put(id, new Bot());
        return bots.get(id);
    }
}
