#include <stdio.h>
#include <iostream>
#include "mpi.h"

using namespace std;

void getArray(int* array, int length)
{
	for (int i = 0; i < length; i++)
	{
		array[i] = i;
	}
}

int getSum(int* array, int* tempArray, int lengthOfArray, int argc, char** argv) {
	int tempSum = 0;
	int sum = 0;

	int rank = 0;
	int countOfPartOfArray = 2;
	int lengthOfPartOfArray = 0;

	MPI_Init(&argc, &argv);
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
	MPI_Comm_size(MPI_COMM_WORLD, &countOfPartOfArray);

	MPI_Status mpiStatus;

	if (rank == 0)
	{
		int rightIndex = 0;
		lengthOfPartOfArray = lengthOfArray / countOfPartOfArray;
		for (int i = 0; i < --countOfPartOfArray; i++)
		{
			rightIndex = i + lengthOfPartOfArray;
			MPI_Send(&lengthOfPartOfArray, 1, MPI_INTEGER, i, 0, MPI_COMM_WORLD);
			MPI_Send(&array[rightIndex], lengthOfPartOfArray, MPI_INTEGER, i, 0, MPI_COMM_WORLD);
		}

		for (int i = 0; i < lengthOfPartOfArray; i++)
		{
			tempSum += array[i];
		}
	}
	else
	{
		MPI_Recv(&lengthOfPartOfArray, 1, MPI_INT, 0, 0, MPI_COMM_WORLD, &mpiStatus);
		MPI_Recv(&tempArray, lengthOfPartOfArray, MPI_INT, 0, 0, MPI_COMM_WORLD, &mpiStatus);
		for (int i = 0; i < lengthOfPartOfArray; i++)
		{
			tempSum += tempArray[i];
		}

		MPI_Send(&tempSum, 1, MPI_INT, 0, 0, MPI_COMM_WORLD);
	}

	MPI_Reduce(&tempSum, &sum, 1, MPI_INTEGER, MPI_SUM, 0, MPI_COMM_WORLD);
	MPI_Finalize();

	return sum;
}

int main(int argc, char** argv)
{
	const int lengthOfArray = 100;
	int array[lengthOfArray];
	getArray(array, lengthOfArray);

	int tempArray[lengthOfArray];
	int sum = getSum(array, tempArray, lengthOfArray, argc, argv);
	cout << "sum: " << sum;

	return 0;
}