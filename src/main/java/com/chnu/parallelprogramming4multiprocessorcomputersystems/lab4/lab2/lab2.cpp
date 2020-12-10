#include <iostream>
#include <vector>
#include <cmath>

using namespace std;

void printArray(vector<int> &array, unsigned long long int size);

void getArray(int *array, int length) {
    for (int i = 0; i < length; i++) {
        array[i] = i;
    }
}

int getSum(vector<int> &array) {
    vector<int> tempArray;
    int counter = 0;

    while (true) {
        array[counter] = array[counter] + array[array.size() - 1 - counter];
        counter++;
        if (counter == array.size() / 2) {
            tempArray = array;
            array = vector<int>(static_cast<int>(ceil(static_cast<double>(array.size()) / 2)));
            for (int j = 0; j < array.size(); j++) {
                array[j] = tempArray[j];
                printArray(array, array.size());
            }
            counter = 0;
        }
        if (array.size() == 1) {
            return array[0];
        }
    }
}

void printArray(vector<int> &array, unsigned long long int size) {
    cout << "[";
    for (int i = 0; i < size; i++) {
        cout << array[i] << " ";
    }
    cout << "]" << endl;
}

int main() {
    int lengthOfArray = 10;
    int array[lengthOfArray];

    getArray(array, lengthOfArray);

    vector<int> vectorArray(array, array + sizeof array / sizeof array[0]);

    cout << "sum: " << getSum(vectorArray) << endl;

    return 0;
}