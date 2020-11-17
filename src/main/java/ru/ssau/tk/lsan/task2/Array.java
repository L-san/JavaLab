package ru.ssau.tk.lsan.task2;

public class Array extends Numbers implements ArrayMethods {
    @Override
    public Double[] newEmptyDoubleArray(int size) {
        return new Double[size];
    }

    @Override
    public Double[] newFilledDoubleArray(int size) {
        Double[] arr = new Double[size];
        arr[0] = 2d;
        arr[size - 1] = 2d;
        for (int i = 1; i < size - 1; i++) {
            arr[i] = 1d;
        }
        return arr;
    }

    @Override
    public Double[] newOddArray(int size) {
        Double[] arr = new Double[size];
        for (int i = 0; i < size; i++) {
            arr[i] = 2d * i + 1d;
        }
        return arr;
    }

    @Override
    public Double[] newDescendingEvenArray(int size) {
        Double[] arr = new Double[size];
        for (int i = size - 1; i > -1; i--) {
            arr[i] = 2d * i + 2;
        }
        return arr;
    }

    @Override
    public Double[] newFibonacciArray(int size) {
        Double[] arr = new Array().newEmptyDoubleArray(size);
        getFibonacci(size, arr);
        return arr;
    }

    @Override
    public Double[] newSqrIndexArray(int size) {
        Double[] arr = new Array().newEmptyDoubleArray(size);
        for (int i = 0; i < size; i++) {
            arr[i] = (double) (i * i);
        }
        return arr;
    }

    @Override
    public Double[] newQuadraticEquationArray(double a, double b, double c) {
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

    @Override
    public Double[] newNotDivisibleBy3Array(int size) {

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

    @Override
    public Double[] newArithmeticProgressionArray(int size, double firstElem, double difference) {
        return getAriphmeticProgression(size, firstElem, difference);
    }

    @Override
    public void makeOppositeSign(Double[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = arr[i] * (-1);
        }
    }

    @Override
    public Double[] newGeometricProgressionArray(int size, double firstElem, double difference) {
        return getGeometricProgression(size, firstElem, difference);
    }

    @Override
    public Double[] allSimpleNumbersBefore(int n) {
        if ((n <= 1)) {
            System.err.println("n<=1");
        }
        return new Double[0];
    }

    @Override
    public Double[] allDivisorsOfAnInteger(int n) {
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

    @Override
    public boolean isInArray(Double n, Double[] arr) {
        for(int i = 0; i<arr.length; i++){
            if(arr[i]==n){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isNullInArray(Integer[] arr) {
        for(int i = 0; i<arr.length; i++){
            if(arr[i]==null){
                return true;
            }
        }
        return false;
    }

    @Override
    public int howManyEven(Integer[] arr) {
        int cnt = 0;
        for(int i = 0; i<arr.length; i++){
            if(arr[i]%2==0){
                cnt++;
            }
        }
        return cnt;
    }

    public Double maxValue(Double[] arr) {
        if(arr.length==0){return null;}
        Double auxiliary = arr[0];
        for(int i = 1;i<arr.length;i++){
            if(auxiliary<arr[i]){
                auxiliary = arr[i];
            }
        }
        return auxiliary;
    }

    public Integer maxValue(Integer[] arr) {
        if(arr.length==0){return null;}
        Integer auxiliary = arr[0];
        for(int i = 1;i<arr.length;i++){
            if(auxiliary<arr[i]){
                auxiliary = arr[i];
            }
        }
        return auxiliary;
    }
}
