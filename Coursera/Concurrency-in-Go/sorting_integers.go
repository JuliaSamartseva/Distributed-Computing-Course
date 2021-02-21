package main

import (
	"fmt"
	"os"
)

func merge(left []int, right []int) []int {
	result := make([]int, len(left)+len(right))
	leftIndex, rightIndex := 0, 0

	for i := 0; i < cap(result); i++ {
		switch {
		case leftIndex >= len(left):
			result[i] = right[rightIndex]
			rightIndex++
		case rightIndex >= len(right):
			result[i] = left[leftIndex]
			leftIndex++
		case left[leftIndex] < right[rightIndex]:
			result[i] = left[leftIndex]
			leftIndex++
		default:
			result[i] = right[rightIndex]
			rightIndex++
		}
	}
	return result
}

func bubbleSort(input []int, result chan []int) {
	fmt.Printf("Sorting array: %v \n", input)
	swapped := true
	for swapped {
		swapped = false
		for i := 1; i < len(input); i++ {
			if input[i-1] > input[i] {
				input[i], input[i-1] = input[i-1], input[i]
				swapped = true
			}
		}
	}
	result <- input
}

func partition(data []int, result chan []int) {
	if len(data) < 2 {
		result <- data
		return
	}

	first := make(chan []int)
	second := make(chan []int)
	third := make(chan []int)
	fourth := make(chan []int)

	step := len(data) / 4

	go bubbleSort(data[:step], first)
	go bubbleSort(data[step:2*step], second)
	go bubbleSort(data[2*step:3*step], third)
	go bubbleSort(data[3*step:], fourth)

	firstArray := <-first
	secondArray := <-second
	thirdArray := <-third
	fourthArray := <-fourth

	close(first)
	close(second)
	close(third)
	close(fourth)

	firstMerge := merge(firstArray, secondArray)
	secondMerge := merge(thirdArray, fourthArray)
	resultArray := merge(firstMerge, secondMerge)
	result <- resultArray
}

func mergeSort(data []int) []int {
	res := make(chan []int)
	go partition(data, res)
	finalResult := <-res
	return finalResult
}

func read(n []int) []interface{} {
	p := make([]interface{}, len(n))
	for i := range n {
		p[i] = &n[i]
	}
	return p
}

func main() {
	var size int
	for {
		fmt.Println("How many numbers is in the array: ")
		fmt.Scan(&size)
		if size < 4 {
			fmt.Println("Size of the array should be more than 4.")
			continue
		}
		numbers := make([]int, size)
		fmt.Println("Print your array of the given size divided by spaces: ")
		n, err := fmt.Fscan(os.Stdin, read(numbers)...)

		fmt.Println("Your array: ")
		if err != nil {
			fmt.Println(err)
			continue
		}
		fmt.Println(numbers, n)

		sorted := mergeSort(numbers)
		fmt.Println(sorted)
		break
	}
}
