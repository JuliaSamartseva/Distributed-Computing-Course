package main

import (
	"context"
	"fmt"
	"golang.org/x/sync/semaphore"
	"math/rand"
	"sync"
	"time"
)

type SmokingObject int

const (
	Tobacco SmokingObject = iota
	Paper
	Matches
)

var smokeMap = map[int]SmokingObject{
	0: Tobacco,
	1: Paper,
	2: Matches,
}

type SmokingObjectChannels struct {
	tobacco *semaphore.Weighted
	paper   *semaphore.Weighted
	matches *semaphore.Weighted
}

func (temp SmokingObject) String() string {
	return [...]string{"Tobacco", "Paper", "Matches"}[temp]
}

func middle(ctx context.Context, wg *sync.WaitGroup, channels SmokingObjectChannels, smokers [3]chan SmokingObject) {
	for {
		time.Sleep(100 * time.Millisecond)
		randomObject := smokeMap[rand.Intn(3)]
		fmt.Printf("Agent chooses %s.\n", randomObject)
		switch randomObject {
		case Tobacco:
			channels.paper.Acquire(ctx, 1)
			channels.matches.Acquire(ctx, 1)
		case Paper:
			channels.tobacco.Acquire(ctx, 1)
			channels.matches.Acquire(ctx, 1)
		case Matches:
			channels.tobacco.Acquire(ctx, 1)
			channels.paper.Acquire(ctx, 1)
		}

		for _, smoker := range smokers {
			smoker <- randomObject
		}

		wg.Add(1)
		wg.Wait()
	}
}

func smoker(wg *sync.WaitGroup, name string, smokeType SmokingObject, neededObjects [2]*semaphore.Weighted, signal chan SmokingObject) {
	for {
		fmt.Printf("%s is waiting for a cigarette\n", name)
		chosen := <-signal
		if smokeType != chosen {
			continue
		}
		neededObjects[0].Release(1)
		neededObjects[1].Release(1)
		fmt.Printf("%s smokes a cigarette\n", name)
		time.Sleep(time.Millisecond * 100)
		wg.Done()
	}
}

func main() {
	var channels SmokingObjectChannels
	ctx := context.Background()
	channels.paper = semaphore.NewWeighted(int64(10))
	channels.tobacco = semaphore.NewWeighted(int64(10))
	channels.matches = semaphore.NewWeighted(int64(10))
	wg := sync.WaitGroup{}
	infiniteWait := sync.WaitGroup{}
	infiniteWait.Add(1)

	signalPaper := make(chan SmokingObject, 1)
	go smoker(&wg, "First (has paper)", Paper, [...]*semaphore.Weighted{channels.matches, channels.tobacco}, signalPaper)
	signalTobacco := make(chan SmokingObject, 1)
	go smoker(&wg, "Second (has tobacco)", Tobacco, [...]*semaphore.Weighted{channels.paper, channels.matches}, signalTobacco)
	signalMatches := make(chan SmokingObject, 1)
	go smoker(&wg, "Third (has matches)", Matches, [...]*semaphore.Weighted{channels.paper, channels.tobacco}, signalMatches)

	go middle(ctx, &wg, channels, [...]chan SmokingObject{signalPaper, signalTobacco, signalMatches})
	infiniteWait.Wait()
}
