package main

import (
	"encoding/json"
	"fmt"
)

func main() {
	var name, address string

	fmt.Printf("Enter name: ")
	fmt.Scanf("%s", &name)
	fmt.Printf("Enter address: ")
	fmt.Scanf("%s", &address)

	person := make(map[string]string)
	person["name"] = name
	person[address] = address
	jsonObject, err := json.Marshal(person)
	if err != nil {
		fmt.Println(err)
	} else {
		fmt.Printf(string(jsonObject))
	}
}

