package ru.bvn13.examples.bot;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ru.bvn13.examples.bot.accessories.InlineKeyboardBuilder;
import ru.bvn13.examples.bot.menu.MenuManager;

/**
 * Created by bvn13 on 21.02.2018.
 */
public class MenuBot extends TelegramLongPollingBot {

    private MenuManager menuManager = new MenuManager();

    public void init() {
        menuManager.setColumnsCount(2);

        menuManager.addMenuItem("Action 1", "action 1");
        menuManager.addMenuItem("Action 2", "action 2");
        menuManager.addMenuItem("Action 3", "action 3");
        menuManager.addMenuItem("Action 4", "action 4");
        menuManager.addMenuItem("Action 5", "action 5");
        menuManager.addMenuItem("Action 6", "action 6");
        menuManager.addMenuItem("Action 7", "action 7");
        menuManager.addMenuItem("Action 8", "action 8");
        menuManager.addMenuItem("Action 9", "action 9");
        menuManager.addMenuItem("Action 10", "action 10");
        menuManager.addMenuItem("Action 11", "action 11");
        menuManager.addMenuItem("Action 12", "action 12");
        menuManager.addMenuItem("Action 13", "action 13");
        menuManager.addMenuItem("Action 14", "action 14");
        menuManager.addMenuItem("Action 15", "action 15");
        menuManager.addMenuItem("Action 16", "action 16");
        menuManager.addMenuItem("Action 17", "action 17");
        menuManager.addMenuItem("Action 18", "action 18");
        menuManager.addMenuItem("Action 19", "action 19");
        menuManager.addMenuItem("Action 20", "action 20");

        menuManager.init();
    }


    private void replaceMessageWithText(long chatId, long messageId, String text) {
        EditMessageText newMessage = new EditMessageText()
                .setChatId(chatId)
                .setMessageId(Math.toIntExact(messageId))
                .setText(text);
        try {
            execute(newMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    private void replaceMessage(long chatId, long messageId, SendMessage message) {
        EditMessageText newMessage = new EditMessageText()
                .setChatId(chatId)
                .setMessageId(Math.toIntExact(messageId))
                .setText(message.getText())
                .setReplyMarkup((InlineKeyboardMarkup) message.getReplyMarkup());
        try {
            execute(newMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onUpdateReceived(Update update) {


        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {

            if (update.getMessage().getText().equals("/menu")) {
                long chatId = update.getMessage().getChatId();

                // lets render the menu
                InlineKeyboardBuilder builder = menuManager.createMenuForPage(0, true);

                builder.setChatId(chatId).setText("Choose action:");
                SendMessage message = builder.build();

                try {
                    // Send the message
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

            } else {

            }

        } else if (update.hasCallbackQuery()) {

            // Set variables
            long chatId = update.getCallbackQuery().getMessage().getChatId();
            String callData = update.getCallbackQuery().getData();
            long messageId = update.getCallbackQuery().getMessage().getMessageId();

            // here will be menu buttons callbacks

            if (callData.equals(MenuManager.CANCEL_ACTION)) {
                replaceMessageWithText(chatId, messageId, "Cancelled.");


            } else if (callData.startsWith(MenuManager.PREV_ACTION) || callData.startsWith(MenuManager.NEXT_ACTION)) {

                String pageNum = "0";
                if (callData.startsWith(MenuManager.PREV_ACTION)) {
                    pageNum = callData.replace(MenuManager.PREV_ACTION+":", "");
                } else {
                    pageNum = callData.replace(MenuManager.NEXT_ACTION+":", "");
                }

                InlineKeyboardBuilder builder = menuManager.createMenuForPage(Integer.parseInt(pageNum), true);

                builder.setChatId(chatId).setText("Choose action:");
                SendMessage message = builder.build();

                replaceMessage(chatId, messageId, message);

            }

        }
    }

    @Override
    public String getBotUsername() {
        // Return bot username
        // If bot username is @MyAmazingBot, it must return 'MyAmazingBot'
        return "MenuBot";
    }

    @Override
    public String getBotToken() {
        // Return bot token from BotFather
        return "12345:qwertyuiopASDGFHKMK";
    }

}

