
package com.company;

import java.util.Date;

public class Bot {

    public String  messageHandler(String command){
        return switch (command){
            case "Привет" -> "Привет! Я бот, который подскажет сколько сейчас время";
            case "Какой день?" -> new Date().toString();
            case "Конец"-> "true";
            default -> "Спроси: 'Какой день?' ";
        };

    }
}