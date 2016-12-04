package sbt.lesson15.client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

import sbt.lesson15.common.interfaces.Response;
import sbt.lesson15.common.model.Message;
import sbt.lesson15.common.networking.CodeMessage;
import sbt.lesson15.common.networking.RequestImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import static sbt.lesson15.common.networking.CodeMessage.*;

/**
 * Created by Артём on 06.11.2016.
 */
public class ClientChat {
    final static Log LOGGER = LogFactory.getLog(ClientChat.class);
    private final String ADDR_IP;
    private final int PORT;
    private final int TIMEOUT = 900000;

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
            socket.setSoTimeout(TIMEOUT);
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
            LOGGER.info(e);
            System.out.println("Сервер прервал соединение");
        }
    }
}

