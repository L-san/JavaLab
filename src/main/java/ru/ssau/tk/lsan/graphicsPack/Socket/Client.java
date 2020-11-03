package ru.ssau.tk.lsan.graphicsPack.Socket;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Exchanger;

public class Client extends Thread {
    private static Socket clientSocket;
    private static InputStream in;
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
                in = clientSocket.getInputStream();
                try {
                    byte[] line;
                    while ((line = in.readNBytes(1))[0] != (byte) '\n')
                        ; // в буффере может лежать полстроки и мусор, потому скипнем сразу до следующей
                    while (true) {
                        message = reader(4, 2);
                       // for (int i = 0; i < message.length; i++) {
                        //    System.out.println(message[i]);
                       // }
                        exchanger.exchange(message[1]);
                    }
                } catch (IOException e) {//| InterruptedException
                    e.printStackTrace();
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

    protected double[] parser(byte[] line) {
        //AccX_H, AccX_L, AccY_H, AccY_L, AccZ_H, AccZ_L,
        //GyroX_H, GyroX_L, GyroY_H, GyroY_L, GyroZ_H, GyroZ_L,
        //MagX_H, MagX_L, MagY_H, MagY_L, MagZ_H, MagZ_L
        double[] out = new double[line.length];
        for (int i = 0; i < line.length; i++) {
            out[i] = (char) line[i];
        }
        return out;
    }

    protected double[] reader(int length, int len) {
        if (len != 2) {
            throw new IllegalArgumentException("Forbidden len");
        }
        byte[] line;
        byte[] out = new byte[length];
        double[] outDouble = new double[length / 2];
        int j = 0;
        int cnt = 0;
        int k = 0;
        int flag = 0;
        try {
            while (flag != -1) {
                line = in.readNBytes(1);
                if (line[0] == (byte) '\n') {
                    flag = -1;
                }
                if (j >= length) {
                    //System.out.println("Array's length is not " + length);
                    break;
                }
                if ((line[0] == (byte) '\0')&&(flag != -1)) {
                    out[j] = 0;
                    j++;
                    ++cnt;
                } else if((line[0] != (byte) '\0')&&(flag != -1)){
                    out[j] = line[0];
                    j++;
                    ++cnt;
                }
                if (cnt == 2) {
                    outDouble[k++] = (double) toInt(out[j - 2], out[j - 1]);
                    cnt = 0;
                }

            }

        } catch (IOException | IllegalArgumentException e) {//
            e.printStackTrace();
        }
        return outDouble;
    }

    public int toInt(byte hb, byte lb) {
        ByteBuffer bb = ByteBuffer.wrap(new byte[]{hb, lb});
        return bb.getShort();
    }
}
