package main

import (
	"fmt"
	"math/rand"
	"strconv"
	"time"
)

const dimension = 2000

func getRandomMatrix(n int) [][]float64{
	data := make([][]float64, n)
	for i := 0; i < n; i++ {
		data[i] = make([]float64, n)
		for j := 0; j < n; j++ {
			data[i][j] = float64(rand.Intn(200))
		}
	}
	return data
}

func multiplyHelper(A[][] float64, B[][] float64, C[][] float64, ch chan int, current int) {
	for i := 0; i < len(A); i++ {
		for j := 0; j < len(B); j++ {
			C[current][i] += A[current][j] * B[j][i]
		}
	}
	ch <- 1
}

func multiply(A[][] float64, B[][] float64, C[][] float64) {
	startTime := time.Now()
	c := make(chan int, len(A))
	for i := 0; i < len(A); i++ {
		go multiplyHelper(A, B, C, c, i)
	}

	for i := 0; i < len(A); i++ {
		<- c
	}
	endTime := time.Now()
	total := endTime.Sub(startTime).Milliseconds()
	fmt.Print("Matrix [" + strconv.Itoa(len(A)) + "x" + strconv.Itoa(len(A)) + "] Time: " + strconv.Itoa(int(total)) + "ms")
}

func main() {
	A := getRandomMatrix(dimension)
	B := getRandomMatrix(dimension)
	C := make([][]float64, dimension)

	for i := range C {
		C[i] = make([]float64, dimension)
	}
	multiply(A, B, C)
}
