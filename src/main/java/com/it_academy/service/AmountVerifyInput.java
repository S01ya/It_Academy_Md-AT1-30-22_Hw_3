package com.it_academy.service;

import java.util.Scanner;

public class AmountVerifyInput {
    public static Integer getUserAmountForRefill() {
        Scanner scanner = new Scanner(System.in);
        Integer amount = null;
        Integer number;
        System.out.println("Please enter amount for refill/withdraw your account");
        if (scanner.hasNextInt()) {
            number = scanner.nextInt();
            if (number > 100000000) {
                System.out.println("The amount you entered is more than 100 000 000. Try again");

                number = getUserAmountForRefill();
            }
                amount = number;
        } else {
            System.out.println("The amount you entered is not recognized. Try again");
            scanner.next();
            amount = getUserAmountForRefill();
        }
        return amount;
    }
}
