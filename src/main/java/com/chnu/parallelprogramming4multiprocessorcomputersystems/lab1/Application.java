package com.chnu.parallelprogramming4multiprocessorcomputersystems.lab1;

import java.util.Arrays;

public class Application {

    public static void main(String[] args) {
        ArrayManager manager = new ArrayManager();

        int[] array = manager.getArray(2);
        int[][] arrayOfArrays = manager.getArrayOfArrays(array, 2);

        if (arrayOfArrays == null) {
            return;
        }
        System.out.println(Arrays.deepToString(arrayOfArrays));

        int availableCPU = Runtime.getRuntime().availableProcessors();

        ThreadForArray threadForArray = new ThreadForArray(arrayOfArrays, availableCPU);

        for (int i = 0; i < threadForArray.getCountOfThreads(); i++) {
            if (i < arrayOfArrays.length) {
                new Thread(threadForArray).start();
            }
            if (i == arrayOfArrays.length - 1) {
                break;
            }
        }

        System.out.println("Sum of " + array.length + " = " + summationByNumber(array.length));
    }

    private static int summationByNumber(int number) {
        int sum = 0;
        for (int i = 0; i < number; i++) {
            sum += i;
        }
        return sum;
    }
}