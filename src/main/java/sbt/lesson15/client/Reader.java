package sbt.lesson15.client;

import sbt.lesson15.common.interfaces.Response;
import sbt.lesson15.common.networking.CodeMessage;
import sbt.lesson15.common.networking.RequestImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;

/**
 * Created by Артём on 06.11.2016.
 */
public class Reader implements Runnable {
    private final Reciver reciver;
    final static Log LOGGER = LogFactory.getLog(Reader.class);

    public Reader(Reciver reciver) {
        this.reciver = reciver;
    }

    public void run() {
        Response response;

        while (reciver.getState()) {
            try {
                response = reciver.sendMessage(new RequestImpl(CodeMessage.ACCEPT, null));

                if (response.getCode() == CodeMessage.ANSWER_MESSAGE) {
                    response.getMessages().forEach(System.out::println);
                } else {
                    System.out.println("Ошибка " + response.getContext());
                    return;
                }
                Thread.sleep(1000);
            } catch (ClassNotFoundException | IOException | InterruptedException e) {
                LOGGER.info(e);
                return;
            }
        }
    }

}
