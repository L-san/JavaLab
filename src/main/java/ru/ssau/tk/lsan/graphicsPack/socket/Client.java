package ru.ssau.tk.lsan.graphicsPack.socket;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;

public class Client extends Thread {
    private static Socket clientSocket;
    private static InputStream in;
    private static ArrayBlockingQueue<Byte> exchanger;
    protected byte[] message;

    public Client(ArrayBlockingQueue<Byte> ex) {
        exchanger = ex;
    }

    @Override
    public void run() {
        try {
            try {
                clientSocket = new Socket("0.0.0.0", 4012);
                System.out.println("Connection started");
                in = clientSocket.getInputStream();
                /*byte[] line = new byte[]{0};
                while (line[0] != (byte) '\n') {
                    line = in.readNBytes(1);
                }*/
                // в буффере может лежать полстроки и мусор, потому скипнем сразу до следующей
                while (true) {
                    message = reader(18);
                    for (int i = 0; i < message.length; i++) {
                        exchanger.put(message[i]);
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

    protected byte[] reader(int length) {
        byte[] line;
        byte[] out = new byte[length];

        int j = 0;
        try {
            while (true) {
                line = in.readNBytes(1);
                if (line[0] != (byte) '\n') {
                    out[j++] = line[0];
                } else if (j == length) {
                    return out;
                } else {
                    j = 0;
                }
            }
        } catch (IOException | IllegalArgumentException e) {//
            e.printStackTrace();
        }
        return out;
    }
}
