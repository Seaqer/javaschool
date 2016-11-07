package lesson15.Client;

import lesson15.Common.Interface.Response;
import lesson15.Common.Networking.CodeMessage;
import lesson15.Common.Networking.RequestImpl;

import java.io.IOException;

/**
 * Created by Артём on 06.11.2016.
 */
public class Reader implements Runnable {
    private final Reciver reciver;

    public Reader(Reciver reciver) {
        this.reciver = reciver;
    }

    public void run() {
        Response response;

        while (true) {
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
                e.printStackTrace();
            }
        }
    }

}
