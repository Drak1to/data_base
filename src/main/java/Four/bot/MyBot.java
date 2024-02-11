package Four.bot;

import Four.Utils.DataBaseHandler;
import Four.models.Client;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.SQLException;
import java.util.Random;

public class MyBot extends TelegramLongPollingBot {
    private final String[] ADVICES = {
            "**Порада 1:** Регулярно відпочивайте та забирайте себе від роботи.",
            "**Порада 2:** Встановіть конкретні цілі та розплануйте шлях до їх досягнення.",
            "**Порада 3:** Зберігайте позитивний настрій та уникайте стресів.",
            "**Порада 4:** Спробуйте щось нове щодня, розширюйте свої горизонти.",
            "**Порада 5:** Навчайтеся вмінню слухати і розуміти інших людей.",
            "**Порада 6:** Дбайте про своє здоров'я, відводьте час для регулярних медичних перевірок.",
            "**Порада 7:** Створіть зручний розклад та дотримуйтесь його для ефективного використання часу.",
            "**Порада 8:** Вивчайте нові речі та розвивайте свої навички.",
            "**Порада 9:** Будьте вдячні за те, що у вас є, та цінуйте свої досягнення.",
            "**Порада 10:** Регулярно відпочивайте, дозволяючи собі насолоджуватися моментами."
    };
    private final String[] PROMOTION = {
            "** 1 Акція друзі - За кожного друга ви получвєте 2% знижки.",
            "** 2 Акція постоялець - Кожне 10 відвідування на 3% дешевше.",
            "** 3 Акція Передбачник - При оплаті 20 відвідувань знижка 5%.",
            "** 4 Акція Везунчик - Якщо ви побачили цю акцію ви получаєте 1 відвідування задарма.",
            "** 5 Акція Оцінка - При оставлені відгуку ви получаєте 1% знижки на 3 наступні відвідування. "

    };


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
        if (msg != null && msg.hasText()) {
            String command = msg.getText();
            if (command.startsWith("/registration")) {
                try {
                    registerClient(msg);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else if (command.startsWith("/myinfo") || command.startsWith("/myInfo")) {
                showClientInfo(msg);
            } else if (command.startsWith("/addvisit")) {
                addVisits(msg);
            } else if (command.startsWith("/statistics")) {
                clientsCount(msg);
            } else if (command.startsWith("/graphic")) {
                dayOfVisits(msg);
            } else if (command.startsWith("/advice")) {
                getAdviceToUser(msg);
            }else if (command.startsWith("/comments")){
                setComments(msg);
            }else if (command.startsWith("/promotion")){
                getPromotion(msg);
            }else if (command.startsWith("/award")){
                awardSystem(msg);
            }
        }
    }

    private void showClientInfo(Message msg) {
        String[] word = msg.getText().split(" ");

        if (word.length != 2) {
            sendMessage(msg, "Неправильний формат команди. Використовуйте /myinfo {номер_телефону}");
            return;
        }
        String phoneNumber = word[1];
        DataBaseHandler dataBaseHandler = new DataBaseHandler();
        dataBaseHandler.getConnection();
        System.out.println(phoneNumber);

        Client client = dataBaseHandler.getClientByNumber(phoneNumber);
        if (client != null) {
            sendMessage(msg, client.toString());
        } else {
            sendMessage(msg, "Client has not found");
        }
    }

    private void registerClient(Message msg) throws SQLException {
        String[] word = msg.getText().split(" ");

        if (word.length != 4) {
            sendMessage(msg, "Неправильний формат команди. Використовуйте /реєстрація {ім'я} {прізвище} {номер_телефону} {day_of_visits}");
            return;
        }
        String firstName = word[1];
        String lastName = word[2];
        String phoneNumber = word[3];
        DataBaseHandler dataBaseHandler = new DataBaseHandler();
        dataBaseHandler.getConnection();

        Client client = new Client(firstName, lastName, phoneNumber, 0, null, null, 0);
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

    private void addVisits(Message msg) {
        String[] word = msg.getText().split(" ");

        if (word.length != 2) {
            sendMessage(msg, "Неправильний формат команди. Використовуйте /addvisit {номер_телефону} ");
            return;
        }
        String phoneNumber = word[1];
        DataBaseHandler dataBaseHandler = new DataBaseHandler();
        dataBaseHandler.getConnection();

        boolean flag = dataBaseHandler.updateClientByVisits(phoneNumber);
        if (flag) {
            sendMessage(msg, "Your count is increase");
            showClientInfo(msg);

        } else {
            sendMessage(msg, "Not founded by number");
        }
    }

    private void clientsCount(Message msg) {
        DataBaseHandler dataBaseHandler = new DataBaseHandler();
        dataBaseHandler.getConnection();

        int count = dataBaseHandler.getRegisteredClientsCount();
        sendMessage(msg, "Count of visitor " + String.valueOf(count));
    }

    private void dayOfVisits(Message msg) {
        String[] word = msg.getText().split(" ");

        if (word.length != 3) {
            sendMessage(msg, "Неправильний формат команди. Використовуйте /graphic {номер_телефону} {day of visit} ");
            return;
        }
        String phoneNumber = word[1];
        String day = word[2];
        DataBaseHandler dataBaseHandler = new DataBaseHandler();
        dataBaseHandler.getConnection();

        boolean flag = dataBaseHandler.saveDayOfVisits(phoneNumber, day);

        if (flag) {
            sendMessage(msg, "Your visits day is apply");
            Client client = dataBaseHandler.getClientByNumber(phoneNumber);
            sendMessage(msg, String.valueOf(client));

        } else {
            sendMessage(msg, "Not founded by number");
        }
    }

    public String getRandomAdvice() {
        Random random = new Random();
        return ADVICES[random.nextInt(ADVICES.length)];
    }

    public void getAdviceToUser(Message msg) {
        String advice = getRandomAdvice();
        sendMessage(msg, advice);
    }

    public void setComments(Message msg) {
        String[] word = msg.getText().split(" ");

        if (word.length != 3) {
            sendMessage(msg, "Неправильний формат команди. Використовуйте /comments {номер_телефону} {your comments} ");
            return;
        }


        String phoneNumber = word[1];
        String comments = word[2];
        DataBaseHandler dataBaseHandler = new DataBaseHandler();
        dataBaseHandler.getConnection();

        boolean flag = dataBaseHandler.saveComments(phoneNumber,comments);

        if (flag) {
            sendMessage(msg, "Your comments is apply");
            Client client = dataBaseHandler.getClientByNumber(phoneNumber);
            sendMessage(msg, String.valueOf(client));

        } else {
            sendMessage(msg, "Not founded by number");
        }
    }
    public String getRandomPromotion() {
        Random random = new Random();
        return PROMOTION[random.nextInt(PROMOTION.length)];
    }

    public void getPromotion(Message msg) {
        String advice = getRandomPromotion();
        sendMessage(msg, advice);
    }

    public void awardSystem(Message msg){
            String[] word = msg.getText().split(" ");

            if (word.length != 2) {
                sendMessage(msg, "Неправильний формат команди. Використовуйте /award {номер_телефону} ");
                return;
            }
            String phoneNumber = word[1];
            DataBaseHandler dataBaseHandler = new DataBaseHandler();
            dataBaseHandler.getConnection();

            boolean flag = dataBaseHandler.updateClientByAward(phoneNumber);
           // Client client = new Client();
            if (flag   ) {
//                if(client.getVisitsCount() == 5 || client.getVisitsCount() == 10 || client.getVisitsCount() == 15 || client.getVisitsCount() == 20 ) {
//                }
                    sendMessage(msg, "Your award is increase");
                    showClientInfo(msg);

            } else {
                sendMessage(msg, "Not founded by number , or you don't got award");
            }
        }

    }






