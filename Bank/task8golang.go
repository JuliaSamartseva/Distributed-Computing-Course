package main

import (
	"sync"
	"time"
)

type Bank struct {
	cashbox int
	storage int
	sync.RWMutex
}

type Account struct {
	money int
	sync.RWMutex
}

func observer(bank *Bank) {
	for {
		storageAction := false
		bank.RLock()
		if bank.cashbox < 2000 || bank.cashbox > 100000 {
			storageAction = true
		}
		bank.RUnlock()

		if storageAction {
			bank.Lock()
			time.Sleep(10 * time.Millisecond)
			if bank.cashbox < 2000 {
				if bank.storage < 2000 {
					bank.cashbox += bank.storage
					bank.storage = 0
				} else {
					bank.cashbox += 2000
					bank.storage -= 2000
				}
			} else {
				moneyToTransfer := bank.cashbox / 2
				bank.storage += moneyToTransfer
				bank.cashbox -= moneyToTransfer
			}
			bank.Unlock()
		}

		time.Sleep(500 * time.Millisecond)
	}
}

func getMoney(bank *Bank, account *Account, sum int) {
	account.Lock()
	bank.Lock()
	account.money -= sum
	bank.cashbox -= sum
	bank.Unlock()
	account.Unlock()
}

func fillMoney(bank *Bank, account *Account, sum int) {
	account.Lock()
	bank.Lock()
	account.money += sum
	bank.cashbox += sum
	bank.Unlock()
	account.Unlock()
}

func transferMoney(fromAccount *Account, toAccount *Account, sum int) {
	fromAccount.Lock()
	toAccount.Lock()
	fromAccount.money -= sum
	toAccount.money += sum
	toAccount.Unlock()
	fromAccount.Unlock()
}

func cashier() {
	actions := [3]string{"Get money", "Transfer money", "Fill money"}
	// choose random action
	// choose random id of the account
	// do what was needed
}


