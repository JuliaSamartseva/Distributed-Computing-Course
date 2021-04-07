package main

import (
	"context"
	"fmt"
	"math/rand"
	"sync"
)

type Arrays struct {
	data    [][]int
	barrier *CyclicBarrier
}

const (
	WorkersNumber = 3
)

func generateArrays(size int) [][]int {
	data := make([][]int, WorkersNumber)
	for i := 0; i < WorkersNumber; i++ {
		data[i] = make([]int, size)
		for j := 0; j < size; j++ {
			data[i][j] = rand.Intn(2)
		}
	}
	return data
}

func checkSum(arrays Arrays, sums []int, index int, ctx context.Context, group *sync.WaitGroup) {
	for {
		select {
		case <-ctx.Done():
			fmt.Println("Quitting...")
			group.Done()
			return
		default:
			fmt.Println("Changing element")
			changeElement(arrays, index)
			sums[index] = sum(arrays.data[index])
			arrays.barrier.Await()
		}
	}
}
func changeElement(arrays Arrays, index int) {
	changeIndex := rand.Intn(len(arrays.data[index]))
	operation := rand.Intn(2)
	switch operation {
	case 0:
		if arrays.data[index][changeIndex] < 100 {
			arrays.data[index][changeIndex]++
		}
	case 1:
		if arrays.data[index][changeIndex] > 0 {
			arrays.data[index][changeIndex]--
		}
	}
}

func sum(array []int) int {
	total := 0
	for i := 0; i < len(array); i++ {
		total += array[i]
	}
	return total
}

func compareSums(sums []int) bool {
	fmt.Printf("Current state: %v\n", sums)
	return sums[0] == sums[1] && sums[1] == sums[2]
}

func main() {
	var size int
	fmt.Printf("Enter size of array: ")
	fmt.Scan(&size)

	sums := make([]int, WorkersNumber)
	var arrays Arrays
	ctx, cancel := context.WithCancel(context.Background())

	arrays.barrier = NewActionableCyclicBarrier(WorkersNumber, func() {
		if compareSums(sums) {
			fmt.Println("All sums are equal, exiting.")
			cancel()
		}
	})
	arrays.data = generateArrays(size)

	wait := sync.WaitGroup{}
	wait.Add(WorkersNumber)
	for i := 0; i < WorkersNumber; i++ {
		go checkSum(arrays, sums, i, ctx, &wait)
	}
	wait.Wait()
}
