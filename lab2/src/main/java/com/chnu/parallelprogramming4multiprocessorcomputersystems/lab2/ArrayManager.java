package com.chnu.parallelprogramming4multiprocessorcomputersystems.lab2;

import java.util.Arrays;

class ArrayManager {

    int[] getArray(int length) {
        int[] array = new int[length];

        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }

        return array;
    }

    int sum(int[] array) throws InterruptedException {
        int[] tempArray;
        int counter = 0;

        while (true) {
            Thread thread = new Thread(new ThreadForArray(array, counter));
            thread.start();
            thread.join();
            counter++;
            if (counter == array.length / 2) {
                tempArray = array;
                array = new int[(int) Math.ceil((double) array.length / 2)];
                for (int j = 0; j < array.length; j++) {
                    array[j] = tempArray[j];
                    System.out.println(Arrays.toString(array));
                }
                counter = 0;
            }
            if (array.length == 1) {
                return array[0];
            }
        }
    }
}