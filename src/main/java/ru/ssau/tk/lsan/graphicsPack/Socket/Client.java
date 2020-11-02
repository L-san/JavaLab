package ru.ssau.tk.lsan.graphicsPack.Socket;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.Exchanger;

public class Client extends Thread{
    private static Socket clientSocket;
    private static BufferedReader in;
    private static Exchanger<Double> exchanger;
    protected double[] message;
    public Client(Exchanger<Double> ex) {
        exchanger = ex;
    }

    @Override
    public void run() {
        try {
            try {
                clientSocket = new Socket("0.0.0.0", 4012);
                System.out.println("Connection started");
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                int msgFlag = 1;
                String str = "";
                while (msgFlag != -1) {
                    StringBuffer sBuff = new StringBuffer(in.readLine());
                    if (str.isEmpty()) {
                        msgFlag = -1;
                    } else {
                        message = parser(str);
                        exchanger.exchange(10d);
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

    protected double[] parser(String str) {
        //AccX_H, AccX_L, AccY_H, AccY_L, AccZ_H, AccZ_L,
        //GyroX_H, GyroX_L, GyroY_H, GyroY_L, GyroZ_H, GyroZ_L,
        //MagX_H, MagX_L, MagY_H, MagY_L, MagZ_H, MagZ_L
        double[] parsedArr = new double[str.length() / 2];
        for (int i = 0; i < str.length() - 1; i+=2) {
            parsedArr[i] = Double.parseDouble(str.substring(i, i + 1));
        }
        return parsedArr;
    }
}
