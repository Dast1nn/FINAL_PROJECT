package com.example.final_inf_202;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import static java.sql.DriverManager.getConnection;

public class Menu {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label balance;

    @FXML
    private Button dashboard;

    @FXML
    private Label expenses;

    @FXML
    private Label income;

    @FXML
    private Button log_out;

    @FXML
    private Button profile;

    @FXML
    private Button send_money;

    @FXML
    private Button transaction;

    @FXML
    private TextField transfer_money;

    @FXML
    private TextField transfer_phone;

    @FXML
    void dashboardAction(ActionEvent event) throws IOException {
        dashboard.getScene().getWindow().hide();
        Stage signup = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 738, 476);
        signup.setScene(scene);
        signup.show();
        signup.setResizable(false);
    }

    @FXML
    void log_out_button(ActionEvent event) throws IOException {
        log_out.getScene().getWindow().hide();
        Stage signup = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Register.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 471, 555);
        signup.setScene(scene);
        signup.show();
        signup.setResizable(false);
    }

    @FXML
    void profile_button(ActionEvent event) throws IOException {
        profile.getScene().getWindow().hide();
        Stage signup = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Profile.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 738, 476);
        signup.setScene(scene);
        signup.show();
        signup.setResizable(false);
    }

    @FXML
    void transaction_button(ActionEvent event) throws IOException {
        transaction.getScene().getWindow().hide();
        Stage signup = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Transaction.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 738, 476);
        signup.setScene(scene);
        signup.show();
        signup.setResizable(false);

    }

    @FXML
    void send_money_button(ActionEvent event) {
    if(DB.transfer_1(transfer_phone,transfer_money)) {
        Register.showAlert("Transfer successfull");
        LoggedUser.setPhone(transfer_phone);
        int amount = Integer.parseInt(transfer_money.getText());
        LoggedUser.setAmount(amount);
    }
        else Register.showAlert("Transfer Failed...");



    }int id=LoggedUser.getId();
    @FXML
    void initialize() {
       DB.loadBalance(balance);
    }

}
