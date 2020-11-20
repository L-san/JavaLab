package ru.ssau.tk.lsan.task2;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;

public final class Array extends Numbers {
    public static Double[] newEmptyDoubleArray(int size) {
        return new Double[size];
    }

    public static Double[] newFilledDoubleArray(int size) {
        Double[] arr = new Double[size];
        arr[0] = 2d;
        arr[size - 1] = 2d;
        for (int i = 1; i < size - 1; i++) {
            arr[i] = 1d;
        }
        return arr;
    }

    public static Double[] newOddArray(int size) {
        Double[] arr = new Double[size];
        for (int i = 0; i < size; i++) {
            arr[i] = 2d * i + 1d;
        }
        return arr;
    }

    public static Double[] newDescendingEvenArray(int size) {
        Double[] arr = new Double[size];
        for (int i = size - 1; i > -1; i--) {
            arr[i] = 2d * i + 2;
        }
        return arr;
    }

    public static Double[] newFibonacciArray(int size) {
        Double[] arr = Array.newEmptyDoubleArray(size);
        getFibonacci(size, arr);
        return arr;
    }

    public static Double[] newSqrIndexArray(int size) {
        Double[] arr = new Array().newEmptyDoubleArray(size);
        for (int i = 0; i < size; i++) {
            arr[i] = (double) (i * i);
        }
        return arr;
    }

    public static Double[] newQuadraticEquationArray(double a, double b, double c) {
        double D = b * b - 4 * a * c;
        Double[] ans = new Double[]{};
        if (a == 0) {
            ans = new Double[]{-c / b};
        } else if (D > 0) {
            ans = new Double[]{(-b - Math.sqrt(D)) / (2 * a), (-b + Math.sqrt(D)) / (2 * a)};
        } else if (D == 0) {
            ans = new Double[]{-b / (2 * a)};
        } else if (D < 0) {
            ans = new Double[]{};
        }
        return ans;
    }

    public static Double[] newNotDivisibleBy3Array(int size) {

        Double[] arr = new Double[size];
        arr[0] = 1d;
        for (int i = 1; i < size; i++) {
            if (i % 2 == 0) {
                arr[i] = arr[i - 1] + 2d;
            } else {
                arr[i] = arr[i - 1] + 4d;
            }
        }
        return arr;
    }

    public static Double[] newArithmeticProgressionArray(int size, double firstElem, double difference) {
        return getAriphmeticProgression(size, firstElem, difference);
    }

    public static void makeOppositeSign(Double[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = arr[i] * (-1);
        }
    }

    public static Double[] newGeometricProgressionArray(int size, double firstElem, double difference) {
        return getGeometricProgression(size, firstElem, difference);
    }

    public static Double[] allSimpleNumbersBefore(int n) {
        if ((n <= 1)) {
            System.err.println("n<=1");
        }
        return new Double[0];
    }

    public static Double[] allDivisorsOfAnInteger(int n) {
        String ans = "";
        for (int i = 2; i < Math.sqrt(n); i++) {
            while (n % i == 0) {
                n /= i;
            }
            ans += i;
            ans += " ";
        }
        ans += n;
        String[] ansArr = ans.split(" ");
        Double[] ansDoubleArr = new Double[ansArr.length];
        for (
                int j = 0;
                j < ansArr.length; j++) {
            ansDoubleArr[j] = Double.parseDouble(ansArr[j]);
        }
        return ansDoubleArr;
    }

    public static boolean isInArray(Double n, Double[] arr) {
        for (Double aDouble : arr) {
            if (aDouble.equals(n)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNullInArray(Integer[] arr) {
        for (Integer integer : arr) {
            if (integer == null) {
                return true;
            }
        }
        return false;
    }

    public static int howManyEven(Integer[] arr) {
        int cnt = 0;
        for (Integer integer : arr) {
            if (integer % 2 == 0) {
                cnt++;
            }
        }
        return cnt;
    }

    public static Double maxValue(Double[] arr) {
        if (arr.length == 0) {
            return null;
        }
        Double auxiliary = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (auxiliary < arr[i]) {
                auxiliary = arr[i];
            }
        }
        return auxiliary;
    }

    public static Integer maxValue(Integer[] arr) {
        if (arr.length == 0) {
            return null;
        }
        Integer auxiliary = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (auxiliary < arr[i]) {
                auxiliary = arr[i];
            }
        }
        return auxiliary;
    }

    public static Integer sumOfEven(Integer[] arr) {
        int cnt = 0;
        for (Integer integer : arr) {
            if (integer % 2 == 0) {
                cnt += integer;
            }
        }
        return cnt;
    }

    public static Double sumByEvenIndexes(Double[] arr) {
        Double cnt = 0d;
        for (int i = 0; i < arr.length; i += 2) {
            cnt += arr[i];
        }
        return cnt;
    }

    public static int[] eachByteInversion(int[] arr) {
        int[] answer = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            answer[i] = ~arr[i];
        }
        return answer;
    }

    public static void eachByteInversionByLink(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = ~arr[i];
        }
    }

    public static int[] onePointTwentySix(int[] arr) {
        if (arr.length % 2 == 0) {
            int[] ans = new int[arr.length / 2];
            int j = 0;
            for (int i = 0; i < ans.length; i++) {
                ans[i] = arr[j++] + arr[j++];
            }
            return ans;
        }
        return arr;
    }

    public static boolean[] isEven(int[] arr) {
        boolean[] ans = new boolean[arr.length];
        for (int i = 0; i < arr.length; i++) {
            ans[i] = arr[i] % 2 == 0;
        }
        return ans;
    }

    public static int[] separate(long num) {
        return new int[]{(int) (num >> 32), (int) num};
    }

    public static long toLong(int[] arr) {
        return ((long) arr[0] << 32) | ((long) arr[1]);
    }

    public static int[] fillWithRealsFromIndex(int index, int size) {
        int[] arr = new int[size];
        for (int j = 1; j <= size; j++) {
            arr[index % size] = (index + j - (index++));
        }
        return arr;
    }

    public static int[][] eachDimensionLessByOne(int num) {
        int[][] arr = new int[num][];
        int cnt = 1;
        for (int i = 0; i < num; i++) {
            arr[i] = new int[num - i];
            for (int j = 0; j < num - i; j++) {
                arr[i][j] = cnt++;
            }
        }
        return arr;
    }

}
