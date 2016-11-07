package lesson15.Client;

import lesson15.Common.Interface.Request;
import lesson15.Common.Interface.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Reciver {
    private final ObjectOutputStream outputStream;
    private final ObjectInputStream inputStream;

    public Reciver(ObjectOutputStream outputStream, ObjectInputStream inputStream) {
        this.outputStream = outputStream;
        this.inputStream = inputStream;
    }

    /**
     * Направить запрос серверу
     * @param request Запрос
     * @return Ответ
     */
    public synchronized Response sendMessage(Request request) throws IOException, ClassNotFoundException {
        outputStream.writeObject(request);
        return (Response) inputStream.readObject();
    }
}
