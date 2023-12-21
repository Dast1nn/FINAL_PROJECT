package com.example.final_inf_202;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

import static java.sql.DriverManager.*;
public class Transaction {
    @FXML
    private Button go_back;
    @FXML
    public ListView<Transaction> list_transaction;

    @FXML
    void go_back_button(ActionEvent event) throws IOException {
        go_back.getScene().getWindow().hide();
        Stage signup = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 738, 476);
        signup.setScene(scene);
        signup.show();
        signup.setResizable(false);
    }

    @FXML
    void initialize() throws SQLException {
        ObservableTransactionList transactionList = new ObservableTransactionList();
        transactionList.addObserver((o, arg) -> {
            if (arg instanceof Transaction_1) {
                Transaction_1 transaction = (Transaction_1) arg;
                list_transaction.getItems().add(transaction);
            }
        });
        transactionList.addObserver(new DatabaseObserver(transactionList));

        list_transaction.setItems(transactionList.getTransactions());
        fetchLast5Transactions(transactionList);
        simulateNewTransactions(transactionList);
    }

    private void simulateNewTransactions(ObservableTransactionList transactionList) {
        String phone = String.valueOf(DB.loadId(LoggedUser.getPhone()));
        String  amount = String.valueOf(LoggedUser.getAmount());
        String  date= String.valueOf(LocalDate.now());
        String description = "transfer money";
        String id  = String.valueOf(LoggedUser.getPhone().getText());
        Transaction_1 newTransaction = new Transaction_1(phone, description, amount, date,id);
        if (!transactionList.getTransactions().contains(newTransaction)) {
            transactionList.addTransaction(newTransaction);
        }
    }
    private void fetchLast5Transactions(ObservableTransactionList transactionList) {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Project", "postgres", "Dastan1506");
             Statement statement = connection.createStatement()) {
            String phone_1 = String.valueOf(DB.loadId(LoggedUser.getPhone()));
            String id = String.valueOf(LoggedUser.getId());
            String sqlQuery = "SELECT id_r,phone_r,description, amount, timee FROM transfer WHERE id_s = '" + id + "'";
            ResultSet resultSet = statement.executeQuery(sqlQuery);

            while (resultSet.next()) {
                String phone = resultSet.getString("id_r");
                String id_1 = resultSet.getString("phone_r");
                String description = resultSet.getString("description");
                String amount = resultSet.getString("amount");
                String date = resultSet.getString("timee");
                String list = phone  + " " + description + " "  + amount + " " + date;
                Transaction_1 transaction = new Transaction_1(phone, description, amount, date,id_1);
                if (!transactionList.getTransactions().contains(transaction)) {
                    transactionList.addTransaction(transaction);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

