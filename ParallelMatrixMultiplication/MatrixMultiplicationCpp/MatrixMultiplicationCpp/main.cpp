#include <iostream>
#include <omp.h>
#include <conio.h>
#include <intrin.h>
#include <iomanip>
#include <chrono>
#include <stdlib.h>
#include <ctime>
#include <math.h>

using namespace std;

const int dimension = 2000;

void fill(int **A) {
	for (int i = 0; i < dimension; i++)
		for (int j = 0; j < dimension; j++)
			A[i][j] = rand() % 200;
}

int main() {

	srand(time(NULL));

	int** A = new int* [dimension];
	int** B = new int* [dimension];
	int** C = new int* [dimension];
	for (int i = 0; i < dimension; i++) {
		A[i] = new int[dimension];
		B[i] = new int[dimension];
		C[i] = new int[dimension];
	}
		
	fill(A);
	fill(B);

	clock_t start_time = clock();

	#pragma omp parallel for private(current,i,j) shared(A,B,C)
	for (int current = 0; current < dimension; current++)
		for (int i = 0; i < dimension; i++) 
			for (int j = 0; j < dimension; j++)
				C[current][i] += A[current][j] * B[j][i];
		
		

	std::cout << "Matrix [" << dimension << "x" << dimension << "] Time: " << int(clock() - start_time) << " ms" << endl;

	delete[] A;
	delete[] B;
	delete[] C;

	system("pause");

	return 0;
}