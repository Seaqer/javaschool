package sbt.lesson15.client;

/**
 * Created by Артём on 09.11.2016.
 */

import sbt.lesson15.client.exception.InvalidFormatMessage;
import sbt.lesson15.common.interfaces.Response;
import sbt.lesson15.common.model.Message;
import sbt.lesson15.common.networking.CodeMessage;
import sbt.lesson15.common.networking.RequestImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Артём on 09.11.2016.
 */
public class Sender {
    final static Log LOGGER = LogFactory.getLog(Sender.class);
    private final Reciver reciver;
    private final String nickName;
    private final Scanner sc = new Scanner(System.in);

    public Sender(Reciver reciver, String nickName) {
        this.reciver = reciver;
        this.nickName = nickName;
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
                if (reciver.getState() ) {
                    if (message.equals("exit")) {
                        reciver.sendMessage(new RequestImpl(CodeMessage.EXIT, new Message(nickName, "system", "")));
                        break;
                    }
                    args = validateMessage(message);
                    response = reciver.sendMessage(new RequestImpl(CodeMessage.SEND, new Message(nickName, args[1], args[0])));

                    if (!response.getCode().equals(CodeMessage.ANSWER_OK)) {
                        System.out.println(response.getContext());
                    }
                } else {
                    return;
                }
            } catch (InvalidFormatMessage e) {
                LOGGER.info(e);
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
