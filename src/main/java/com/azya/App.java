package com.azya;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new Bot());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
            System.out.println("123");
            System.out.println("123");
            System.out.println("Wrong");
            System.out.println("Wrong");
            System.out.println("Wrong");
            System.out.println("Wrong");
            System.out.println("Master feature");
            System.out.println("NewBranch");
            System.out.println("AnotherNewBranch");
        }

    }
}
