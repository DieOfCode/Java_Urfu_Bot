package com.company;

public class Main {

    public static void main(String[] args) {
        Bot bot = new Bot();
        SysIO inputOutput = new SysIO();

        while (true){
            String message = inputOutput.Input();
            var response = bot.reply(message);
            if (message.equals("Конец"))break;
            else inputOutput.Output(response);
        }
    }
}