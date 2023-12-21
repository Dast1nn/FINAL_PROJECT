package com.example.final_inf_202;
import java.sql.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class Register {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button create;

    @FXML
    private Button go_back;

    @FXML
    private Button log_in_here;

    @FXML
    private TextField re_email;

    @FXML
    private PasswordField re_password;

    @FXML
    private TextField re_phone;

    @FXML
    private TextField regis_name;
    @FXML
    private Label lable_suc;
    @FXML
    private TextField balance;

    @FXML
    void back(ActionEvent event) throws IOException {
        go_back.getScene().getWindow().hide();
        Stage signup = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Register.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 471, 555);
        signup.setScene(scene);
        signup.show();
        signup.setResizable(false);
    }

    @FXML
    void create_acc(ActionEvent event) {
        String name = regis_name.getText();
        String pas = re_password.getText();
        String email = re_email.getText();
        String phone = re_phone.getText();
        String bal = balance.getText();
        if (name.isEmpty()  || pas.isEmpty() || email.isEmpty() || phone.isEmpty() || bal.isEmpty()) {
            showAlert("Please fill in all fields");
            return;
        }
          if(DB.register(name,pas,email,phone,bal)){
              showAlert("Registration Successful\nPlease, go back and login");
              regis_name.clear();
              re_password.clear();
              re_email.clear();
              re_phone.clear();
              balance.clear();
          }
          else{
              showAlert("Registration failed");

          }
        }

            @FXML
            void log_in (ActionEvent event) throws IOException {
                    log_in_here.getScene().getWindow().hide();
                    Stage signup = new Stage();
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Register.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(), 471, 555);
                    signup.setScene(scene);
                    signup.show();
                    signup.setResizable(false);
            }

            @FXML
            void initialize () {

            }
    public static void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

        }
