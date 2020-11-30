package com.chnu.parallelprogramming4multiprocessorcomputersystems.lab3.lab2;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class ThreadForArray extends Thread {
    private final int leftIndex;
    private final int rightIndex;
    private final CyclicBarrier barrier;
    private int[] array;

    ThreadForArray(ArrayManager arr, int leftIndex, int rightIndex, CyclicBarrier barrier) {
        this.leftIndex = leftIndex;
        this.rightIndex = rightIndex;
        this.barrier = barrier;

        this.array = arr.getArray();
    }

    @Override
    public void run() {
        int tmpSum = array[leftIndex] + array[rightIndex];
        array[Math.min(leftIndex, rightIndex)] = tmpSum;

        try {
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}