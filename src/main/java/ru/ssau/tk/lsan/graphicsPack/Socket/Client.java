package ru.ssau.tk.lsan.graphicsPack.Socket;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.Exchanger;

public class Client extends Thread {
    private static Socket clientSocket;
    private static BufferedReader in;
    private static Exchanger<Double> exchanger;
    protected Double message;

    public Client(Exchanger<Double> ex) {
        exchanger = ex;
    }

    @Override
    public void run() {
        try {
            try {
                clientSocket = new Socket("localhost", 4012);
                System.out.println("Connection started");
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                int msgFlag = 1;
                while (msgFlag != -1) {
                    message = ((double) in.read());
                    if (message == null) {
                        msgFlag = -1;
                    }else{
                        exchanger.exchange(message);
                    }
                }
            } finally {
                System.out.println("Connection lost");
                clientSocket.close();
                in.close();
            }
        } catch (IOException | InterruptedException e) {
            System.err.println(e.getMessage());
        }
    }
}
