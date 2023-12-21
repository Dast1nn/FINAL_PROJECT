package com.example.final_inf_202;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class LogIn {

        @FXML
        private ResourceBundle resources;

        @FXML
        private URL location;

        @FXML
        private Button SignUp;

        @FXML
        private Button logIn;

        @FXML
        private PasswordField password;

        @FXML
        private  TextField  phone_login;

        @FXML
        void signUp(ActionEvent event) throws IOException {
                SignUp.getScene().getWindow().hide();
                Stage signup = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("regis_1.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 471, 555);
                signup.setScene(scene);
                signup.show();
                signup.setResizable(false);
        }
        @FXML
        void login_acc(ActionEvent event) throws  IOException {
                String phone = phone_login.getText();
                String enteredPassword = password.getText();
                User user = DB.validateLogin(phone, enteredPassword);
                if(user != null){
                        showAlert( "Login Successfully!");
                        int id= DB.loadId(phone_login);
                        LoggedUser.setId(id);
                        logIn.getScene().getWindow().hide();
                        Stage signup = new Stage();
                        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Menu.fxml"));
                        Scene scene = new Scene(fxmlLoader.load(), 738, 476);
                        signup.setScene(scene);
                        signup.show();
                        signup.setResizable(false);

                }else{
                        showAlert( "Login failed...");
                }
        }


        @FXML
        void initialize() {

        }
        private void showAlert(String message) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText(message);
                alert.showAndWait();
        }

    }
