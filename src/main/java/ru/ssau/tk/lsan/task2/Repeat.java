package ru.ssau.tk.lsan.task2;

public class Repeat {
    public static void repeat(int times, Runnable r){
        for(int i = 0; i<times; i++){
            r.run();
        }
    }
}
