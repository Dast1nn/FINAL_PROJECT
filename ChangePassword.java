package com.example.final_inf_202;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

public class ChangePassword {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PasswordField confirm_password;

    @FXML
    private PasswordField new_password;
    @FXML
    private Button go_back;


    @FXML
    private Button reset_password;

    @FXML
    void reset_password_button(ActionEvent event) {
        String pas = new_password.getText();
        String con = confirm_password.getText();
        int id = LoggedUser.getId();
        if(pas.equals(con)){
            DatabaseSingleton databaseSingleton = DatabaseSingleton.getInstance();

            try (Connection connection = databaseSingleton.getConnection()) {
                String updateQuery = "UPDATE regis SET password = ? WHERE id = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                    preparedStatement.setString(1, pas);
                    preparedStatement.setInt(2, id);
                    preparedStatement.executeUpdate();
                    Register.showAlert("Password changed successfully!");
                    new_password.clear();
                    confirm_password.clear();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                Register.showAlert("Error changing password.");
                new_password.clear();
                confirm_password.clear();
            }

        }
        else {
            Register.showAlert("Passwords not the same!");
        }
    }
    @FXML
    void initialize() {

    }
    @FXML
    void go_back_button(ActionEvent event) throws IOException {
        go_back.getScene().getWindow().hide();
        Stage signup = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Profile.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 738, 476);
        signup.setScene(scene);
        signup.show();
        signup.setResizable(false);
    }

}
