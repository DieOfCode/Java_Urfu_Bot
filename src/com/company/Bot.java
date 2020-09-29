package com.company;

public class Bot {
    private User user;

    public Bot(Integer id){
        user = new User(id);
    }

    public String replay(String command){
        var botAnswer = "";
        switch (command.toLowerCase()){
            case "привет", "/help"-> botAnswer = "Привет! Я бот, который сохранит информацию по вашим координатам. Мои команды: запиши, покажи";
            case "запиши" -> {
                var io = new SysIO();
                io.Output("Введите сообщение");
                var message = io.Input();
                io.Output("Введтите координаты");
                var coors = io.Input().split(" ");
                var coordinates = new Coordinates(Double.parseDouble(coors[0]), Double.parseDouble(coors[1]));
                user.setInfoByCoordinates(coordinates, message);
                botAnswer = "Сообщение записано";
            }
            case "покажи"-> botAnswer =  user.getInfo();
            default-> botAnswer = "Напиши /help";
        }
        return botAnswer;

    }
}