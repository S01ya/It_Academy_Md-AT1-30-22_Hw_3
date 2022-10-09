package com.it_academy.service;

import com.it_academy.model.Account;
import com.it_academy.model.Currency;

import java.util.Scanner;

public class AccountService {
        public static Account accountInput(String userCurrency) {
            Account account = new Account();
            switch (userCurrency){
                case "BY":
                    account.setCurrency(Currency.BY);
                    break;
                case "RUB":
                    account.setCurrency(Currency.RUB);
                    break;
                case "USD":
                    account.setCurrency(Currency.USD);
                    break;
                case "EUR":
                    account.setCurrency(Currency.EUR);
                    break;
                case "PLN":
                    account.setCurrency(Currency.PLN);
                    break;
                default:
                    System.out.println("You have mistaken in writing currency. Please, try again");
                    accountInput(userCurrency);
            }
            account.setBalance(0);
            return account;
        }
}
