package main

import "sync"

type CyclicBarrier struct {
	generation    int
	count         int
	parties       int
	trip          *sync.Cond
	barrierAction func()
}

func (b *CyclicBarrier) nextGeneration() {
	b.trip.Broadcast()
	b.count = b.parties
	b.generation++
}

func (b *CyclicBarrier) Await() {
	b.trip.L.Lock()
	defer b.trip.L.Unlock()

	generation := b.generation

	b.count--
	index := b.count

	if index == 0 {
		b.barrierAction()
		b.nextGeneration()
	} else {
		for generation == b.generation {
			b.trip.Wait()
		}
	}
}

func NewActionableCyclicBarrier(parties int, barrierAction func()) *CyclicBarrier {
	if parties <= 0 {
		panic("Parties must be positive number.")
	}

	b := CyclicBarrier{
		count:         parties,
		parties:       parties,
		trip:          sync.NewCond(&sync.Mutex{}),
		barrierAction: barrierAction,
	}

	return &b
}
