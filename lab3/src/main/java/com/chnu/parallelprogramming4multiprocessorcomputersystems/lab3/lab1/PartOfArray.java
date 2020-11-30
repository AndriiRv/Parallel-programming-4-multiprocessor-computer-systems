package com.chnu.parallelprogramming4multiprocessorcomputersystems.lab3.lab1;

class PartOfArray {
    private int[] partOfArray;
    private boolean isNeedOneMorePart;

    int[] getPartOfArray() {
        return partOfArray;
    }

    void setPartOfArray(int[] partOfArray) {
        this.partOfArray = partOfArray;
    }

    boolean isNeedOneMorePart() {
        return isNeedOneMorePart;
    }

    void setNeedOneMorePart(boolean needOneMorePart) {
        isNeedOneMorePart = needOneMorePart;
    }
}