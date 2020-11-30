package com.chnu.parallelprogramming4multiprocessorcomputersystems.lab3.lab1;

class ArrayManager {

    int[] getArray(int length) {
        int[] array = new int[length];

        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }

        return array;
    }

    int[][] getArrayOfArrays(int[] array, int countOfPartOfArray) {
        if (countOfPartOfArray > array.length) {
            System.out.println("Count of part of array should be less than length of array");
            return null;
        }

        int[][] result = new int[countOfPartOfArray][array.length / countOfPartOfArray];
        int partOfLengthOfArray = array.length / countOfPartOfArray;

        int lastEndValue = 0;

        int counter = 0;

        int[][] newResult = new int[0][];

        for (int i = 0; i < countOfPartOfArray; i++) {

            int end;
            int start;
            if (counter == 0) {
                end = partOfLengthOfArray - 1;
                lastEndValue = end;
            } else {
                if (i == (countOfPartOfArray - 1)) {
                    end = lastEndValue + partOfLengthOfArray + countOfPartOfArray;
                } else {
                    end = lastEndValue + partOfLengthOfArray;
                }
                if (end >= array.length) {
                    int temp = end - array.length + 1;
                    end -= temp;
                    newResult = new int[countOfPartOfArray][array.length / countOfPartOfArray];
                    copyFromOlderToNewerArray(result, newResult);
                }
            }
            if (counter == 0) {
                start = partOfLengthOfArray - end - 1;
            } else {
                start = lastEndValue + 1;
            }
            lastEndValue = end;

            if (newResult.length == 0) {
                PartOfArray partOfArray = getPartOfArray(array, start, end, i, countOfPartOfArray);
                result[i] = partOfArray.getPartOfArray();

                if (partOfArray.isNeedOneMorePart()) {
                    countOfPartOfArray++;
                }

                counter++;
            } else {
                PartOfArray partOfArray = getPartOfArray(array, start, end, i, countOfPartOfArray);
                newResult[i] = partOfArray.getPartOfArray();
            }
        }
        if (newResult.length == 0) {
            return result;
        } else {
            return newResult;
        }
    }

    private void copyFromOlderToNewerArray(int[][] older, int[][] newer) {
        for (int j = 0; j < older.length; j++) {
            newer[j] = older[j];
        }
    }

    private PartOfArray getPartOfArray(int[] array, int begin, int end, int iteration, int countOfPartOfArray) {
        PartOfArray obj = new PartOfArray();
        int[] partOfArray = new int[end - begin + 1];

        int counter = 0;
        for (int i = begin; i <= end; i++) {
            partOfArray[counter] = array[i];
            counter++;
        }

        if (iteration == (countOfPartOfArray - 1) && (end < array.length - 1)) {
            obj.setNeedOneMorePart(true);
        }
        obj.setPartOfArray(partOfArray);

        return obj;
    }
}