package com.company;

import java.util.Stack;

public class State {
    BotStack<String> listOfCommand = new BotStack<String>();

    public String getLastCommand(){
        return listOfCommand.empty() ? "Ничего не происходило" : listOfCommand.lastElement();
    }

}
