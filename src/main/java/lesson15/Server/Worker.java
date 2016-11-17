package lesson15.Server;

import lesson15.Common.Model.Message;
import lesson15.Common.Interface.Request;
import lesson15.Common.Networking.ResponseAnswer;
import lesson15.Common.Networking.ResponseMessage;


import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import static lesson15.Common.Networking.CodeMessage.*;

public class Worker implements Runnable {

    private final Socket socket;
    private final List<Worker> clients;
    private final List<Message> historyClient = new ArrayList<>();
    private Sender senderMessage;
    private String nickName;

    public Worker(Socket socket, List<Worker> clients) {
        nickName = "";
        this.socket = socket;
        this.clients = clients;
    }

    public void run() {
        System.out.println("Соединение установленно " + socket.getInetAddress());
        try (ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())) {
            senderMessage = new Sender(outputStream, inputStream);
            socket.setSoTimeout(900000);

            if (authenticationClient()) {
                messageHandler();
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Неизвестный формат сообщения");
        } catch (SocketTimeoutException e) {
            System.out.println("Превышен таймут в 15 минут " + socket.getInetAddress());
        } catch (IOException e) {
            System.out.println("Ошибка соединения " + socket.getInetAddress());
        } finally {
            System.out.println("Соединение разорвано " + socket.getInetAddress());
            removeSocket();
        }

    }

    /**
     * Проверка пользователя
     */
    private boolean authenticationClient() throws IOException, ClassNotFoundException {
        String nickName;
        boolean result;
        Request request = senderMessage.getRequest();
        nickName = request.getMessage().getSender();

        if (validate(nickName)) {
            this.nickName = nickName;
            sendMessage(new Message("system", nickName + " вошел в чат", "system"));
            result = true;
        } else {
            senderMessage.sendResponse(new ResponseAnswer(ANSWER_ERR, "Логин уже занят"));
            result = false;
        }

        return result;
    }

    /**
     * Обработка команд пользователя
     *
     * @throws IOException            Ошибка передачи данных
     * @throws ClassNotFoundException Неизвестный формат сообщения
     */
    private void messageHandler() throws IOException, ClassNotFoundException {
        Request request;

        while (socket.isConnected()) {
            request = senderMessage.getRequest();

            switch (request.getCODE()) {
                case SEND:
                    sendMessage(request.getMessage());
                    break;
                case ACCEPT:
                    getMessages();
                    break;
                case EXIT:
                    senderMessage.sendResponse(new ResponseAnswer(EXIT, "выход"));
                    return;
                default:
                    throw new RuntimeException("invalid format message");
            }
        }
    }

    /**
     * Отправить сообщение пользователю
     *
     * @param message Сообщение
     */
    private void sendMessage(Message message) throws IOException {
        String sender = message.getSender();

        if (validate(message.getNickName()) && !sender.equals("system")) {
            senderMessage.sendResponse(new ResponseAnswer(ANSWER_ERR, "Нет пользователя с логином " + message.getNickName()));
        } else {
            synchronized (historyClient) {
                clients.stream()
                        .filter(x -> x.nickName.equals(message.getNickName()) || sender.equals("system"))
                        .forEach(x -> x.historyClient.add(message));
            }
            senderMessage.sendResponse(new ResponseAnswer(ANSWER_OK, "Отправлено"));
        }
    }

    /**
     * Получить все сообщения адресованные текущему пользователю
     */
    private void getMessages() throws IOException {
        synchronized (historyClient) {
            senderMessage.sendResponse(new ResponseMessage(ANSWER_MESSAGE, new ArrayList<>(historyClient)));
            historyClient.clear();
        }
    }

    /**
     * Проверить не занято имя пользователя
     *
     * @param name имя
     * @return реузльтат проверки
     */
    private boolean validate(String name) {
        return !name.isEmpty() && clients.stream().noneMatch(x -> x.nickName.equals(name)) && !name.equals("system");
    }

    /**
     * Удалить соединение из списка соеденинений
     */
    private void removeSocket() {
        synchronized (historyClient) {
            clients.remove(this);
            clients.stream()
                    .filter(x -> !x.nickName.equals(nickName))
                    .forEach(x -> x.historyClient.add(new Message("system", nickName + " вышел из чата", "system")));
        }
    }

    /**
     * Завершить соединение
     */
    public void close() {
        if (socket.isConnected()) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Worker worker = (Worker) o;

        return socket != null ? socket.equals(worker.socket) : worker.socket == null && (nickName != null ? nickName.equals(worker.nickName) : worker.nickName == null);

    }

    @Override
    public int hashCode() {
        int result = socket != null ? socket.hashCode() : 0;
        result = 31 * result + (nickName != null ? nickName.hashCode() : 0);
        return result;
    }
}

