package sbt.lesson15.server;

import sbt.lesson15.common.interfaces.Request;
import sbt.lesson15.common.interfaces.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class Sender {
    private final ObjectOutputStream outputStream;
    private final ObjectInputStream inputStream;

    public Sender(ObjectOutputStream outputStream, ObjectInputStream inputStream) {
        this.outputStream = outputStream;
        this.inputStream = inputStream;
    }

    /**
     * Отправить ответ
     *
     * @param response ответ
     */
    public  void sendResponse(Response response) throws IOException {
            if (response == null) {
                throw new NullPointerException("response is null");
            }

            outputStream.writeObject(response);
            outputStream.flush();
    }

    /**
     * Получить запрос
     * @return запрос
     */
    public  Request getRequest() throws IOException, ClassNotFoundException {
        return (Request) inputStream.readObject();
    }
}
