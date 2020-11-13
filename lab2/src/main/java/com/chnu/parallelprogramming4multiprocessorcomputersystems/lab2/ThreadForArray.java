package com.chnu.parallelprogramming4multiprocessorcomputersystems.lab2;

public class ThreadForArray implements Runnable {
    private int[] array;
    private int counter;

    ThreadForArray(int[] array, int counter) {
        this.array = array;
        this.counter = counter;
    }

    @Override
    public void run() {
        calculateSum();
    }

    private synchronized void calculateSum() {
        array[counter] = array[counter] + array[array.length - 1 - counter];
    }
}