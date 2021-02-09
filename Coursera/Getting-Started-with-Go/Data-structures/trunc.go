package main

import (
	"fmt"
)

func main() {
	var x float64
	fmt.Scanf("%f", &x)
	fmt.Printf("Truncated integer: %d", int64(x))
}
