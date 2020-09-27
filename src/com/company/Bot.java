
package com.company;

import java.util.Date;

public class Bot {
    State botState = new State();
    public String reply(String command){
        String botAnswer = switch (command) {
            case "Привет", "/help" -> "Привет! Я бот, который подскажет сколько сейчас время";
            case "Какой день?" -> new Date().toString();
            case "Конец" -> "true";
            default -> "Спроси: 'Какой день?' ";
        };
        botState.listOfCommand.push(botAnswer);

        return botAnswer;

    }
}