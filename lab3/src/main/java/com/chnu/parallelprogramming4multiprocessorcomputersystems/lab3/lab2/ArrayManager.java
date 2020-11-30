package com.chnu.parallelprogramming4multiprocessorcomputersystems.lab3.lab2;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

class ArrayManager {
    private int[] array;

    int[] getArray() {
        return array;
    }

    ArrayManager(int length) {
        this.array = initArray(length);
    }

    private int[] initArray(int length) {
        int[] array = new int[length];
        for (int i = 0; i < length; i++) {
            array[i] = i + 1;
        }
        return array;
    }

    int sum(int[] array) {
        int lengthOfArray = array.length;
        int currentElement = lengthOfArray - 1;

        while (lengthOfArray > 1) {
            int partOfArray = lengthOfArray / 2;
            CyclicBarrier barrier = new CyclicBarrier(partOfArray + 1);

            for (int i = 0; i < lengthOfArray / 2; i++) {
                new ThreadForArray(this, i, currentElement, barrier).start();
                currentElement--;
            }
            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
            lengthOfArray = lengthOfArray / 2 + lengthOfArray % 2;
        }
        return array[0];
    }
}