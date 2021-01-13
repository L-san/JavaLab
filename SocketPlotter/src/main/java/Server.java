import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;

public class Server extends Thread {
    private static Socket clientSocket;
    private static ServerSocket server;
    private static BufferedReader in;
    private static ArrayBlockingQueue<String> exchanger;

    public Server(ArrayBlockingQueue<String> ex) {
        exchanger = ex;
    }

    @Override
    public void run() {
        try {
            try {
                server = new ServerSocket(4004);
                clientSocket = server.accept();
                System.out.println("Сервер запущен!");
                try {
                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    System.out.println("Поток клиент сокета захвачен.");
                    while (true) {
                        String str = in.readLine();
                        System.out.println(str);
                        exchanger.put(str);
                    }

                } finally {
                    clientSocket.close();
                    in.close();
                }
            } finally {
                System.out.println("Сервер закрыт!");
                server.close();
            }
        } catch (IOException | InterruptedException e) {
            System.err.println(e);
        }
    }
}

