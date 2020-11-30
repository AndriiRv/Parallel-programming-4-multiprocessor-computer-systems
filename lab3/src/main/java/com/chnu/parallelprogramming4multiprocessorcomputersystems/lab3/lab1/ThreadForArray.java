package com.chnu.parallelprogramming4multiprocessorcomputersystems.lab3.lab1;

import java.util.Arrays;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadForArray implements Runnable {
    private static final int COUNT_OF_TWO_CORE_CPU = 2;

    private int[][] arrayOfArrays;
    private volatile static AtomicInteger counter = new AtomicInteger(0);
    private volatile static AtomicInteger sum = new AtomicInteger(0);
    private int countOfThreads;

    private CyclicBarrier cyclicBarrier;

    ThreadForArray(int[][] arrayOfArrays, int countOfThreads, CyclicBarrier cyclicBarrier) {
        this.arrayOfArrays = arrayOfArrays;
        this.countOfThreads = countOfThreads;
        this.cyclicBarrier = cyclicBarrier;
    }

    int getCountOfThreads() {
        return countOfThreads;
    }

    public static AtomicInteger getSum() {
        return sum;
    }

    @Override
    public void run() {
        calculateSum();
    }

    private void calculateSum() {
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

        try {
            cyclicBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
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

    private synchronized void setSumToVar() {
        sum.set(sum.get() + getSumOfPartsOfArray());
        counter.set(counter.get() + 1);
    }

    private int getSumOfPartsOfArray() {
        if (counter.get() < arrayOfArrays.length) {
            int[] array = arrayOfArrays[counter.get()];
            int sum = Arrays.stream(array).sum();

            System.out.println(Thread.currentThread().getName() + " finish " + counter.get() + " part to get sum: " + sum);
            return sum;
        }
        return 0;
    }
}