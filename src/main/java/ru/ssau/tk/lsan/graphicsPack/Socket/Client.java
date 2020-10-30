package ru.ssau.tk.lsan.graphicsPack.Socket;

import java.io.*;
import java.net.Socket;

public class Client extends Thread{
    private static Socket clientSocket;
    private static BufferedReader in;

    @Override
    public void run() {
        try {
            try {
                clientSocket = new Socket("localhost", 4012);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                int msgFlag = 1;
                while(msgFlag!=-1) {
                    //Integer serverWord = in.read();
                   // System.out.println(serverWord.toString());
                    int serverWord = in.read();
                    System.out.println(serverWord);
                    if(serverWord == -1){
                        msgFlag = -1;
                    }
                }
            } finally {
                System.out.println("Connection lost");
                clientSocket.close();
                in.close();
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
