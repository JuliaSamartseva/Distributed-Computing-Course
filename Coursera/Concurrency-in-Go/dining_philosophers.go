package main

import (
	"fmt"
	"sync"
	"time"
)

type Chopstick struct{ sync.Mutex }

type Philosopher struct {
	left, right *Chopstick
	id int
	eats int
}

func (p Philosopher) eat(permission chan bool, philosopherReady *sync.WaitGroup) {
	for i := 0; i < 3; i++ {
		// getting permission from the host
		_ = <-permission

		p.left.Lock()
		p.right.Lock()

		fmt.Printf("Philosopher %v is starting to eat. \n", p.id)
		time.Sleep(1 * time.Second)
		p.eats++
		fmt.Printf("Philosopher %v has finished eating. Eat count = %v \n", p.id, p.eats)

		p.right.Unlock()
		p.left.Unlock()

		philosopherReady.Done()
	}
}

func philosophersHost(wg *sync.WaitGroup, permission chan bool, philosopherReady *sync.WaitGroup) {
	defer wg.Done()
	for i := 0; i < 7; i++ {
		// giving the permission to 2 philosophers

		philosopherReady.Add(2)
		permission <- true
		permission <- true
		philosopherReady.Wait()
	}
	// 14 philosophers have finished eating
	// giving permission to one which is left
	philosopherReady.Add(1)
	permission <- true
	philosopherReady.Wait()
}

func main() {
	chopsticks := make([]*Chopstick, 5)
	for i := 0; i < 5; i++ {
		chopsticks[i] = new(Chopstick)
	}
	philosophers := make([]*Philosopher, 5)
	for i := 0; i < 5; i++ {
		philosophers[i] = &Philosopher{chopsticks[i], chopsticks[(i + 1) % 5], i, 0 }
	}

	permission := make(chan bool, 2)
	philosopherReady := sync.WaitGroup{}

	wg := sync.WaitGroup{}
	wg.Add(1)
	go philosophersHost(&wg, permission, &philosopherReady)


	// Start dining.
	for i := 0; i < 5; i++ {
		go philosophers[i].eat(permission, &philosopherReady)
	}

	wg.Wait()
}
