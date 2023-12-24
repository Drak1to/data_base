package Three.bot;

import Three.Utils.DataBaseHandler;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.Map;

public class MyBot extends TelegramLongPollingBot {
    Map<Long, UserStatement> userStatementMap = new HashMap<>();
    Map<Long, String> userFilmTitle = new HashMap<>();

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();
            String msgText = update.getMessage().getText();
            String email = update.getMessage().getText();

//            SendMessage sendMessage = new SendMessage();
//            sendMessage.setChatId(chatId);
//            sendMessage.setText(msgText);
//            try {
//                execute(sendMessage);
//            } catch (TelegramApiException e) {
//                throw new RuntimeException(e);
//            }

            if (msgText.startsWith("/start")) {
                handleStartCommand(chatId);
            } else if (msgText.startsWith("/register")) {
                handleRegisterCommand(chatId);
            } else if (userStatementMap.get(chatId) == UserStatement.AWAITING_REGISTRATION) {
                completeRegister(chatId, msgText, email);
            } else if (msgText.startsWith("/create")){
                handleCreateNameFilmCommand(chatId);
            }else if (userStatementMap.get(chatId) == UserStatement.AWAITING_FILM_NAME){
                userStatementMap.put(chatId, UserStatement.AWAITING_FILM_RATING);
                String filmName = msgText;
                userFilmTitle.put(chatId, filmName);
                handleCreateNameFilmCommand(chatId);
                System.out.println(userStatementMap);
                System.out.println(userFilmTitle);
            }else if (userStatementMap.get(chatId) == UserStatement.AWAITING_FILM_RATING){
                String filmName = userFilmTitle.get(chatId);
                int filmRating = Integer.parseInt(msgText);
                completeFilmCreating(filmRating, filmName, chatId );
            }
        } else if (update.hasCallbackQuery()) {
            handleCallbackQuery(update.getCallbackQuery());
        }
    }

    private void handleCreateNameFilmCommand(long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Write name for creating film");
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }

    private void completeFilmCreating(int rating, String title,  long chatId) {
        DataBaseHandler dataBaseHandler = new DataBaseHandler();
        dataBaseHandler.createFilm(rating, title, chatId);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Film is crating");
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
        userStatementMap.remove(chatId);
        userFilmTitle.remove(chatId);
    }

    private void handleCallbackQuery(CallbackQuery callbackQuery) {
        String callbackData = callbackQuery.getData();
        long chatId = callbackQuery.getMessage().getChatId();
        String callbackId = callbackQuery.getId();

        if(callbackData.equalsIgnoreCase("get_id")){
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText("Ваш id: " + chatId);
            try {
                execute(sendMessage);

                AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
                answerCallbackQuery.setCallbackQueryId(callbackId);
                execute(answerCallbackQuery);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void handleStartCommand(long chatId) {
        Map<String, String> buttons = new HashMap<>();
        buttons.put("Get your id","get_id");

        SendMessage sendMessage = TelegramBotUtils.createMessage(chatId, "Hi you can get your id!" , buttons);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleRegisterCommand(long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Write your name for registration");
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }


        userStatementMap.put(chatId, UserStatement.AWAITING_REGISTRATION);
        System.out.println(userStatementMap);
    }

    private void completeRegister(long chatId, String username,  String email) {
        DataBaseHandler dataBaseHandler = new DataBaseHandler();
        dataBaseHandler.registerUser(username, chatId,email);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Registration is finished");
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
        userStatementMap.remove(chatId);
    }


    @Override
    public String getBotUsername() {
        return "Magic4444bot";
    }

    @Override
    public String getBotToken() {
        return "6932827365:AAHLMAkjq3u2ZRScEs6KjTYoSfg1QalRstM";
    }
}
