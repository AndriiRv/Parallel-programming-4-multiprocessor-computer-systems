package com.chnu.parallelprogramming4multiprocessorcomputersystems.lab1;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadForArray implements Runnable {
    private static final int COUNT_OF_TWO_CORE_CPU = 2;

    private int[][] arrayOfArrays;
    private volatile AtomicInteger counter = new AtomicInteger(0);
    private volatile AtomicInteger sum = new AtomicInteger(0);
    private int countOfThreads;

    ThreadForArray(int[][] arrayOfArrays, int countOfThreads) {
        this.arrayOfArrays = arrayOfArrays;
        this.countOfThreads = countOfThreads;
    }

    int getCountOfThreads() {
        return countOfThreads;
    }

    @Override
    public void run() {
        calculateSum();
    }

    private synchronized void calculateSum() {
        System.out.println(Thread.currentThread().getName() + " was started");
        int countOfPartForFirstThread = (arrayOfArrays.length / countOfThreads) + 1;

        if (countOfThreads < arrayOfArrays.length) {
            if (countOfThreads == 1) {
                setSumToVar();
            } else if (countOfThreads == COUNT_OF_TWO_CORE_CPU) {
                int countOfPartForSecondThread = arrayOfArrays.length - counter.get();
                calculateSumByThreads(countOfPartForFirstThread, countOfPartForSecondThread);
            } else if (countOfThreads > COUNT_OF_TWO_CORE_CPU) {
                int countOfPartForNextThreads = arrayOfArrays.length / countOfThreads;
                calculateSumByThreads(countOfPartForFirstThread, countOfPartForNextThreads);
            }
        } else if (counter.get() < arrayOfArrays.length) {
            setSumToVar();
        }
        System.out.println(Thread.currentThread().getName() + " was ended");
    }

    private void calculateSumByThreads(int countOfPartForFirstThread, int countOfPartForNextThread) {
        if (counter.get() <= 0) {
            for (int i = 0; i < countOfPartForFirstThread; i++) {
                setSumToVar();
            }
        } else {
            for (int i = 0; i < countOfPartForNextThread; i++) {
                setSumToVar();
            }
        }
    }

    private void setSumToVar() {
        sum.set(sum.get() + getSumOfPartsOfArray());
        counter.set(counter.get() + 1);
    }

    private int getSumOfPartsOfArray() {
        int[] array = arrayOfArrays[counter.get()];
        int sum = Arrays.stream(array).sum();

        System.out.println(Thread.currentThread().getName() + " finish " + counter.get() + " part to get sum: " + sum);
        return sum;
    }
}