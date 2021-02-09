package main

import (
	"fmt"
	"math/rand"
	"time"
)

type Player struct {
	monasteryType string
	energy        int
}

func generatePlayers(size int) []Player {
	players := make([]Player, 0, 1)
	for i := 0; i < size/2; i++ {
		players = append(players, Player{"Yin", rand.Intn(100)})
		players = append(players, Player{"Yang", rand.Intn(100)})
	}
	return players
}

func fight(firstPlayer Player, secondPlayer Player) Player {
	time.Sleep(100 * time.Millisecond)
	fmt.Printf("Competing %v ---- %v \n", firstPlayer, secondPlayer)
	if firstPlayer.energy > secondPlayer.energy {
		return firstPlayer
	} else {
		return secondPlayer
	}
}

func compete(players []Player, current chan Player, result chan Player, size int) {
	time.Sleep(1 * time.Second)
	if len(players) < 2 {
		current <- players[0]
	} else if len(players) == 2 {
		r := fight(players[0], players[1])
		current <- r
	} else {
		leftChan := make(chan Player)
		rightChan := make(chan Player)

		middle := len(players) / 2

		go compete(players[:middle], leftChan, result, size)
		go compete(players[middle:], rightChan, result, size)

		var firstWinner, secondWinner Player
		firstWinner = <-leftChan
		secondWinner = <-rightChan

		close(leftChan)
		close(rightChan)

		resultPlayer := fight(firstWinner, secondWinner)

		if len(players) == size {
			result <- resultPlayer
		} else {
			current <- resultPlayer
		}
	}
}

func main() {
	players := generatePlayers(16)
	ch := make(chan Player)
	go compete(players, nil, ch, len(players))
	result := <-ch

	fmt.Printf("The winner is %v with energy %v", result.monasteryType, result.energy)
}
