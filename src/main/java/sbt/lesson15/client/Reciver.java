package sbt.lesson15.client;

import sbt.lesson15.common.interfaces.Request;
import sbt.lesson15.common.interfaces.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Reciver {
    private final ObjectOutputStream outputStream;
    private final ObjectInputStream inputStream;
    private volatile boolean stateReciver;

    public Reciver(ObjectOutputStream outputStream, ObjectInputStream inputStream) {
        this.outputStream = outputStream;
        this.inputStream = inputStream;
        stateReciver = true;
    }

    /**
     * Получить состояние ресивера
     * @return состояние
     */
    public boolean getState(){
        return stateReciver;
    }

    /**
     * Направить запрос серверу
     *
     * @param request Запрос
     * @return Ответ
     */
    public synchronized Response sendMessage(Request request) throws IOException, ClassNotFoundException {
        try {
            outputStream.writeObject(request);
            return (Response) inputStream.readObject();
        } catch (IOException ex) {
            stateReciver = false;
            throw ex;
        }
    }
}

