package com.chnu.parallelprogramming4multiprocessorcomputersystems.lab3.lab1;

import java.util.Arrays;
import java.util.concurrent.CyclicBarrier;

public class Application {

    public static void main(String[] args) {
        ArrayManager manager = new ArrayManager();

        int[] array = manager.getArray(20);
        int[][] arrayOfArrays = manager.getArrayOfArrays(array, 4);

        if (arrayOfArrays == null) {
            return;
        }
        System.out.println(Arrays.deepToString(arrayOfArrays));

        int availableCPU = 2;

        CyclicBarrier cyclicBarrier = new CyclicBarrier(availableCPU, new Runnable() {
            @Override
            public void run() {
                System.out.println("Total sum of elements: " + ThreadForArray.getSum());
            }
        });

        ThreadForArray threadForArray = new ThreadForArray(arrayOfArrays, availableCPU, cyclicBarrier);

        for (int i = 0; i < threadForArray.getCountOfThreads(); i++) {
            if (i < arrayOfArrays.length) {
                new Thread(threadForArray).start();
            }
            if (i == arrayOfArrays.length - 1) {
                break;
            }
        }
    }
}