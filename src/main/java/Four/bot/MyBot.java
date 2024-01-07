package Four.bot;

import Four.Utils.DataBaseHandler;
import Four.models.Client;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.SQLException;

public class MyBot extends TelegramLongPollingBot {

    @Override
    public String getBotUsername() {
        return "Club4444_bot";
    }

    @Override
    public String getBotToken() {
        return "6895480659:AAFOYd4iFxZmgSpV2CfM7VQxRHa7257UC1M";
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message msg = update.getMessage();
        if(msg != null && msg.hasText()){
            String command = msg.getText();
            if(command.startsWith("/registration")){
                try {
                    registerClient(msg);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void registerClient(Message msg) throws SQLException {
        String[] word = msg.getText().split(" ");

        if(word.length != 4){
            sendMessage(msg, "Неправильний формат команди. Використовуйте /реєстрація {ім'я} {прізвище} {номер_телефону}");
            return;
        }
        String firstName = word[1];
        String lastName = word[2];
        String phoneNumber = word[3];
        DataBaseHandler dataBaseHandler = new DataBaseHandler();
        dataBaseHandler.getConnection();

        Client client = new Client(firstName, lastName, phoneNumber, 0);
        dataBaseHandler.saveClient(client);

        sendMessage(msg, "Client is registration");
    }

    private void sendMessage(Message msg, String s) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(msg.getChatId());
        sendMessage.setText(s);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
