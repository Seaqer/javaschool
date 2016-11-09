package lesson15.Client;

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

    public ClientChat(String ADDR_IP, int PORT) {
        this.ADDR_IP = ADDR_IP;
        this.PORT = PORT;
    }

    /**
     * Запуск клиента
     */
    public void startClient() {
        String nickName;
        final Scanner sc = new Scanner(System.in);

        try (Socket socket = new Socket(ADDR_IP, PORT);
             ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream())) {

            Reciver reciver = new Reciver(outputStream, inputStream);

            System.out.println("Введите nickname:");
            nickName = sc.nextLine();
            Response response = reciver.sendMessage(new RequestImpl(AUTH, new Message(nickName, null, null)));

            if (response.getCode() == CodeMessage.ANSWER_OK) {
                Thread reader = new Thread(new Reader(reciver));
                Sender writer = new Sender(reciver, nickName);
                reader.setDaemon(true);
                reader.start();
                writer.SendMessage();
            } else {
                System.out.println(response.getContext());
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Сервер прервал соединение");
        }
    }
}

