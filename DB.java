package com.example.final_inf_202;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.*;
import java.util.ArrayList;
public class DB {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/Project";
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "Dastan1506";
    private static final DatabaseSingleton databaseSingleton = DatabaseSingleton.getInstance();

    public static User validateLogin(String phone, String password) {
        try (Connection connection = databaseSingleton.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM regis WHERE phone = ? AND password = ?")) {

            preparedStatement.setString(1, phone);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int userId = resultSet.getInt("id");
                int currentBalance = resultSet.getInt("balance");

                return new User(userId, phone, password, currentBalance);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean register(String name, String password, String email, String phone, String balance) {
        try {
            if (!checkUser(phone)) {
                try (Connection connection = databaseSingleton.getConnection();
                     PreparedStatement preparedStatement = connection.prepareStatement(
                             "INSERT INTO regis (name, password, email, phone, balance) VALUES (?, ?, ?, ?, ?)")) {

                    preparedStatement.setString(1, name);
                    preparedStatement.setString(2, password);
                    preparedStatement.setString(3, email);
                    preparedStatement.setString(4, phone);
                    preparedStatement.setInt(5, Integer.parseInt(balance));

                    preparedStatement.executeUpdate();
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    private static boolean checkUser(String phone) {
        try (Connection connection = databaseSingleton.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM regis WHERE phone = ?")) {

            preparedStatement.setString(1, phone);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static void loadBalance(Label label) {
        try (Connection connection = databaseSingleton.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT balance FROM regis WHERE id = ?")) {

            statement.setInt(1, LoggedUser.getId());

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int balance = resultSet.getInt("balance");
                label.setText(balance + "$");
            } else {
                label.setText("No balance found");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int loadId(TextField field) {
        int id = 0;
        try (Connection connection = databaseSingleton.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT id FROM regis WHERE phone = ?")) {

            statement.setString(1, field.getText());

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                id = resultSet.getInt("id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public static boolean existPhone(String phone) {
        try (Connection connection = databaseSingleton.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT phone FROM regis WHERE phone = ?")) {

            statement.setString(1, phone);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static int myBalance() {
        int balance = 0;
        try (Connection connection = databaseSingleton.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT balance FROM regis WHERE id = ?")) {

            statement.setInt(1, LoggedUser.getId());

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                balance = resultSet.getInt("balance");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return balance;
    }

    public static boolean transfer_1(TextField phone, TextField money) {
        if (existPhone(phone.getText()) && myBalance() - Integer.parseInt(money.getText()) >= 0) {
            try (Connection connection = databaseSingleton.getConnection();
                 PreparedStatement statement = connection.prepareStatement(
                         "UPDATE regis SET balance = CASE WHEN id = ? THEN balance - ? WHEN phone = ? THEN balance + ? ELSE balance END")) {

                statement.setInt(1, LoggedUser.getId());
                statement.setInt(2, Integer.parseInt(money.getText()));
                statement.setString(3, phone.getText());
                statement.setInt(4, Integer.parseInt(money.getText()));

                statement.executeUpdate();
                return true;

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    
   }