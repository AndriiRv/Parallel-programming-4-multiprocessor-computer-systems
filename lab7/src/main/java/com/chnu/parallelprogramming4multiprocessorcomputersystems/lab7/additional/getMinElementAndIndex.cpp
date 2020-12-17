#include <iostream>

using namespace std;

int getMinimumElement(int *array) {
    int minimumElement = array[0];

    #pragma omp parallel for reduction(min:minimumElement)
    for (int i = 0; i < sizeof(array); i++) {
        if (array[i] < minimumElement) {
            minimumElement = array[i];
        }
    }

    return minimumElement;
}

int getIndexOfMinimumElement(int *array) {
    int minimumElement = array[0];
    int indexOfMinimumElement;

    for (int i = 0; i < sizeof(array); i++) {
        if (array[i] < minimumElement) {
            minimumElement = array[i];
            indexOfMinimumElement = i;
        }
    }

    return indexOfMinimumElement;
}

int main() {
    int array[] = {5, 10, 4, 2, -22, 88, 56};

    cout << "min elem: " << getMinimumElement(array) << endl;
    cout << "index of min elem: " << getIndexOfMinimumElement(array) << endl;
    return 0;
}