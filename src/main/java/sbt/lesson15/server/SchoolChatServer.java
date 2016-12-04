package sbt.lesson15.server;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class SchoolChatServer {
    final static Log LOGGER = LogFactory.getLog(SchoolChatServer.class);
    private final int port;
    private final List<Worker> clients = new CopyOnWriteArrayList<>();
    private final ExecutorService executorService;
    private volatile boolean isDown = true;
    private ServerSocket serverSocket;
    private final int countMaxUser;
    private int countUser = 0;

    public SchoolChatServer(int port, int countMaxUser) {
        this.port = port;
        executorService = Executors.newFixedThreadPool(countMaxUser + 1);
        this.countMaxUser = countMaxUser;
    }

    /**
     * Запустить сервер
     */
    public void startServer() {
        if (!isDown) {
            throw new IllegalStateException("Сервер уже работает");
        }

        try {
            isDown = false;
            String command;
            serverSocket = new ServerSocket(port);
            Scanner console = new Scanner(System.in);

            System.out.println("Сервер запущен");
            listenConnections();
            while (true) {
                command = console.nextLine();
                switch (command) {
                    case "shutdown":
                        shutdownServer();
                        executorService.shutdown();
                        return;
                }

            }
        } catch (IOException e) {
            LOGGER.info(e);
            throw new IllegalAccessError("невозможно прослушивать порт " + port);
        }
    }

    /**
     * Остановить сервер
     */
    public void shutdownServer() throws IOException {
        if (isDown) {
            throw new IllegalStateException("Сервер уже остановлен");
        }
        isDown = true;
        clients.forEach(Worker::close);
        if (!serverSocket.isClosed()) {
            serverSocket.close();
        }
    }

    /**
     * Прием входящих подключений
     */
    private void listenConnections() {
        executorService.execute(
                () -> {
                    while (!serverSocket.isClosed()) {
                        try {
                            Socket socket = serverSocket.accept();
                            if (countUser == countMaxUser) {
                                socket.close();
                            } else {
                                Worker worker = new Worker(socket, clients);
                                clients.add(worker);
                                executorService.execute(worker);
                                ++countUser;
                            }
                        } catch (IOException e) {
                            LOGGER.info(e);
                            System.out.println("Сервер остановлен");
                        }
                    }
                });
    }
}
