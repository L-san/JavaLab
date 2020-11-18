package ru.ssau.tk.lsan.task2;

import java.util.Iterator;

public class ArrayT {
    <T extends Number> T occursMostOften(T[] arr) {
        int count, maxcount, num;
         maxcount=1; num=0;
        for (int i = 0; i < arr.length; i++) {
            count = 0;
            for (int j = i; j < arr.length; j++) {
                if (arr[i].equals(arr[j])) {
                    count++;
                }
            }
                if (count > maxcount) {
                    maxcount = count;
                    num = i;
                }

        }
        return arr[num];

    }

    <T extends Number> int getFirstIndexOf(T[] arr, T number) {
        for(int i = 0; i<arr.length;i++){
            if(arr[i]==number){
                return i;
            }
        }
        return -1;
    }
}
