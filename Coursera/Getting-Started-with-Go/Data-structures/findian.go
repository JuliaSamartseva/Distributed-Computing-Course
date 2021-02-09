package main

import (
	"fmt"
	"strings"
)

func main() {
	var x string
	fmt.Scanf("%s", &x)
	x = strings.ToLower(x)
	if len(x) < 3 || (x[0] != 'i' || x[len(x) - 1] != 'n')  {
		fmt.Printf("Not Found!")
		return
	}

	for i, c := range x {
		if i != 0 && i != len(x) - 1 && c == 'a' {
			fmt.Printf("Found!")
			return
		}
	}

	fmt.Printf("Not Found!")
}
