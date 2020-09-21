package com.company;

public class Main {

    public static void main(String[] args) {
        Bot bot = new Bot();
        SysIO inputOutput = new SysIO();

        while (true){
            var response = bot.messageHandler(inputOutput.Input());
            if (Boolean.parseBoolean(response))break;
            else inputOutput.Output(response);
        }
    }
}