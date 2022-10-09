package com.it_academy.demo;

import com.it_academy.model.User;
import com.it_academy.service.AccountService;
import com.it_academy.service.AmountVerifyInput;
import com.it_academy.service.UserService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import static com.it_academy.query_executor.UserQueryExecutor.*;

public class ApplicationDB {
    private static final String JDBC_DRIVER_PATH = "org.sqlite.JDBC";
    private static final String DATABASE_URL = "jdbc:sqlite:MYDB.db";

    public static void main(String[] args) throws SQLException {
        if (isDriverExists()) {
            System.out.println("Hi");
            // 2. Создаем connection и внуть передаем URL базы данных
            Connection connection = DriverManager.getConnection(DATABASE_URL);
            String action;
            do {
                printMenu();
                action = new Scanner(System.in).nextLine();
                switch (action) {
                    case "1":
                        addUser(connection, UserService.userInput());
                        break;
                    case "2":
                        System.out.println("Please enter userId in oder to create account: ");
                        int userID = new Scanner(System.in).nextInt();
                        System.out.println("Enter account's currency by your wish: BY, RUB, USD, EUR or PLN");
                        String userCurrency = new Scanner(System.in).nextLine();
                        addAccount(connection, userID, AccountService.accountInput(userCurrency));
                        break;
                    case "3":
                        System.out.println("Please enter userId to refill account:");
                        int userId = new Scanner(System.in).nextInt();
                        AmountVerifyInput amountVerifyInput = new AmountVerifyInput();
                        Integer amount = amountVerifyInput.getUserAmountForRefill();
                        System.out.println("Please enter currency to refill account:");
                        String currency = new Scanner(System.in).nextLine();
                        refillAccount(connection, userId, amount, currency);
                        break;
                    case "4":
                        System.out.println("Please enter userId to withdraw account:");
                        int userIdForWithdraw = new Scanner(System.in).nextInt();
                        AmountVerifyInput amountVerifyInput1 = new AmountVerifyInput();
                        Integer amountForWithdraw = amountVerifyInput1.getUserAmountForRefill();
                        System.out.println("Please enter currency to withdraw account:");
                        String currencyForWithdraw = new Scanner(System.in).nextLine();
                        withdrawAccount(connection, userIdForWithdraw, amountForWithdraw, currencyForWithdraw);
                        break;
                    case "5":
                        printAllUsers(connection);
                        break;
                    case "6":
                        System.out.println("Thanks for using the program!");
                        break;
                }
            } while (!"6".equals(action));
            connection.close();
        }
    }

    private static void printMenu() {
        String multipleLines = ""
                + System.lineSeparator() + "Please enter a number of desired operation:"
                + System.lineSeparator() + "1 - if you need to register (add) а new user"
                + System.lineSeparator() + "2 - if you need to add an account to user"
                + System.lineSeparator() + "3 - if you need to refill account"
                + System.lineSeparator() + "4 - if you need to withdraw (money) from the account"
                + System.lineSeparator() + "5 - if you need to show all users"
                + System.lineSeparator() + "6 - if you want to finish the program";
        System.out.println(multipleLines);
    }

    // 1.сначала загружаем драйвер в память с помощью метода forName обернув его в метод isDriverExists:
    public static boolean isDriverExists() {
        try {
            Class.forName(JDBC_DRIVER_PATH); //в метод передаем путь к драйверу JDBC org.sqlite.JDBC
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("JDBC Driver was not found");
            return false;
        }
        return true;
    }
}

