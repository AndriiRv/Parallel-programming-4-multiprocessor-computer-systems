package com.chnu.parallelprogramming4multiprocessorcomputersystems.lab2;

public class Application {

    public static void main(String[] args) throws InterruptedException {
        ArrayManager manager = new ArrayManager();
        int[] array = manager.getArray(100);

        System.out.println("Sum of array: " + manager.sum(array));
    }
}