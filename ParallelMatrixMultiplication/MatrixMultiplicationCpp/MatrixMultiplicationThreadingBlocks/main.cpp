#include <iostream>
#include <tbb/parallel_for.h>
#include <tbb/blocked_range.h>
#include <ctime>
#include <chrono>

using namespace tbb;
using namespace std;
using namespace chrono;

const int dimension = 1000;

double A[dimension][dimension];
double B[dimension][dimension];
double C[dimension][dimension];

class MatrixMultiplication
{
public:
    void operator()(blocked_range<int> range) const {
        for (int current = range.begin(); current != range.end(); ++current) {
            for (int i = 0; i < dimension; ++i) {
                for (int j = 0; j < dimension; ++j) {
                    C[current][i] += A[current][j] * B[j][i];
                }
            }
        }
    }
};

void fill(double m[dimension][dimension]) {
    for (int i = 0; i < dimension; i++)
        for (int j = 0; j < dimension; j++)
            m[i][j] = rand() % 200;
}


int main()
{
    fill(A);
    fill(B);

    clock_t start_time = clock();
    parallel_for(blocked_range<int>(0, dimension), MatrixMultiplication());
    std::cout << "Matrix [" << dimension << "x" << dimension << "] Time: " << int(clock() - start_time) << " ms" << endl;
    return 0;
}