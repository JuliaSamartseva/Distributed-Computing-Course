package main

import (
	"bufio"
	"fmt"
	"os"
	"strings"
)

type Person struct {
	FirstName string
	LastName string
}

func main() {
	var fileName string
	var validFile *os.File

	for {
		fmt.Println("Print the name of the file: ")
		fmt.Scanf("%s", &fileName)
		file, err := os.Open(fileName)
		if err != nil {
			fmt.Println("Try again.")
		} else {
			validFile = file
			break
		}
	}
	fileScanner := bufio.NewScanner(validFile)
	result := make([]Person, 0, 1)
	for fileScanner.Scan() {
		temp := strings.Split(fileScanner.Text(), " ")
		result = append(result, Person{temp[0], temp[1]})
	}
	validFile.Close()
	for _, person := range result {
		fmt.Printf("First name: %s, Last name: %s\n", person.FirstName, person.LastName)
	}
}
