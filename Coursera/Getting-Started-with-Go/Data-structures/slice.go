package main

import (
	"fmt"
	"sort"
	"strconv"
	"strings"
)

func main() {
	result := make([]int, 0, 3)

	for {
		fmt.Printf("Please, enter the number or 'X' to exit\n")
		var s string
		fmt.Scanf("%s", &s)
		if strings.ToLower(s) == "x" {
			break
		}
		value, _ := strconv.Atoi(s)
		result = append(result, value)
		sort.Ints(result)
		fmt.Println(result)
	}


}
