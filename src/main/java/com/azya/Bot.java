package com.azya;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {
    private static final String NAME = "AzyaTestBot";
    private static final String TOKEN= "888862781:AAEoLdZtzOunpuFhZVHyO5G6WeaKVYBgvWs";
    private static final String HELLO_MSG= "Привет";
    private static final String HELP_MSG= "Помощь";

    @Override
    public String getBotUsername() {
        return NAME;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            handleTextMessage(update);
        } else if (update.hasCallbackQuery()) {
            handleCallBack(update);
        }
    }

    private void handleCallBack(Update update) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        String data = callbackQuery.getData();
        Long chatId = callbackQuery.getMessage().getChatId();
        String primaryText= callbackQuery.getMessage().getText();
        Integer messageId = callbackQuery.getMessage().getMessageId();
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(chatId).setMessageId(messageId)
                .setReplyMarkup(getInlineKeyBoard(data + "A", data + "B", data + "C", data + "D"))
                .setText(primaryText).enableMarkdown(true);
        try {
            execute(editMessageText);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    private void handleTextMessage(Update update) {
        Message gotMessage = update.getMessage();
        String message = gotMessage.getText();
        String chatId = gotMessage.getChatId().toString();
        String response;
        switch (message) {
            case HELLO_MSG:
                response = String.format("Привет. Я бот. Сегодня %s", LocalDate.now().toString());
                break;
            case HELP_MSG:
                response = "Говорите со мной, и я отвечу";
                break;
            default:
                response = "Не понимаю";
                break;
        }

        sendMessage(response, chatId);
    }

    public synchronized void sendMessage(String message, String chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        setButtons(sendMessage);
        sendMessage.setText(message);
        sendMessage.setReplyMarkup(getInlineKeyBoard("A", "B", "C", "D"));
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private synchronized void setButtons(SendMessage sendedMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendedMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        List<KeyboardRow> keyBoard = new ArrayList<>();
        KeyboardRow firstRow = new KeyboardRow();
        firstRow.add(new KeyboardButton(HELLO_MSG));
        KeyboardRow secondRow = new KeyboardRow();
        secondRow.add(new KeyboardButton(HELP_MSG));
        keyBoard.add(firstRow);
        keyBoard.add(secondRow);
        replyKeyboardMarkup.setKeyboard(keyBoard);
    }



    private InlineKeyboardMarkup getInlineKeyBoard(String ...values){
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        for (String value : values) {
            List<InlineKeyboardButton> row = new ArrayList<>();
            row.add(new InlineKeyboardButton().setText(value).setCallbackData(value));
            buttons.add(row);
        }
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(buttons);
        return inlineKeyboardMarkup;
    }




    @Override
    public String getBotToken() {
        return TOKEN;
    }
}
