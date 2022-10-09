package com.it_academy.query_executor;

import com.it_academy.model.Account;
import com.it_academy.model.Currency;
import com.it_academy.model.Transaction;
import com.it_academy.model.User;

import java.sql.*;

import static java.lang.String.format;

public class UserQueryExecutor {
    public static String updateAccountAndTransactQuery = "UPDATE Accounts SET balance ='%d' WHERE accountId = '%d'; " +
            "INSERT INTO Transactions (accountId, amount) VALUES ('%d', '%d')";
    public static String addAccountQuery = "INSERT INTO Accounts (userId, balance, currency) VALUES('%d', '%d', '%s')";
    public static String queryToVerifyAccount = "SELECT accountId FROM Accounts WHERE currency= '%s' " +
            "AND userId = '%d';";

    public static void printAllUsers(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM Users;");
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            System.out.println('\n' + "id: " + resultSet.getInt("userId"));
            System.out.println("name: " + resultSet.getString("name"));
            System.out.println("address: " + resultSet.getString("address"));
        }
        resultSet.close();
        statement.close();
    }

    public static void addUser(Connection connection, User user) throws SQLException {
        Statement statement = connection.createStatement();
        String queryToAddUser = "INSERT INTO Users (name, address) VALUES('%s', '%s')";
        statement.executeUpdate(format(queryToAddUser, user.getName(), user.getAddress()));
        statement.close();
        ResultSet generatedUserId = statement.executeQuery("SELECT last_insert_rowid()");
        if (generatedUserId.next()) {
            user.setUserId(generatedUserId.getInt(1));
            System.out.println("Registration was successful, your userId is " + user.getUserId());
            generatedUserId.close();
            statement.close();
        }
    }

    public static void addAccount(Connection connection, int userID, Account account) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultCurrencySet = statement.executeQuery(format(queryToVerifyAccount,
                account.getCurrency().toString(), userID));
        if (resultCurrencySet.next()) {
            account.setAccountId(resultCurrencySet.getInt("accountId"));
            System.out.println("You already have an account in this currency, accountId is " + account.getAccountId());
            resultCurrencySet.close();
        } else {
            statement.executeUpdate(format(addAccountQuery, userID,
                    account.getBalance(), account.getCurrency().toString()));
            ResultSet generatedAccountId = statement.executeQuery("SELECT last_insert_rowid()");
            if (generatedAccountId.next()) {
                account.setAccountId(generatedAccountId.getInt(1));
                System.out.println("Registration was successful, your accountId is " + account.getAccountId());
            }
            generatedAccountId.close();
            statement.close();
        }
    }

    public static void refillAccount(Connection connection, int userId,
                                     int amount, String currency) throws SQLException {
        try {
            Transaction transaction = new Transaction();
            Account account = getAccountBalance(connection, userId, currency);
            if ((account.getBalance() + amount) > 2000000000) {
                System.out.println("You can't to refill account, amount is too match");
            } else {
                account.setBalance(account.getBalance() + amount);
                Statement statement = connection.createStatement();
                statement.executeUpdate(format(updateAccountAndTransactQuery, account.getBalance(),
                        account.getAccountId(), account.getAccountId(), amount));
                ResultSet generatedTrancactionId = statement.executeQuery("SELECT last_insert_rowid()");
                if (generatedTrancactionId.next()) {
                    transaction.setTransactionId(generatedTrancactionId.getInt(1));
                    generatedTrancactionId.close();
                }
                statement.close();
                System.out.println("transaction with transactionId:  " + transaction.getTransactionId() +
                        " completed successfully. Вalance of your account with accountId " + account.getAccountId() +
                        " is: " + account.getBalance() + account.getCurrency());
            }
        } catch (NullPointerException e) {
            System.out.print("NullPointerException caught");
        }
    }

    public static void withdrawAccount(Connection connection, int userId,
                                       int amount, String currency) throws SQLException {
        try {
            Transaction transaction = new Transaction();
            Account account = getAccountBalance(connection, userId, currency);
            amount = amount * -1;
            if ((account.getBalance() + amount) > 2000000000 || (account.getBalance() + amount) < 0) {
                System.out.println("You can't to withdrawAccount account, amount is too match");
            } else {
                account.setBalance(account.getBalance() + amount);
                Statement statement = connection.createStatement();
                statement.executeUpdate(format(updateAccountAndTransactQuery, account.getBalance(),
                        account.getAccountId(), account.getAccountId(), amount));
                ResultSet generatedTrancactionId = statement.executeQuery("SELECT last_insert_rowid()");
                if (generatedTrancactionId.next()) {
                    transaction.setTransactionId(generatedTrancactionId.getInt(1));
                    generatedTrancactionId.close();
                }
                statement.close();
                System.out.println("transaction with transactionId:  " + transaction.getTransactionId() +
                        " completed successfully. Вalance of your account with accountId: " + account.getAccountId() +
                        " is: " + account.getBalance() + account.getCurrency());
            }
        } catch (NullPointerException e) {
            System.out.print("NullPointerException caught");
        }
    }

    public static Account getAccountBalance(Connection connection, int userID, String currency) throws SQLException {
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM Accounts WHERE currency= '%s' AND userId = '%d';";
        Account account = null;
        ResultSet resultSet = statement.executeQuery(format(query, currency, userID));
        if (resultSet.next()) {
            account = new Account();
            account.setAccountId(resultSet.getInt("accountId"));
            account.setUserId(resultSet.getInt("userId"));
            account.setCurrency(Currency.valueOf(resultSet.getString("currency")));
            account.setBalance(resultSet.getInt("balance"));
        } else {
            System.out.println("You have not opened an account yet, please select item 2 in the menu");
        }
        statement.close();
        return account;
    }
}

