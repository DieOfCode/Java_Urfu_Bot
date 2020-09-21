package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Bot bot = new Bot();
        Scanner scan = new Scanner(System.in);

        while (true){
            var response = bot.messageHandler(scan.nextLine());
            if (Boolean.parseBoolean(response))break;
            else System.out.println(response);
        }
    }
}