package ru.ssau.tk.lsan.graphicsPack.socket;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;

public class Client2Plotter extends Thread {
    private static Socket clientSocket;
    private static ArrayBlockingQueue<String> exchanger;
    private static BufferedWriter out;

    public Client2Plotter(ArrayBlockingQueue<String> ex) {
        exchanger = ex;
    }

    @Override
    public void run() {

        try {
            try {
                clientSocket = new Socket("localhost", 4004);
                while (true) {
                    out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                    String sms = exchanger.take();
                    out.write(sms+ "\n");
                    out.flush();
                    System.out.println("sms "+sms);
                }
            } finally {
                System.out.println("Клиент был закрыт...");
                clientSocket.close();
                out.close();
            }
        } catch (IOException | InterruptedException e) {
            System.err.println(e);
        }

    }

}
