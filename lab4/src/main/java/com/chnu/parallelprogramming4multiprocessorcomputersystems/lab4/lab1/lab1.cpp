#include <iostream>

using namespace std;

void getArray(int *array, int length) {
    for (int i = 0; i < length; i++) {
        array[i] = i;
    }
}

int getSumPartOfArray(int *array, int leftIndex, int rightIndex) {
    int result = 0;
    for (int i = leftIndex; i <= rightIndex; i++) {
        result += array[i];
    }
    return result;
}

int getSum(int *array, int length, int countOfPartOfArray) {
    int leftIndex = 0;
    int lengthOfPartOfArray = length / countOfPartOfArray;

    int result = 0;
    for (int i = 0; i < --countOfPartOfArray; i++) {
        int rightIndex = leftIndex + lengthOfPartOfArray;
        result += getSumPartOfArray(array, leftIndex, rightIndex);

        leftIndex += ++lengthOfPartOfArray;
    }

    if (countOfPartOfArray <= 1) {
        result += getSumPartOfArray(array, leftIndex, --length);
    }
    return result;
}

int main() {
    int lengthOfArray = 100;
    int array[lengthOfArray];

    getArray(array, lengthOfArray);

    int countOfPartOfArray = 2;
    cout << "sum: " << getSum(array, lengthOfArray, countOfPartOfArray) << endl;

    return 0;
}