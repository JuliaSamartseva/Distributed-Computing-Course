package main

import (
	"bufio"
	"fmt"
	"math/rand"
	"os"
	"sync"
	"time"
)

type Garden struct {
	matrix [][]bool
	sync.RWMutex
}

func generateGarden(size int) [][]bool {
	garden := make([][]bool, size)
	for i := 0; i < size; i++ {
		garden[i] = make([]bool, size)
		for j := 0; j < size; j++ {
			garden[i][j] = true
		}
	}
	return garden
}

func nature(garden *Garden) {
	for {
		garden.Lock()
		size := len(garden.matrix)
		garden.matrix[rand.Intn(size)][rand.Intn(size)] = false
		time.Sleep(20 * time.Millisecond)
		garden.Unlock()
	}
}

func gardenWatcher(garden *Garden, id int) {
	for {
		for i, innerArray := range garden.matrix {
			for j := range innerArray {
				time.Sleep(10 * time.Millisecond)
				garden.Lock()
				if garden.matrix[i][j] == false {
					garden.matrix[i][j] = true
					fmt.Printf("Garden watcher %v has watered a plant with coordinates %v, %v.\n", id, i, j)
				}
				garden.Unlock()
			}
		}
	}
}

func printStateToFile(garden *Garden, file *os.File) {
	for {
		time.Sleep(3 * time.Second)
		w := bufio.NewWriter(file)
		garden.RLock()
		for i, innerArray := range garden.matrix {
			for j := range innerArray {
				fmt.Fprint(w, garden.matrix[i][j], " ")
			}
			fmt.Fprintf(w, "\n")
		}
		fmt.Fprintf(w, "\n")
		w.Flush()
		garden.RUnlock()
	}
}

func printGarden(garden *Garden) {
	for {
		time.Sleep(3 * time.Second)
		garden.RLock()
		fmt.Printf("State of the garden: \n")
		for i, innerArray := range garden.matrix {
			for j := range innerArray {
				fmt.Printf("%v ", garden.matrix[i][j])
			}
			fmt.Printf("\n")
		}
		fmt.Printf("\n")
		garden.RUnlock()
	}
}

func main() {
	var garden Garden
	garden.matrix = generateGarden(20)
	file, err := os.Create("garden.txt")
	if err != nil {
		panic(err)
	}
	defer file.Close()

	infiniteWait := sync.WaitGroup{}
	infiniteWait.Add(1)

	// starting goroutines
	go nature(&garden)
	go gardenWatcher(&garden, 0)
	go printStateToFile(&garden, file)
	go printGarden(&garden)
	time.Sleep(3000 * time.Millisecond)
	go gardenWatcher(&garden, 1)

	infiniteWait.Wait()
}
