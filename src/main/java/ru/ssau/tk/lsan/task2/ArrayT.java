package ru.ssau.tk.lsan.task2;

import java.util.Arrays;

public class ArrayT {
    public static <T extends Number> T occursMostOften(T[] arr) {
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

    public static <T extends Number> int getFirstIndexOf(T[] arr, T number) {
        for(int i = 0; i<arr.length;i++){
            if(arr[i].equals(number)){
                return i;
            }
        }
        return -1;
    }

    public static <T extends Number> void swapFirstAndLastElement(T[] arr) {
        T auxiliary;
        auxiliary = arr[0];
        arr[0]=arr[arr.length-1];
        arr[arr.length-1] = auxiliary;
    }

    public static <T extends Number> void isNaN(T[] arr){
        for(T t:arr){
            if(!Double.isNaN((double)t)){
                Arrays.sort(arr);
            }
        }
    }

}
