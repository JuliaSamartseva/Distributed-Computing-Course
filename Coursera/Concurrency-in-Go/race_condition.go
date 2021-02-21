package main

import (
	"fmt"
	"sync"
)

func main() {
	counter := 0

	first := 10000
	wg := sync.WaitGroup{}
	wg.Add(2)
	go func() {
		defer wg.Done()
		for i := 0; i < first; i++ {
			counter += 1
		}
	}()

	second := 10000
	go func() {
		defer wg.Done()
		for i := 0; i < second; i++ {
			counter += 1
		}
	}()

	wg.Wait()
	fmt.Printf("Expected %v, got %v - the results are not deterministic. ", first + second, counter)
}
/*
	The code when adding 1 to the counter is not executed as a single atomic instruction.
	Instead, it consists of such steps:
	- Read counter from memory into register.
	- Add 1 to register.
	- Write register to memory.

	Two threads can read the counter from memory, and add the 1 simultaneously, and write it to the memory.
	This will result to counter having different value than it was expected.
 */