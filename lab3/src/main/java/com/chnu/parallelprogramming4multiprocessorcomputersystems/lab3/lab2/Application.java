package com.chnu.parallelprogramming4multiprocessorcomputersystems.lab3.lab2;

public class Application {

    public static void main(String[] args) {
        ArrayManager manager = new ArrayManager(10);

        System.out.println(manager.sum(manager.getArray()));
    }
}