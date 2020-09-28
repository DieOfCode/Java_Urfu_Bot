package com.company;

import java.util.HashMap;
import java.util.Stack;

public class State {
    BotStack<HashMap<String,String>> listOfCommand = new BotStack<>();

    public String getLastCommand(){
        return listOfCommand.empty() ? "Ничего не происходило" : String.valueOf(listOfCommand.lastElement());
    }

}
