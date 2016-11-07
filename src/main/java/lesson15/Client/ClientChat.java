package lesson15.Client;

import lesson15.Client.Exception.InvalidFormatMessage;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

import lesson15.Common.Interface.Response;
import lesson15.Common.Model.Message;
import lesson15.Common.Networking.CodeMessage;
import lesson15.Common.Networking.RequestImpl;

import static lesson15.Common.Networking.CodeMessage.*;

/**
 * Created by Артём on 06.11.2016.
 */
public class ClientChat {
    private final String ADDR_IP;
    private final int PORT;
    private Reciver reciver;
    private String nickName;
    private final Scanner sc = new Scanner(System.in);

    public ClientChat(String ADDR_IP, int PORT) {
        this.ADDR_IP = ADDR_IP;
        this.PORT = PORT;
    }


    public void startClient() throws IOException, ClassNotFoundException {
        Response response;

        try (Socket socket = new Socket(ADDR_IP, PORT);
             ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream())) {
            reciver = new Reciver(outputStream, inputStream);

            System.out.println("Введите nickname:");
            nickName = sc.nextLine();
            response = reciver.sendMessage(new RequestImpl(AUTH, new Message(nickName, null, null)));

            if (response.getCode() == CodeMessage.ANSWER_OK) {
                Thread reader = new Thread(new Reader(reciver));
                reader.setDaemon(true);
                reader.start();
                SendMessage();
            } else {
                System.out.println(response.getContext());
            }
        } catch (EOFException e) {
            System.out.println("Сервер прервал соединение");
        }
    }


    /**
     * Отправка сообщений серверу
     */
    public void SendMessage() throws IOException, ClassNotFoundException {
        String message;
        Response response;
        String[] args;

        while (true) {
            try {
                message = sc.nextLine();
                if (message.equals("exit")) {
                    break;
                }
                args = validateMessage(message);
                response = reciver.sendMessage(new RequestImpl(CodeMessage.SEND, new Message(nickName, args[1], args[0])));

                if (!response.getCode().equals(CodeMessage.ANSWER_OK)) {
                    System.out.println(response.getContext());
                }
            } catch (InvalidFormatMessage e) {
                System.out.println(e.getMessage());
            }
        }

    }

    /**
     * Разбить сообщение
     *
     * @param message Проверяемое сообщение
     */
    private String[] validateMessage(String message) throws InvalidFormatMessage {
        String[] args = message.split(">");
        if (args.length != 2) {
            throw new InvalidFormatMessage("format: toUser>message");
        }
        return args;

    }
}

